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
	EditText number;//电话号码
	EditText body;//短信内容
	Button send;//发送按钮
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        send = (Button) this.findViewById(R.id.send);//得到发送按钮的引用
        number = (EditText) this.findViewById(R.id.number);//得到电话号码文本框的引用
        body = (EditText) this.findViewById(R.id.body);//得到短信内容文本框的引用
        send.setOnClickListener(this);//添加监听
		IntentFilter myIntentFilter = new IntentFilter("SMS_SEND_ACTION");//创建过滤器
		MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();//创建BroadcastReceiver
		registerReceiver(myBroadcastReceiver, myIntentFilter);//注册BroadcastReceiver
    }
	@Override
	public void onClick(View v) {//监听方法
		if(v == send){//按下发送按钮
			send.setEnabled(false);//设置按钮为不可用
			String strNumber = number.getText().toString();//得到电话号码
			String strBody = body.getText().toString();//得到需要发送的信息
			
			SmsManager smsManager = SmsManager.getDefault();//得到SmsManager
			Intent intentSend = new Intent("SMS_SEND_ACTION");//创建Intent
			
			PendingIntent sendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, intentSend, 0);
			smsManager.sendTextMessage(strNumber, null, strBody, sendPI, null);//发送短信
			send.setEnabled(true);//设置按钮为可用
		}
	}
	public class MyBroadcastReceiver extends BroadcastReceiver{//自定义的BroadcastReceiver
		@Override
		public void onReceive(Context context, Intent intent) {//重写的onReceive方法
			switch(getResultCode()){
			case Activity.RESULT_OK://发送成功
				Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();//提示
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE://发送失败
				Toast.makeText(context, "发送失败", Toast.LENGTH_LONG).show();//提示
				break;
			default://其他情况
				Toast.makeText(context, "未知", Toast.LENGTH_LONG).show();//提示
				break;
			}
		}
	}
}