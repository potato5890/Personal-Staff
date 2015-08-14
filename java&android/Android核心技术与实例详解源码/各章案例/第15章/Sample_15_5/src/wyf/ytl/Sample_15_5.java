package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
public class Sample_15_5 extends Activity {
	EditText myEditText;//���ڽ����û�����
	WebView myWebView;//WebView���û���ʾhtml
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ���û�����
        myEditText = (EditText) findViewById(R.id.myEditText);//�õ�EditText������ 
        myWebView = (WebView) findViewById(R.id.myWebView);//�õ�WebView������
        WebSettings myWebSettings = myWebView.getSettings();//ȡ��WebSettings
        myWebSettings.setJavaScriptEnabled(true);//���ÿ�������JavaScript
        myWebView.addJavascriptInterface(this, "ytl");//���ø�html���õĶ�������
        String url = "file:///android_asset/translate.html";
        myWebView.loadUrl(url);//��assets/translate.html����
    }
	public void runJavaScript(){//�Զ����ִ�нű��ķ���
		if (myEditText.getText().toString() != ""){//���û���������ݲ�Ϊ��ʱ
			//����translate.html��javascript��translate����
			myWebView.loadUrl("javascript:translate('"+ myEditText.getText().toString() + "')");
		}
	}
}