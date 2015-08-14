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
	Button registerButton2 = null;//注册界面的"立即注册"按钮
	Button resetButton = null;//注册界面的"重置"按钮
	EditText u_nameEditText = null;//注册界面的用户名
	EditText pwdEditText1 = null;//密码1
	EditText pwdEditText2 = null;//密码2
	EditText qqEditText = null;//QQ号
	EditText emailEditText = null;//电子邮件
	EditText u_disEditText = null;//自我描述
	Socket s = null;//声明Socket的引用
	DataOutputStream dout = null;//输出流
	DataInputStream din = null;//输入流	
	ProgressDialog myDialog = null;//进度框
	Handler myHandler = new Handler(){//创建Handler对象用于根据收到的消息更新UI
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){//收到注册成功消息
				Bundle data = msg.getData();//得到数据
				String u_name = data.getString("u_name");//得到用户名
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, MainActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("u_name", u_name);//向bundle中存放用户名
				intent.putExtras(bundle);//将bundle存放到Intent中
				startActivity(intent);//启动MainActivity
				RegisterActivity.this.finish();	//释放当前的Activity
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) {//Activity创建时被调用
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent intent = this.getIntent();//得到Intent
		Bundle bundle = intent.getExtras();//得到Intent中的bundle
		String action = bundle.getString("action");//得到action数据
		if(action.equals("register")){//进入注册界面
			setContentView(R.layout.register);//设置当前显示的用户界面
			registerButton2 = (Button)findViewById(R.id.registerButton2);
			resetButton = (Button)findViewById(R.id.resetButton);
			registerButton2.setOnClickListener(this);//添加监听
			resetButton.setOnClickListener(this);//添加监听
			//得到各个控件的引用
			u_nameEditText = (EditText)findViewById(R.id.u_nameEditText);
			qqEditText = (EditText)findViewById(R.id.qqEditText);
			pwdEditText1 = (EditText)findViewById(R.id.pwdEditText1);
			pwdEditText2 = (EditText)findViewById(R.id.pwdEditText2);
			emailEditText = (EditText)findViewById(R.id.emailEditText);
			u_disEditText = (EditText)findViewById(R.id.u_disEditText);
		}
    }
	@Override
	public void onClick(View view) {//按钮监听方法
		if(view == registerButton2){//按下注册界面的"立即注册"按钮
			String u_name = u_nameEditText.getText().toString();
			String u_pwd1 = pwdEditText1.getText().toString();
			String u_pwd2 = pwdEditText2.getText().toString();
			if(u_name.trim().equals("")){//当用户名为空时
				Toast.makeText(this,"用户名不能为空!",Toast.LENGTH_SHORT).show();
				return;
			}
			else if(u_pwd1.trim().equals("")){//密码不能为空
				Toast.makeText(this,"密码不能为空!",Toast.LENGTH_SHORT).show();
				return;
			}
			else if(!u_pwd1.trim().equals(u_pwd2.trim())){//两次密码不一致时
				Toast.makeText(this,"两次密码不一致!",Toast.LENGTH_SHORT).show();
				return;			
			}
			myDialog = ProgressDialog.show(this, "进度", "正在加载...",true);
			new Thread(){
				public void run(){
					try{//连接网络并打开流
				        s = new Socket("192.168.9.100", 9999);
				        dout = new DataOutputStream(s.getOutputStream());
				        din = new DataInputStream(s.getInputStream());
					}catch(Exception e){//捕获异常
						e.printStackTrace();//打印异常
					}
					String u_name = u_nameEditText.getText().toString();//用户名
					String u_qq	= qqEditText.getText().toString();//qq号
					String u_pwd1 = pwdEditText1.getText().toString();//密码
					String u_Email = emailEditText.getText().toString();//邮件
					String u_dis = u_disEditText.getText().toString();//描述
					String msg = "<#REGISTER#>"+u_name+"|"+u_pwd1+"|"+u_qq+"|"+u_Email+"|"+u_dis;
					try {
						dout.writeUTF(msg);//向服务器发送注册消息
						String msg2 = din.readUTF();//接收服务器发送来的消息
						if(msg2.startsWith("<#REGISTEROK#>")){//注册成功
							msg2=msg2.substring(14);//截取字串
							String[] str = msg2.split("\\|");//分割字符串
							Message message = new Message();//创建消息
							Bundle data = new Bundle();//创建数据
							data.putString("uid", str[0]);//向bundle中添加数据
							data.putString("u_name", str[1]);//向bundle中添加数据
							message.what = 1;//设置消息的what值
							message.setData(data);//存放数据
							myHandler.sendMessage(message);//发送消息
						}
					} catch (IOException e) {//捕获异常
						e.printStackTrace();//打印异常
					} finally{
						try{
							if(dout != null){
								dout.close();
								dout = null;
							}
						}
						catch(Exception e){//捕获异常
							e.printStackTrace();//打印异常信息
						}
						try{
							if(din != null){
								din.close();
								din = null;
							}
						}
						catch(Exception e){//捕获异常
							e.printStackTrace();//打印异常信息
						}
						try{
							if(s != null){
								s.close();
								s = null;
							}
						}
						catch(Exception e){//捕获异常
							e.printStackTrace();//打印异常信息
						}
						myDialog.dismiss();
					}				
				}
			}.start();
		}
		else if(view == resetButton){//按下重置按钮
			u_nameEditText.setText("");
			pwdEditText1.setText("");
			pwdEditText2.setText("");
			qqEditText.setText("");
			emailEditText.setText("");
			u_disEditText.setText("");

		}
	}
	@Override
	protected void onDestroy() {//Actvity释放时被调用
		super.onDestroy();
		try {
			if(dout != null){
				dout.writeUTF("<#ClientDown#>");//通知服务器客户端退出
			}
		} catch (IOException e) {//捕获异常
			e.printStackTrace();//打印遗产
		}
	}
}