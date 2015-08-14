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
	String urlPost = "http://192.168.9.101:8080/MyHttpSample/response.jsp";	//POST�����URL	
	String urlGet = "http://www.google.com";								//GET�����URL
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);						//���õ�ǰ��Ļ
        Button btnPost = (Button)findViewById(R.id.btnPost);	//��ȡButton�ؼ�����		
        btnPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {			//��дonClick����
				HttpPost httpPostRequest = new HttpPost(urlPost);		//����HttpPost����
				List<NameValuePair> httpParams = new ArrayList<NameValuePair>();	//������Ų�����ArrayList
				httpParams.add(new BasicNameValuePair("name", "Java"));		//����post����
				try {
					httpPostRequest.setEntity(new UrlEncodedFormEntity(httpParams,HTTP.UTF_8));
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpPostRequest);
					if(httpResponse.getStatusLine().getStatusCode()==200){		//���ӳɹ�
						String result = EntityUtils.toString(httpResponse.getEntity());	//�����Դ
						result = result.replaceAll("\r\n|\n\r|\r|\n", "");				//ȥ����Ϣ�еĻس��ͻ���
						EditText etPost = (EditText)findViewById(R.id.etPost);		//���EditText����
						etPost.setText(result);									//ΪEditText��������
					}
				} catch (Exception e) {										//���񲢴�ӡ�쳣
					EditText etPost = (EditText)findViewById(R.id.etPost);		//���EditText����
					etPost.setText("���ӳ���:"+e.getMessage());							//ΪEditText���ó�����Ϣ
				}
			}
		});
        Button btnGet = (Button)findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpGet httpGetRequest = new HttpGet(urlGet);		//����HttpPost����
				try {
					HttpResponse httpResponse = new DefaultHttpClient().execute(httpGetRequest);
					if(httpResponse.getStatusLine().getStatusCode()==200){		//���ӳɹ�
						String result = EntityUtils.toString(httpResponse.getEntity());	//�����Դ
						result = result.replaceAll("\r\n|\n\r|\r|\n", "");				//ȥ����Ϣ�еĻس��ͻ���
						EditText etGet = (EditText)findViewById(R.id.etGet);		//���EditText����
						etGet.setText(result);									//ΪEditText��������
					}
				} catch (Exception e) {										//���񲢴�ӡ�쳣
					EditText etGet = (EditText)findViewById(R.id.etGet);		//���EditText����
					etGet.setText("���ӳ���:"+e.getMessage());							//ΪEditText���ó�����Ϣ
				}
			}
		});
    }
}