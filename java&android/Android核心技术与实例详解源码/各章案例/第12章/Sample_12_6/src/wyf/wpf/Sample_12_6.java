package wyf.wpf;						//声明包语句

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Sample_12_6 extends Activity {
	MyBatteryReceiver mbr = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {		//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mbr = new MyBatteryReceiver();					//创建Broadcast组件对象
        ToggleButton tb = (ToggleButton)findViewById(R.id.tb);		//获得ToggleButton对象
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//设置监听器
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);	//还有3个Action说说得
					registerReceiver(mbr, filter);					//注册BroadcastReceiver
				}
				else{
					unregisterReceiver(mbr);			//取消注册的BroadcastReceiver
					TextView tv = (TextView)findViewById(R.id.tv);
					tv.setText(null);					//清空TextView中显示的内容
				}
			}
		});
    }
    private class MyBatteryReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {		//重写onReceiver方法
			int current = intent.getExtras().getInt("level");		//获得当前电量
			int total = intent.getExtras().getInt("scale");			//获得总电量
			int percent = current*100/total;		//计算百分比
			TextView tv = (TextView)findViewById(R.id.tv);			//获得TextView对象
			tv.setText("现在的电量是："+percent+"%。");				//设置TextView显示的内容
		}
    }
}