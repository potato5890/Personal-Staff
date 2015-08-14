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
        bdial.setOnClickListener(// Ϊ���Ͱ�ť��Ӽ�����
                //OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
                new View.OnClickListener(){ 
                	public void onClick(View v){ 
                		//��ȡ����ĵ绰����
                		EditText etTel=(EditText)findViewById(R.id.EditText02);
                		String telStr=etTel.getText().toString();
                		//��ȡ����Ķ�������
                		EditText etSms=(EditText)findViewById(R.id.EditText01);
                		String smsStr=etSms.getText().toString();
                		//�жϺ����ַ����Ƿ�Ϸ�
                		if(PhoneNumberUtils.isGlobalPhoneNumber(telStr)){//�Ϸ����Ͷ���
	                		 v.setEnabled(false);//���ŷ������ǰ�����Ͱ�ť����Ϊ������
	                		 sendSMS(telStr,smsStr,v);
                		}
                		else{//���Ϸ�����ʾ
                			Toast.makeText(
                				 Sample_11_1.this, //������
                				 "�绰���벻���ϸ�ʽ������", //��ʾ����
                				 5000						//��Ϣ��ʾʱ��
                				 ).show();                		 
                		}           	 
                 } 
        }); 
    }
    //�Լ�������ֱ�ӷ��Ͷ��ŵķ���
    private void sendSMS(String telNo,String smsStr,View v){
    	PendingIntent pi=
    		PendingIntent.getActivity(this, 0, new Intent(this,Sample_11_1.class), 0);
    	SmsManager sms=SmsManager.getDefault();
    	sms.sendTextMessage(telNo, null, smsStr, pi, null);
    	//���ŷ��ͳɹ�������ʾ
    	Toast.makeText(
    			Sample_11_1.this, //������
				 "��ϲ�㣬���ŷ��ͳɹ���", //��ʾ����
				 5000						//��Ϣ��ʾʱ��
				 ).show(); 
    	v.setEnabled(true);//���ŷ�����ɺ�ָ����Ͱ�ť�Ŀ���״̬
    }
}