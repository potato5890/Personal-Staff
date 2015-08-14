package wyf.wpf;			//���������
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
        Button btn = (Button)findViewById(R.id.btn);		//���Button�ؼ�����
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {		//��дonClick����
				getStringURLResources();		//����ַ�����Դ
				getBitmapURLResources();		//���ͼƬ��Դ
			}
		});
    }
    //����������ָ��URL�ַ�����ȡ������Դ
    public void getStringURLResources(){
    	try{
    		URL myUrl = new URL(stringURL);
    		URLConnection myConn = myUrl.openConnection();	//������
    		InputStream in = myConn.getInputStream();		//��ȡ������
    		BufferedInputStream bis = new BufferedInputStream(in);//��ȡBufferedInputStream����
    		ByteArrayBuffer baf = new ByteArrayBuffer(bis.available());
    		int data = 0;
    		while((data = bis.read())!= -1){		//��ȡBufferedInputStream������
    			baf.append((byte)data);				//�����ݶ�ȡ��ByteArrayBuffer��
    		}
    		String msg = EncodingUtils.getString(baf.toByteArray(), "UTF-8");	//ת��Ϊ�ַ���
    		EditText et = (EditText)findViewById(R.id.et);		//���EditText����
    		et.setText(msg);						//����EditText�ؼ��е�����
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}    
    }
    public void getBitmapURLResources(){
    	try{
    		URL myUrl = new URL(bitmapURL);	//����URL����
    		URLConnection myConn = myUrl.openConnection();			//������
    		InputStream in = myConn.getInputStream();			//���InputStream����
    		Bitmap bmp = BitmapFactory.decodeStream(in);		//����Bitmap
    		ImageView iv = (ImageView)findViewById(R.id.iv);	//���ImageView����
    		iv.setImageBitmap(bmp);					//����ImageView��ʾ������
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
}