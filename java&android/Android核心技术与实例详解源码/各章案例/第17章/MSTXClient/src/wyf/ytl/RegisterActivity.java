package wyf.ytl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RegisterActivity extends Activity implements OnClickListener{
	Button registerButton2 = null;//ע������"����ע��"��ť
	Button resetButton = null;//ע������"����"��ť
	EditText u_nameEditText = null;//ע�������û���
	EditText pwdEditText1 = null;//����1
	EditText pwdEditText2 = null;//����2
	EditText qqEditText = null;//QQ��
	EditText emailEditText = null;//�����ʼ�
	EditText u_disEditText = null;//��������
	Socket s = null;//����Socket������
	DataOutputStream dout = null;//�����
	DataInputStream din = null;//������	
	ProgressDialog myDialog = null;//���ȿ�
	Handler myHandler = new Handler(){//����Handler�������ڸ����յ�����Ϣ����UI
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){//�յ�ע��ɹ���Ϣ
				Bundle data = msg.getData();//�õ�����
				String u_name = data.getString("u_name");//�õ��û���
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("u_name", u_name);//��bundle�д���û���
				intent.putExtras(bundle);//��bundle��ŵ�Intent��
				startActivity(intent);//����MainActivity
				RegisterActivity.this.finish();	//�ͷŵ�ǰ��Activity
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) {//Activity����ʱ������
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent intent = this.getIntent();//�õ�Intent
		Bundle bundle = intent.getExtras();//�õ�Intent�е�bundle
		String action = bundle.getString("action");//�õ�action����
		if(action.equals("register")){//����ע�����
			setContentView(R.layout.register);//���õ�ǰ��ʾ���û�����
			registerButton2 = (Button)findViewById(R.id.registerButton2);
			resetButton = (Button)findViewById(R.id.resetButton);
			registerButton2.setOnClickListener(this);//��Ӽ���
			resetButton.setOnClickListener(this);//��Ӽ���
			//�õ������ؼ�������
			u_nameEditText = (EditText)findViewById(R.id.u_nameEditText);
			qqEditText = (EditText)findViewById(R.id.qqEditText);
			pwdEditText1 = (EditText)findViewById(R.id.pwdEditText1);
			pwdEditText2 = (EditText)findViewById(R.id.pwdEditText2);
			emailEditText = (EditText)findViewById(R.id.emailEditText);
			u_disEditText = (EditText)findViewById(R.id.u_disEditText);
		}
    }
	@Override
	public void onClick(View view) {//��ť��������
		if(view == registerButton2){//����ע������"����ע��"��ť
			String u_name = u_nameEditText.getText().toString();
			String u_pwd1 = pwdEditText1.getText().toString();
			String u_pwd2 = pwdEditText2.getText().toString();
			if(u_name.trim().equals("")){//���û���Ϊ��ʱ
				Toast.makeText(this,"�û�������Ϊ��!",Toast.LENGTH_SHORT).show();
				return;
			}
			else if(u_pwd1.trim().equals("")){//���벻��Ϊ��
				Toast.makeText(this,"���벻��Ϊ��!",Toast.LENGTH_SHORT).show();
				return;
			}
			else if(!u_pwd1.trim().equals(u_pwd2.trim())){//�������벻һ��ʱ
				Toast.makeText(this,"�������벻һ��!",Toast.LENGTH_SHORT).show();
				return;			
			}
			myDialog = ProgressDialog.show(this, "����", "���ڼ���...",true);
			new Thread(){
				public void run(){
					try{//�������粢����
				        s = new Socket("192.168.9.100", 9999);
				        dout = new DataOutputStream(s.getOutputStream());
				        din = new DataInputStream(s.getInputStream());
					}catch(Exception e){//�����쳣
						e.printStackTrace();//��ӡ�쳣
					}
					String u_name = u_nameEditText.getText().toString();//�û���
					String u_qq	= qqEditText.getText().toString();//qq��
					String u_pwd1 = pwdEditText1.getText().toString();//����
					String u_Email = emailEditText.getText().toString();//�ʼ�
					String u_dis = u_disEditText.getText().toString();//����
					String msg = "<#REGISTER#>"+u_name+"|"+u_pwd1+"|"+u_qq+"|"+u_Email+"|"+u_dis;
					try {
						dout.writeUTF(msg);//�����������ע����Ϣ
						String msg2 = din.readUTF();//���շ���������������Ϣ
						if(msg2.startsWith("<#REGISTEROK#>")){//ע��ɹ�
							msg2=msg2.substring(14);//��ȡ�ִ�
							String[] str = msg2.split("\\|");//�ָ��ַ���
							Message message = new Message();//������Ϣ
							Bundle data = new Bundle();//��������
							data.putString("uid", str[0]);//��bundle���������
							data.putString("u_name", str[1]);//��bundle���������
							message.what = 1;//������Ϣ��whatֵ
							message.setData(data);//�������
							myHandler.sendMessage(message);//������Ϣ
						}
					} catch (IOException e) {//�����쳣
						e.printStackTrace();//��ӡ�쳣
					} finally{
						try{
							if(dout != null){
								dout.close();
								dout = null;
							}
						}
						catch(Exception e){//�����쳣
							e.printStackTrace();//��ӡ�쳣��Ϣ
						}
						try{
							if(din != null){
								din.close();
								din = null;
							}
						}
						catch(Exception e){//�����쳣
							e.printStackTrace();//��ӡ�쳣��Ϣ
						}
						try{
							if(s != null){
								s.close();
								s = null;
							}
						}
						catch(Exception e){//�����쳣
							e.printStackTrace();//��ӡ�쳣��Ϣ
						}
						myDialog.dismiss();
					}				
				}
			}.start();
		}
		else if(view == resetButton){//�������ð�ť
			u_nameEditText.setText("");
			pwdEditText1.setText("");
			pwdEditText2.setText("");
			qqEditText.setText("");
			emailEditText.setText("");
			u_disEditText.setText("");

		}
	}
	@Override
	protected void onDestroy() {//Actvity�ͷ�ʱ������
		super.onDestroy();
		try {
			if(dout != null){
				dout.writeUTF("<#ClientDown#>");//֪ͨ�������ͻ����˳�
			}
		} catch (IOException e) {//�����쳣
			e.printStackTrace();//��ӡ�Ų�
		}
	}
}