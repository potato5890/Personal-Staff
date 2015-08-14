package wyf.wpf;						//声明包语句

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class Sample_12_7 extends Activity {
	Calendar c = Calendar.getInstance();
	final int DIALOG_TIME = 0;	//设置对话框id
	AlarmManager am;		//声明AlarmManager对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//设置当前屏幕
        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);	//创建AlarmManager对象
        Button btn = (Button)findViewById(R.id.btn);			//获得Button对象
        btn.setOnClickListener(new View.OnClickListener() {		//设置监听器
			@Override
			public void onClick(View v) {		//重写onClick方法
				showDialog(DIALOG_TIME);		//显示时间选择对话框
			}
		});
    }
	@Override
	protected Dialog onCreateDialog(int id) {		//重写onCreateDialog方法
		Dialog dialog = null;
		switch(id){								//对id进行判断
		case DIALOG_TIME:
			dialog=new TimePickerDialog(				//创建TimePickerDialog对象
				this,
				new TimePickerDialog.OnTimeSetListener(){ //创建OnTimeSetListener监听器
					@Override
					public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
						Calendar c=Calendar.getInstance();//获取日期对象	
						c.setTimeInMillis(System.currentTimeMillis());		//设置Calendar对象
						c.set(Calendar.HOUR, hourOfDay);		//设置闹钟小时数
						c.set(Calendar.MINUTE, minute);			//设置闹钟的分钟数
						c.set(Calendar.SECOND, 0);				//设置闹钟的秒数
						c.set(Calendar.MILLISECOND, 0);			//设置闹钟的毫秒数
						Intent intent = new Intent(Sample_12_7.this,AlarmReceiver.class);	//创建Intent对象
						PendingIntent pi = PendingIntent.getBroadcast(Sample_12_7.this, 0, intent, 0);	//创建PendingIntent
						am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);		//设置闹钟
						Toast.makeText(Sample_12_7.this, "闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
					}    				 
				},
				c.get(Calendar.HOUR_OF_DAY),		//传入当前小时数
				c.get(Calendar.MINUTE),			//传入当前分钟数
				false
			);
			break;
		default:break;
		}
		return dialog;
	}
    
}