package wyf.ytl;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
public class Sample_14_1 extends Activity {
    TextView myTextView1;//x方向的加速度
    TextView myTextView2;//y方向的加速度
    TextView myTextView3;//z方向的加速度
    //SensorManager mySensorManager;//SensorManager对象引用
    SensorManagerSimulator mySensorManager;//声明SensorManagerSimulator对象,调试时用
    @Override 
    public void onCreate(Bundle savedInstanceState) {//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        myTextView1 = (TextView) findViewById(R.id.myTextView1);//得到myTextView1的引用
        myTextView2 = (TextView) findViewById(R.id.myTextView2);//得到myTextView2的引用
        myTextView3 = (TextView) findViewById(R.id.myTextView3);//得到myTextView3的引用
        //mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);//获得SensorManager
        //调试时用
        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mySensorManager.connectSimulator();				//与Simulator服务器连接
    }
    private SensorListener mySensorListener = new SensorListener(){
    	@Override
    	public void onAccuracyChanged(int sensor, int accuracy) {}	//重写onAccuracyChanged方法
    	@Override
    	public void onSensorChanged(int sensor, float[] values) {		//重写onSensorChanged方法
    		if(sensor == SensorManager.SENSOR_ACCELEROMETER){//只检查加速度的变化
    			myTextView1.setText("x方向上的加速度为："+values[0]);	//将提取的x数据显示到TextView
    			myTextView2.setText("y方向上的加速度为："+values[1]);	//将提取的y数据显示到TextView
    			myTextView3.setText("z方向上的加速度为："+values[2]);	//将提取的x数据显示到TextView
    		}
    	}
    };
    @Override
    protected void onResume() {//重写的onResume方法
    	mySensorManager.registerListener(//注册监听
    			mySensorListener, //监听器SensorListener对象
    			SensorManager.SENSOR_ACCELEROMETER,//传感器的类型为加速度
    			SensorManager.SENSOR_DELAY_UI//传感器事件传递的频度
    			);
    	super.onResume();
    }	
    @Override
    protected void onPause() {//重写onPause方法
    	mySensorManager.unregisterListener(mySensorListener);//取消注册监听器
    	super.onPause();
    }
}