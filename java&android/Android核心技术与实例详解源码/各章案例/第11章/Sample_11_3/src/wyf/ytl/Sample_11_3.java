package wyf.ytl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneNumberUtils;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class Sample_11_3 extends Activity implements OnClickListener{
	Button select;//选择联系人按钮
	Button send;//发送
	EditText people;//以及选择的联系人
	HashMap<String, String> peoples = new HashMap<String, String>();//存储着算选择的所有
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前显示的用户界面
        select = (Button) this.findViewById(R.id.select);//得到select按钮
        send = (Button) this.findViewById(R.id.send);//得到send按钮
        people = (EditText) this.findViewById(R.id.people);//得到people按钮
        select.setOnClickListener(this);//设置监听
        send.setOnClickListener(this);//设置监听
    }
	@Override
	public void onClick(View v) {//重写的按钮监听方法
		if(v == select){//按下了选择联系人按钮
			Uri uri = Uri.parse("content://contacts/people");
			Intent intent = new Intent(Intent.ACTION_PICK, uri);//创建Intent
			startActivityForResult(intent, 1);//切换到通讯录
		}
		else if(v == send){//按下发送按钮
			v.setEnabled(false);//设置按钮为不可用
    		//获取输入的短信内容
    		EditText etSms=(EditText)findViewById(R.id.smsBody);//得到EditText控件的引用
    		String smsStr=etSms.getText().toString();//得到短信的文本
		    Set keySet = peoples.keySet();//得到键值集合
		    Iterator ii = keySet.iterator();
		    people.setText("");//置空
		    while(ii.hasNext()){//循环
		    	Object key = ii.next();//得到键值
		    	String tempName = (String)key;//姓名
		    	String tempPhone = peoples.get(key);//得到电话号码
	    		//判断号码字符串是否合法
	    		if(PhoneNumberUtils.isGlobalPhoneNumber(tempPhone)){//合法则发送短信
	        		 sendSMS(tempPhone,smsStr,v);//发送短信 
	    		}
		    }
		}
	}
    private void sendSMS(String telNo,String smsStr,View v){//自己开发的直接发送短信的方法
    	PendingIntent pi=
    		PendingIntent.getActivity(this, 0, new Intent(this,Sample_11_3.class), 0);
    	SmsManager sms=SmsManager.getDefault();
    	sms.sendTextMessage(telNo, null, smsStr, pi, null);//发送短信
    	v.setEnabled(true);//短信发送完成后恢复发送按钮的可用状态
    }	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1){//requestCode码与发送时相同时
			Uri myUri = data.getData();
			if(myUri != null){//当不为空时
				try{
					//得到ContentResolver对象  
				    ContentResolver cr = getContentResolver(); 
				    Cursor c = managedQuery(myUri, null, null, null, null);
				    c.moveToFirst();
				    // 取得联系人名字  
				    int nameFieldColumnIndex = c.getColumnIndex(PhoneLookup.DISPLAY_NAME);  
				    String sName = c.getString(nameFieldColumnIndex); //得到姓名
				    //取得联系人ID  
				    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));  
				    Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "  
				            + contactId, null, null);  
				    // 取得电话号码(当存在多个号码，只取一个)
				    String strPhoneNumber = ""; 
				    if(phone.moveToNext()){//得到一个电话号码
				    	strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  
				    }
				    peoples.put(sName, strPhoneNumber);//存放到容器中
				    Set keySet = peoples.keySet();//键值集合
				    Iterator ii = keySet.iterator();
				    people.setText("");//置空
				    while(ii.hasNext()){
				    	Object key = ii.next();//得到键值
				    	String tempName = (String)key;//姓名
				    	String tempPhone = peoples.get(key);//得到电话号码
				    	people.setText(people.getText() + tempName + ":" + tempPhone+"\n");
				    }
				}catch(Exception e){//捕获异常
					e.printStackTrace();//打印异常信息
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	
}