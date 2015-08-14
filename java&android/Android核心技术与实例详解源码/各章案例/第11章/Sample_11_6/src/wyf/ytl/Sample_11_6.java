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
			case TelephonyManager.CALL_STATE_IDLE://待机状态
				Toast.makeText(Sample_11_6.this, "当前手机为待机状态", Toast.LENGTH_LONG).show();
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://通话中
				Toast.makeText(Sample_11_6.this, "当前手机为通话状态", Toast.LENGTH_LONG).show();
				break;
			case TelephonyManager.CALL_STATE_RINGING://来电状态
				if(incomingNumber.equals(phoneNumber)){//是需要过滤的电话时
					Toast.makeText(Sample_11_6.this, "黑名单来电，可自行处理", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(Sample_11_6.this, "当前手机为来电状态", Toast.LENGTH_LONG).show();
				}
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
    }
}