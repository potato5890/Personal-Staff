package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
public class Sample_11_6 extends Activity {
	String phoneNumber = "5556";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyPhoneStateListener myPhoneStateListene = new MyPhoneStateListener();
        TelephonyManager telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telManager.listen(myPhoneStateListene, MyPhoneStateListener.LISTEN_CALL_STATE);
    }
    public class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch(state){
			case TelephonyManager.CALL_STATE_IDLE://����״̬
				Toast.makeText(Sample_11_6.this, "��ǰ�ֻ�Ϊ����״̬", Toast.LENGTH_LONG).show();
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://ͨ����
				Toast.makeText(Sample_11_6.this, "��ǰ�ֻ�Ϊͨ��״̬", Toast.LENGTH_LONG).show();
				break;
			case TelephonyManager.CALL_STATE_RINGING://����״̬
				if(incomingNumber.equals(phoneNumber)){//����Ҫ���˵ĵ绰ʱ
					Toast.makeText(Sample_11_6.this, "���������磬�����д���", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(Sample_11_6.this, "��ǰ�ֻ�Ϊ����״̬", Toast.LENGTH_LONG).show();
				}
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
    }
}