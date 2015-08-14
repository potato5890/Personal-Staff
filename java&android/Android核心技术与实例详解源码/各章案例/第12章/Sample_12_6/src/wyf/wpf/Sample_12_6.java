package wyf.wpf;						//���������

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
    public void onCreate(Bundle savedInstanceState) {		//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mbr = new MyBatteryReceiver();					//����Broadcast�������
        ToggleButton tb = (ToggleButton)findViewById(R.id.tb);		//���ToggleButton����
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//���ü�����
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);	//����3��Action˵˵��
					registerReceiver(mbr, filter);					//ע��BroadcastReceiver
				}
				else{
					unregisterReceiver(mbr);			//ȡ��ע���BroadcastReceiver
					TextView tv = (TextView)findViewById(R.id.tv);
					tv.setText(null);					//���TextView����ʾ������
				}
			}
		});
    }
    private class MyBatteryReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {		//��дonReceiver����
			int current = intent.getExtras().getInt("level");		//��õ�ǰ����
			int total = intent.getExtras().getInt("scale");			//����ܵ���
			int percent = current*100/total;		//����ٷֱ�
			TextView tv = (TextView)findViewById(R.id.tv);			//���TextView����
			tv.setText("���ڵĵ����ǣ�"+percent+"%��");				//����TextView��ʾ������
		}
    }
}