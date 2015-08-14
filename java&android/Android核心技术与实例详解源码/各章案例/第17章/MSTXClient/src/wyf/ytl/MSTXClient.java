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
	Button loginButton = null;//登录按钮
	Button registerButton = null;//注册按钮
	EditText uid = null;//用户名
	EditText pwd = null;//密码
	CheckBox cb = null;
	ProgressDialog myDialog = null;//进度框
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				Toast.makeText(MSTXClient.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);		
        setContentView(R.layout.login);//登录界面
        GradientDrawable grad = new GradientDrawable(//渐变色
        	Orientation.TOP_BOTTOM,
 			new int[]{R.color.red, R.color.yellow}
 		);
 		getWindow().setBackgroundDrawable(grad);//设置渐变颜色
        
 		loginButton = (Button)findViewById(R.id.loginButton);//得到登录按钮的引用
 		registerButton = (Button)findViewById(R.id.registerButton);//得到注册按钮的引用
 		cb = (CheckBox)findViewById(R.id.cbRemember);//获得CheckBox对象 
 		registerButton.setOnClickListener(this);//添加监听
 		loginButton.setOnClickListener(this);//添加监听
 		uid = (EditText)findViewById(R.id.uid);//得到用户名文本框的引用
 		pwd = (EditText)findViewById(R.id.pwd);//得到密码框的引用
 		checkIfRemember();
    }
	@Override
	public void onClick(View v) {//按钮的监听方法
		if(v == loginButton){//按下登录按钮
			String editUid = uid.getText().toString();
			String editPwd = pwd.getText().toString();
			if(editUid.trim().equals("")){//当用户名为空时 
				Toast.makeText(this, "请您输入用户名！", Toast.LENGTH_SHORT).show();
				return;
			}
			if(editPwd.trim().equals("")){//当密码为空时
				Toast.makeText(this, "请您输入密码！", Toast.LENGTH_SHORT).show();
				return;
			}
			myDialog = ProgressDialog.show(this, "进度", "正在加载...",true);
			new Thread(){//将耗时的动作放到线程中
				public void run(){//重写的run方法
					Socket s = null;
					DataOutputStream dout = null;
					DataInputStream din = null;
					try {
						s = new Socket("192.168.9.100", 9999);//连接服务器
						dout = new DataOutputStream(s.getOutputStream());
						din = new DataInputStream(s.getInputStream());
						dout.writeUTF("<#LOGIN#>"+uid.getText().toString()+"|"+pwd.getText().toString());
						String msg = din.readUTF();//接收服务器发来的消息
						if(msg.startsWith("<#LOGINOK#>")){//登录成功
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
						else if(msg.startsWith("<#LOGINERROR#>")){//登录失败
							myHandler.sendEmptyMessage(1);
						}
					} catch (Exception e) {//捕获异常
						e.printStackTrace();//打印异常
					} finally{//使用finally保证之后的语句一定被执行
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
		else if(v == registerButton){//按下注册按钮
			Intent intent = new Intent();
			intent.setClass(MSTXClient.this, RegisterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("action", "register");
			intent.putExtras(bundle);
			startActivity(intent);
			MSTXClient.this.finish();
		}
	}
	
    public void rememberMe(String uid,String pwd){//方法：将用户的id和密码存入Preferences
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
    	SharedPreferences.Editor editor = sp.edit();			//获得Editor
    	editor.putString("uid", uid);							//将用户名存入Preferences
    	editor.putString("pwd", pwd);							//将密码存入Preferences
    	editor.commit();
    }
    
    public void checkIfRemember(){//方法：从Preferences中读取用户名和密码
    	SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
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