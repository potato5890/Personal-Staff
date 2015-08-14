package wyf.ytl;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
public class Sample_14_5 extends Activity {
	MainView mv;//主View 
	int k = 45;//灵敏度
	private SensorManagerSimulator mySensorManager; //SensorManager对象引用
	//private SensorManager mySensorManager;//SensorManager对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        mv = (MainView) findViewById(R.id.mainView);//获取主View
        //mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	//获得SensorManager
        //调试时用
        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mySensorManager.connectSimulator();        
    } 
    private SensorListener mySensorListener = new SensorListener(){//监听器 
    	@Override 
    	public void onAccuracyChanged(int sensor, int accuracy) {}	//重写onAccuracyChanged方法
    	@Override
    	public void onSensorChanged(int sensor, float[] values) {		//重写onSensorChanged方法
    		if(sensor == SensorManager.SENSOR_ORIENTATION){//只检查姿态的变化
    			double pitch = values[SensorManager.DATA_Y];//得到pitch
    			double roll = values[SensorManager.DATA_Z];//得到roll
    			int x = 0;//临时变量,算中间水泡坐标时会用到
    			int y = 0;
    			if(Math.abs(roll)<=k){//调整x
    				x = mv.big_X + (int)(((mv.big.getWidth()-mv.small.getWidth())/2.0)
    						-(((mv.big.getWidth()-mv.small.getWidth())/2.0)*roll)/k);
    			}
    			else if(roll>k){
    				x = mv.big_X;
    			}
    			else{
    				x = mv.big_X + mv.big.getWidth() - mv.small.getWidth();
    			}
    			if(Math.abs(pitch)<=k){//调整y 
    				y = mv.big_Y + (int)(((mv.big.getHeight()-mv.small.getHeight())/2.0) 
    						+(((mv.big.getHeight()-mv.small.getHeight())/2.0)*pitch)/k);
    			}
    			else if(pitch>k){
    				y = mv.big_Y + mv.big.getHeight() - mv.small.getHeight();
    			}
    			else{
    				y = mv.big_Y;
    			}
    			if(isContain(x, y)){//中间的水泡,如果在圆内才改变坐标
	     			mv.small_X = x;
	    			mv.small_Y = y;   				
    			}
    			mv.postInvalidate();//重绘MainView    			
    		}
    	}
    	public boolean isContain(int x, int y){//判断点是否在园内
    		int tempx = (int) (x + mv.small.getWidth()/2.0);//得到水泡tempx坐标
    		int tempy = (int) (y + mv.small.getWidth()/2.0);//得到水泡tempy坐标
    		int ox = (int) (mv.big_X + mv.big.getWidth()/2.0);//得到大圆的x坐标
    		int oy = (int) (mv.big_X + mv.big.getWidth()/2.0);//得到大圆的y坐标
    		if(Math.sqrt((tempx-ox)*(tempx-ox)+(tempy-oy)*(tempy-oy))
    				>(mv.big.getWidth()/2.0-mv.small.getWidth()/2.0)){
    			return false;//不在圆内
    		}
    		else {
    			return true;//在圆内
    		}
    	}    	
    };    
    @Override
    protected void onResume() {//重写的onResume方法
    	mySensorManager.registerListener(//注册监听
    			mySensorListener, //监听器SensorListener对象
    			SensorManager.SENSOR_ORIENTATION,//传感器的类型为姿态
    			SensorManager.SENSOR_DELAY_UI//频度
    			);
    	super.onResume();
    }	
    @Override
    protected void onPause() {//重写onPause方法
    	mySensorManager.unregisterListener(mySensorListener);//取消注册监听器
    	super.onPause();
    }    
}