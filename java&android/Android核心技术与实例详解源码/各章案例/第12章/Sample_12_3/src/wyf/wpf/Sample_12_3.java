package wyf.wpf;					//声明包语句
import android.app.Activity;			//引入相关类
import android.app.Service;				//引入相关类
import android.os.Bundle;				//引入相关类
import android.os.Vibrator;				//引入相关类
import android.widget.CompoundButton;	//引入相关类
import android.widget.TextView;				//引入相关类
import android.widget.ToggleButton;		//引入相关类
import android.widget.CompoundButton.OnCheckedChangeListener;//引入相关类
public class Sample_12_3 extends Activity {
	Vibrator vibrator;			//声明一个Vibrator对象
    @Override
    public void onCreate(Bundle savedInstanceState) {	//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//设置当前屏幕
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);	//创建Vibrator对象
        ToggleButton tb1 = (ToggleButton)findViewById(R.id.tb1);		//获得ToggleButton对象
        tb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//设置OnCheckedChangeListener监听器
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//重写onCheckedChanged方法
				if(isChecked){		//判断ToggleButton的选中状态
					vibrator.vibrate(new long[]{1000,50,50,100,50}, -1);	//启动振动
					TextView tv1 = (TextView)findViewById(R.id.tv1);		//获得TextView
					tv1.setText(R.string.vibrateOn);					//设置TextView控件内容
				}
				else{
					vibrator.cancel();				//关闭振动
					TextView tv1 = (TextView)findViewById(R.id.tv1);	//获得TextView
					tv1.setText(R.string.vibrateOff);	//设置TextView控件内容
				}	
			}
		});
        ToggleButton tb2 = (ToggleButton)findViewById(R.id.tb2);	//获得ToggleButton对象
        tb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//设置OnCheckedChangeListener监听器
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//重写onCheckedChanged方法
				if(isChecked){		//判断ToggleButton的选中状态
					vibrator.vibrate(2500);	//启动振动
					TextView tv2 = (TextView)findViewById(R.id.tv2);	//获得TextView
					tv2.setText(R.string.vibrateOn);		//设置TextView控件内容
				}
				else{
					vibrator.cancel();	//关闭振动
					TextView tv2 = (TextView)findViewById(R.id.tv2);	//获得TextView
					tv2.setText(R.string.vibrateOff);	//设置TextView控件内容
				}
			}
		});
    }
}