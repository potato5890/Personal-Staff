package wyf.wpf;			//声明包语句
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
public class Sample_10_2 extends Activity {
	String stringURL = "http://192.168.9.101:8080/MyUrlSample/msg.txt";
	String bitmapURL = "http://192.168.9.101:8080/MyUrlSample/pic.png";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.btn);		//获得Button控件对象
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {		//重写onClick方法
				getStringURLResources();		//获得字符串资源
				getBitmapURLResources();		//获得图片资源
			}
		});
    }
    //方法，根据指定URL字符串获取网络资源
    public void getStringURLResources(){
    	try{
    		URL myUrl = new URL(stringURL);
    		URLConnection myConn = myUrl.openConnection();	//打开连接
    		InputStream in = myConn.getInputStream();		//获取输入流
    		BufferedInputStream bis = new BufferedInputStream(in);//获取BufferedInputStream对象
    		ByteArrayBuffer baf = new ByteArrayBuffer(bis.available());
    		int data = 0;
    		while((data = bis.read())!= -1){		//读取BufferedInputStream中数据
    			baf.append((byte)data);				//将数据读取到ByteArrayBuffer中
    		}
    		String msg = EncodingUtils.getString(baf.toByteArray(), "UTF-8");	//转换为字符串
    		EditText et = (EditText)findViewById(R.id.et);		//获得EditText对象
    		et.setText(msg);						//设置EditText控件中的内容
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}    
    }
    public void getBitmapURLResources(){
    	try{
    		URL myUrl = new URL(bitmapURL);	//创建URL对象
    		URLConnection myConn = myUrl.openConnection();			//打开连接
    		InputStream in = myConn.getInputStream();			//获得InputStream对象
    		Bitmap bmp = BitmapFactory.decodeStream(in);		//创建Bitmap
    		ImageView iv = (ImageView)findViewById(R.id.iv);	//获得ImageView对象
    		iv.setImageBitmap(bmp);					//设置ImageView显示的内容
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
}