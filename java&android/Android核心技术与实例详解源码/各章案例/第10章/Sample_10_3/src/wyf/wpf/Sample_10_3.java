package wyf.wpf;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class Sample_10_3 extends Activity {
	String urlPost = "http://192.168.9.101:8080/MyHttpSample/response.jsp";	//POST请求的URL	
	String urlGet = "http://www.google.com";								//GET请求的URL
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);						//设置当前屏幕
        Button btnPost = (Button)findViewById(R.id.btnPost);	//获取Button控件对象		
        btnPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {			//重写onClick方法
				HttpPost httpPostRequest = new HttpPost(urlPost);		//创建HttpPost对象
				List<NameValuePair> httpParams = new ArrayList<NameValuePair>();	//创建存放参数的ArrayList
				httpParams.add(new BasicNameValuePair("name", "Java"));		//设置post参数
				try {
					httpPostRequest.setEntity(new UrlEncodedFormEntity(httpParams,HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpPostRequest);
					if(httpResponse.getStatusLine().getStatusCode()==200){		//连接成功
						String result = EntityUtils.toString(httpResponse.getEntity());	//获得资源
						result = result.replaceAll("\r\n|\n\r|\r|\n", "");				//去掉信息中的回车和换行
						EditText etPost = (EditText)findViewById(R.id.etPost);		//获得EditText对象
						etPost.setText(result);									//为EditText设置内容
					}
				} catch (Exception e) {										//捕获并打印异常
					EditText etPost = (EditText)findViewById(R.id.etPost);		//获得EditText对象
					etPost.setText("连接出错:"+e.getMessage());							//为EditText设置出错信息
				}
			}
		});
        Button btnGet = (Button)findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpGet httpGetRequest = new HttpGet(urlGet);		//创建HttpPost对象
				try {
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpGetRequest);
					if(httpResponse.getStatusLine().getStatusCode()==200){		//连接成功
						String result = EntityUtils.toString(httpResponse.getEntity());	//获得资源
						result = result.replaceAll("\r\n|\n\r|\r|\n", "");				//去掉信息中的回车和换行
						EditText etGet = (EditText)findViewById(R.id.etGet);		//获得EditText对象
						etGet.setText(result);									//为EditText设置内容
					}
				} catch (Exception e) {										//捕获并打印异常
					EditText etGet = (EditText)findViewById(R.id.etGet);		//获得EditText对象
					etGet.setText("连接出错:"+e.getMessage());							//为EditText设置出错信息
				}
			}
		});
    }
}