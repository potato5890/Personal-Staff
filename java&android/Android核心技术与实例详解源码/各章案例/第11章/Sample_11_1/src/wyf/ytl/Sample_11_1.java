package wyf.ytl;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Sample_11_1 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bdial=(Button)this.findViewById(R.id.Button01);
        bdial.setOnClickListener(// 为发送按钮添加监听器
                //OnClickListener为View的内部接口，其实现者负责监听鼠标点击事件
                new View.OnClickListener(){ 
                	public void onClick(View v){ 
                		//获取输入的电话号码
                		EditText etTel=(EditText)findViewById(R.id.EditText02);
                		String telStr=etTel.getText().toString();
                		//获取输入的短信内容
                		EditText etSms=(EditText)findViewById(R.id.EditText01);
                		String smsStr=etSms.getText().toString();
                		//判断号码字符串是否合法
                		if(PhoneNumberUtils.isGlobalPhoneNumber(telStr)){//合法则发送短信
	                		 v.setEnabled(false);//短信发送完成前将发送按钮设置为不可用
	                		 sendSMS(telStr,smsStr,v);
                		}
                		else{//不合法则提示
                			Toast.makeText(
                				 Sample_11_1.this, //上下文
                				 "电话号码不符合格式！！！", //提示内容
                				 5000						//信息显示时间
                				 ).show();                		 
                		}           	 
                 } 
        }); 
    }
    //自己开发的直接发送短信的方法
    private void sendSMS(String telNo,String smsStr,View v){
    	PendingIntent pi=
    		PendingIntent.getActivity(this, 0, new Intent(this,Sample_11_1.class), 0);
    	SmsManager sms=SmsManager.getDefault();
    	sms.sendTextMessage(telNo, null, smsStr, pi, null);
    	//短信发送成功给予提示
    	Toast.makeText(
    			Sample_11_1.this, //上下文
				 "恭喜你，短信发送成功！", //提示内容
				 5000						//信息显示时间
				 ).show(); 
    	v.setEnabled(true);//短信发送完成后恢复发送按钮的可用状态
    }
}