package wyf.ytl;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.widget.Toast;
public class MyBroadcastReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){//�յ����Ƕ���
			Bundle bundle = intent.getExtras();
			if(bundle != null){
				Object[] myObject = (Object[])bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[myObject.length];
				for(int i=0; i<myObject.length; i++){
					messages[i] = SmsMessage.createFromPdu((byte[])myObject[i]);
				}
				StringBuilder sb = new StringBuilder();
				for(SmsMessage tempSmsMessage : messages){
					sb.append("�յ����ԣ�\n");
					sb.append(tempSmsMessage.getDisplayOriginatingAddress()+"\n");
					sb.append("����Ϊ��\n");
					sb.append(tempSmsMessage.getDisplayMessageBody());
				}
				Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
