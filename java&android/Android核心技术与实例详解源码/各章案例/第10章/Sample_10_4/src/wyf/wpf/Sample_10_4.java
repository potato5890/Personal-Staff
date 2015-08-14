package wyf.wpf;								//���������
import android.app.Activity;					//���������
import android.os.Bundle;						//���������
import android.view.View;							//���������
import android.view.Window;						//���������
import android.webkit.URLUtil;					//���������
import android.webkit.WebChromeClient;			//���������
import android.webkit.WebView;				//���������
import android.webkit.WebViewClient;		//���������
import android.widget.Button;				//���������
import android.widget.EditText;				//���������
import android.widget.Toast;				//���������
public class Sample_10_4 extends Activity {
	WebView wv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        wv = (WebView)findViewById(R.id.wv);
        wv.setWebChromeClient(new WebChromeClient(){		//ΪWebView����WebChromeClient
			@Override
			public void onProgressChanged(WebView view, int newProgress) {//��дonProgressChanged����
				Sample_10_4.this.setProgress(newProgress*100);
			}
        });
        wv.setWebViewClient(new WebViewClient() {			//ΪWebView����WebViewClient
        	public void onReceivedError(WebView view, 
        			int errorCode, String description, String failingUrl) {//��дonReceivedError����
        		Toast.makeText(Sample_10_4.this, "Sorry!" + description, Toast.LENGTH_SHORT).show();
        	}
        });
Button btn = (Button)findViewById(R.id.btn);   	//��ȡButton����     
btn.setOnClickListener(new View.OnClickListener() {	//ΪButton��������OnClickListener������
	@Override
	public void onClick(View v) {
		
		EditText et = (EditText)findViewById(R.id.et);		//���WebView����
		String url = et.getText().toString().trim();
		if(URLUtil.isNetworkUrl(url)){		//�ж��Ƿ�����ַ
			wv.loadUrl(url);
		}
		else{
			Toast.makeText(Sample_10_4.this, "�Բ������������ַ�д�", Toast.LENGTH_SHORT).show();
			et.requestFocus();			//�������Ƶ�EditText
		}
	}
});
//����ǰ�����˰�ť
Button btnForward = (Button)findViewById(R.id.btnForward);
btnForward.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		if(wv.canGoForward()){		//�ж��Ƿ��ܹ�ǰ��
			wv.goForward();
		}
		else{
			Toast.makeText(Sample_10_4.this, "�Բ��������ڲ���ǰ����", Toast.LENGTH_SHORT).show();
		}
	}
});
Button btnBack = (Button)findViewById(R.id.btnBack);
btnBack.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		if(wv.canGoBack()){		//�ж��Ƿ��ܹ�ǰ��
			wv.goBack();
		}
		else{
			Toast.makeText(Sample_10_4.this, "�Բ��������ڲ��ܺ��ˣ�", Toast.LENGTH_SHORT).show();
		}
	}
});
    }
}