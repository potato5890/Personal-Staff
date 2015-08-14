package wyf.ytl;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Sample_11_4 extends Activity implements OnClickListener{
	EditText number;//�绰����
	EditText body;//��������
	Button send;//���Ͱ�ť
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ���û�����
        send = (Button) this.findViewById(R.id.send);//�õ����Ͱ�ť������
        number = (EditText) this.findViewById(R.id.number);//�õ��绰�����ı��������
        body = (EditText) this.findViewById(R.id.body);//�õ����������ı��������
        send.setOnClickListener(this);//��Ӽ���
		IntentFilter myIntentFilter = new IntentFilter("SMS_SEND_ACTION");//����������
		MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();//����BroadcastReceiver
		registerReceiver(myBroadcastReceiver, myIntentFilter);//ע��BroadcastReceiver
    }
	@Override
	public void onClick(View v) {//��������
		if(v == send){//���·��Ͱ�ť
			send.setEnabled(false);//���ð�ťΪ������
			String strNumber = number.getText().toString();//�õ��绰����
			String strBody = body.getText().toString();//�õ���Ҫ���͵���Ϣ
			
			SmsManager smsManager = SmsManager.getDefault();//�õ�SmsManager
			Intent intentSend = new Intent("SMS_SEND_ACTION");//����Intent
			
			PendingIntent sendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, intentSend, 0);
			smsManager.sendTextMessage(strNumber, null, strBody, sendPI, null);//���Ͷ���
			send.setEnabled(true);//���ð�ťΪ����
		}
	}
	public class MyBroadcastReceiver extends BroadcastReceiver{//�Զ����BroadcastReceiver
		@Override
		public void onReceive(Context context, Intent intent) {//��д��onReceive����
			switch(getResultCode()){
			case Activity.RESULT_OK://���ͳɹ�
				Toast.makeText(context, "���ͳɹ�", Toast.LENGTH_LONG).show();//��ʾ
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE://����ʧ��
				Toast.makeText(context, "����ʧ��", Toast.LENGTH_LONG).show();//��ʾ
				break;
			default://�������
				Toast.makeText(context, "δ֪", Toast.LENGTH_LONG).show();//��ʾ
				break;
			}
		}
	}
}