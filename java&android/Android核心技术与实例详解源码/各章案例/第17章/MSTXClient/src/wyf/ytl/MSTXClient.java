package wyf.ytl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
public class MSTXClient extends Activity implements OnClickListener{
	Button loginButton = null;//��¼��ť
	Button registerButton = null;//ע�ᰴť
	EditText uid = null;//�û���
	EditText pwd = null;//����
	CheckBox cb = null;
	ProgressDialog myDialog = null;//���ȿ�
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				Toast.makeText(MSTXClient.this, "�û������������", Toast.LENGTH_LONG).show();
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);		
        setContentView(R.layout.login);//��¼����
        GradientDrawable grad = new GradientDrawable(//����ɫ
        	Orientation.TOP_BOTTOM,
 			new int[]{R.color.red, R.color.yellow}
 		);
 		getWindow().setBackgroundDrawable(grad);//���ý�����ɫ
        
 		loginButton = (Button)findViewById(R.id.loginButton);//�õ���¼��ť������
 		registerButton = (Button)findViewById(R.id.registerButton);//�õ�ע�ᰴť������
 		cb = (CheckBox)findViewById(R.id.cbRemember);//���CheckBox���� 
 		registerButton.setOnClickListener(this);//��Ӽ���
 		loginButton.setOnClickListener(this);//��Ӽ���
 		uid = (EditText)findViewById(R.id.uid);//�õ��û����ı��������
 		pwd = (EditText)findViewById(R.id.pwd);//�õ�����������
 		checkIfRemember();
    }
	@Override
	public void onClick(View v) {//��ť�ļ�������
		if(v == loginButton){//���µ�¼��ť
			String editUid = uid.getText().toString();
			String editPwd = pwd.getText().toString();
			if(editUid.trim().equals("")){//���û���Ϊ��ʱ 
				Toast.makeText(this, "���������û�����", Toast.LENGTH_SHORT).show();
				return;
			}
			if(editPwd.trim().equals("")){//������Ϊ��ʱ
				Toast.makeText(this, "�����������룡", Toast.LENGTH_SHORT).show();
				return;
			}
			myDialog = ProgressDialog.show(this, "����", "���ڼ���...",true);
			new Thread(){//����ʱ�Ķ����ŵ��߳���
				public void run(){//��д��run����
					Socket s = null;
					DataOutputStream dout = null;
					DataInputStream din = null;
					try {
						s = new Socket("192.168.9.100", 9999);//���ӷ�����
						dout = new DataOutputStream(s.getOutputStream());
						din = new DataInputStream(s.getInputStream());
						dout.writeUTF("<#LOGIN#>"+uid.getText().toString()+"|"+pwd.getText().toString());
						String msg = din.readUTF();//���շ�������������Ϣ
						if(msg.startsWith("<#LOGINOK#>")){//��¼�ɹ�
							msg=msg.substring(11);
							Intent intent = new Intent();
							intent.setClass(MSTXClient.this, MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("u_name", msg);
							bundle.putString("uid",uid.getText().toString());
							intent.putExtras(bundle);
							startActivity(intent);
							MSTXClient.this.finish();
						}
						else if(msg.startsWith("<#LOGINERROR#>")){//��¼ʧ��
							myHandler.sendEmptyMessage(1);
						}
					} catch (Exception e) {//�����쳣
						e.printStackTrace();//��ӡ�쳣
					} finally{//ʹ��finally��֤֮������һ����ִ��
						try{
							if(din != null){
								din.close();
								din = null;
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						try{
							if(dout != null){
								dout.close();
								dout = null;
							}							
						}
						catch(Exception e){
							e.printStackTrace();
						}
						try{
							if(s != null){
								s.close();
								s = null;
							}							
						}
						catch(Exception e){
							e.printStackTrace();
						}	
						myDialog.dismiss();
					}
				}
			}.start();
			if(cb.isChecked()){
				rememberMe(uid.getText().toString().trim(),pwd.getText().toString().trim());
			}
		}
		else if(v == registerButton){//����ע�ᰴť
			Intent intent = new Intent();
			intent.setClass(MSTXClient.this, RegisterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("action", "register");
			intent.putExtras(bundle);
			startActivity(intent);
			MSTXClient.this.finish();
		}
	}
	
    public void rememberMe(String uid,String pwd){//���������û���id���������Preferences
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//���Preferences
    	SharedPreferences.Editor editor = sp.edit();			//���Editor
    	editor.putString("uid", uid);							//���û�������Preferences
    	editor.putString("pwd", pwd);							//���������Preferences
    	editor.commit();
    }
    
    public void checkIfRemember(){//��������Preferences�ж�ȡ�û���������
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//���Preferences
    	String uid = sp.getString("uid", null);
    	String pwd = sp.getString("pwd", null);
    	if(uid != null && pwd!= null){
    		EditText etUid = (EditText)findViewById(R.id.uid);
    		EditText etPwd = (EditText)findViewById(R.id.pwd);
    		CheckBox cbRemember = (CheckBox)findViewById(R.id.cbRemember); 
    		etUid.setText(uid);
    		etPwd.setText(pwd);
    		cbRemember.setChecked(true);
    	}
    }
}