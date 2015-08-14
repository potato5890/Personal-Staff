package wyf.wpf;						//���������

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
	final int DIALOG_TIME = 0;	//���öԻ���id
	AlarmManager am;		//����AlarmManager����
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//���õ�ǰ��Ļ
        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);	//����AlarmManager����
        Button btn = (Button)findViewById(R.id.btn);			//���Button����
        btn.setOnClickListener(new View.OnClickListener() {		//���ü�����
			@Override
			public void onClick(View v) {		//��дonClick����
				showDialog(DIALOG_TIME);		//��ʾʱ��ѡ��Ի���
			}
		});
    }
	@Override
	protected Dialog onCreateDialog(int id) {		//��дonCreateDialog����
		Dialog dialog = null;
		switch(id){								//��id�����ж�
		case DIALOG_TIME:
			dialog=new TimePickerDialog(				//����TimePickerDialog����
				this,
				new TimePickerDialog.OnTimeSetListener(){ //����OnTimeSetListener������
					@Override
					public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
						Calendar c=Calendar.getInstance();//��ȡ���ڶ���	
						c.setTimeInMillis(System.currentTimeMillis());		//����Calendar����
						c.set(Calendar.HOUR, hourOfDay);		//��������Сʱ��
						c.set(Calendar.MINUTE, minute);			//�������ӵķ�����
						c.set(Calendar.SECOND, 0);				//�������ӵ�����
						c.set(Calendar.MILLISECOND, 0);			//�������ӵĺ�����
						Intent intent = new Intent(Sample_12_7.this,AlarmReceiver.class);	//����Intent����
						PendingIntent pi = PendingIntent.getBroadcast(Sample_12_7.this, 0, intent, 0);	//����PendingIntent
						am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);		//��������
						Toast.makeText(Sample_12_7.this, "�������óɹ�", Toast.LENGTH_LONG).show();//��ʾ�û�
					}    				 
				},
				c.get(Calendar.HOUR_OF_DAY),		//���뵱ǰСʱ��
				c.get(Calendar.MINUTE),			//���뵱ǰ������
				false
			);
			break;
		default:break;
		}
		return dialog;
	}
    
}