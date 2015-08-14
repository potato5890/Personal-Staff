package wyf.wpf;							//声明包语句
import java.io.DataInputStream;				//引入相关类
import java.net.Socket;						//引入相关类
import android.app.Activity;				//引入相关类
import android.os.Bundle;					//引入相关类
import android.widget.EditText;				//引入相关类
public class Sample_10_1_Client extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//设置当前屏幕
        connectToServer();						//连接服务端
    }
    public void connectToServer(){					//方法：连接客户端
    	try{
    		Socket socket = new Socket("192.168.9.100", 8888);//创建Socket对象
    		DataInputStream din = new DataInputStream(socket.getInputStream());	//获得DataInputStream对象
    		String msg = din.readUTF();				 			//读取服务端发来的消息
    		EditText et = (EditText)findViewById(R.id.et);		//获得EditText对象
    		et.setText(msg);									//设置EditText对象
    	}
    	catch(Exception e){										//捕获并打印异常
    		e.printStackTrace();
    	}
    }
}