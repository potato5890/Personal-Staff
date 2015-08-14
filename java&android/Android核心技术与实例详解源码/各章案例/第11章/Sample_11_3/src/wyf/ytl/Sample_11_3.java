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
	Button select;//ѡ����ϵ�˰�ť
	Button send;//����
	EditText people;//�Լ�ѡ�����ϵ��
	HashMap<String, String> peoples = new HashMap<String, String>();//�洢����ѡ�������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ��ʾ���û�����
        select = (Button) this.findViewById(R.id.select);//�õ�select��ť
        send = (Button) this.findViewById(R.id.send);//�õ�send��ť
        people = (EditText) this.findViewById(R.id.people);//�õ�people��ť
        select.setOnClickListener(this);//���ü���
        send.setOnClickListener(this);//���ü���
    }
	@Override
	public void onClick(View v) {//��д�İ�ť��������
		if(v == select){//������ѡ����ϵ�˰�ť
			Uri uri = Uri.parse("content://contacts/people");
			Intent intent = new Intent(Intent.ACTION_PICK, uri);//����Intent
			startActivityForResult(intent, 1);//�л���ͨѶ¼
		}
		else if(v == send){//���·��Ͱ�ť
			v.setEnabled(false);//���ð�ťΪ������
    		//��ȡ����Ķ�������
    		EditText etSms=(EditText)findViewById(R.id.smsBody);//�õ�EditText�ؼ�������
    		String smsStr=etSms.getText().toString();//�õ����ŵ��ı�
		    Set keySet = peoples.keySet();//�õ���ֵ����
		    Iterator ii = keySet.iterator();
		    people.setText("");//�ÿ�
		    while(ii.hasNext()){//ѭ��
		    	Object key = ii.next();//�õ���ֵ
		    	String tempName = (String)key;//����
		    	String tempPhone = peoples.get(key);//�õ��绰����
	    		//�жϺ����ַ����Ƿ�Ϸ�
	    		if(PhoneNumberUtils.isGlobalPhoneNumber(tempPhone)){//�Ϸ����Ͷ���
	        		 sendSMS(tempPhone,smsStr,v);//���Ͷ��� 
	    		}
		    }
		}
	}
    private void sendSMS(String telNo,String smsStr,View v){//�Լ�������ֱ�ӷ��Ͷ��ŵķ���
    	PendingIntent pi=
    		PendingIntent.getActivity(this, 0, new Intent(this,Sample_11_3.class), 0);
    	SmsManager sms=SmsManager.getDefault();
    	sms.sendTextMessage(telNo, null, smsStr, pi, null);//���Ͷ���
    	v.setEnabled(true);//���ŷ�����ɺ�ָ����Ͱ�ť�Ŀ���״̬
    }	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1){//requestCode���뷢��ʱ��ͬʱ
			Uri myUri = data.getData();
			if(myUri != null){//����Ϊ��ʱ
				try{
					//�õ�ContentResolver����  
				    ContentResolver cr = getContentResolver(); 
				    Cursor c = managedQuery(myUri, null, null, null, null);
				    c.moveToFirst();
				    // ȡ����ϵ������  
				    int nameFieldColumnIndex = c.getColumnIndex(PhoneLookup.DISPLAY_NAME);  
				    String sName = c.getString(nameFieldColumnIndex); //�õ�����
				    //ȡ����ϵ��ID  
				    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));  
				    Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "  
				            + contactId, null, null);  
				    // ȡ�õ绰����(�����ڶ�����룬ֻȡһ��)
				    String strPhoneNumber = ""; 
				    if(phone.moveToNext()){//�õ�һ���绰����
				    	strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));  
				    }
				    peoples.put(sName, strPhoneNumber);//��ŵ�������
				    Set keySet = peoples.keySet();//��ֵ����
				    Iterator ii = keySet.iterator();
				    people.setText("");//�ÿ�
				    while(ii.hasNext()){
				    	Object key = ii.next();//�õ���ֵ
				    	String tempName = (String)key;//����
				    	String tempPhone = peoples.get(key);//�õ��绰����
				    	people.setText(people.getText() + tempName + ":" + tempPhone+"\n");
				    }
				}catch(Exception e){//�����쳣
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	
}