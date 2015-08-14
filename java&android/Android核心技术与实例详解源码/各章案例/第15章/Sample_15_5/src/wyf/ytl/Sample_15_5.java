package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
public class Sample_15_5 extends Activity {
	EditText myEditText;//用于接收用户输入
	WebView myWebView;//WebView，用户显示html
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        myEditText = (EditText) findViewById(R.id.myEditText);//得到EditText的引用 
        myWebView = (WebView) findViewById(R.id.myWebView);//得到WebView的引用
        WebSettings myWebSettings = myWebView.getSettings();//取得WebSettings
        myWebSettings.setJavaScriptEnabled(true);//设置可以运行JavaScript
        myWebView.addJavascriptInterface(this, "ytl");//设置给html调用的对象及名称
        String url = "file:///android_asset/translate.html";
        myWebView.loadUrl(url);//将assets/translate.html载入
    }
	public void runJavaScript(){//自定义的执行脚本的方法
		if (myEditText.getText().toString() != ""){//当用户输入的数据不为空时
			//调用translate.html里javascript的translate方法
			myWebView.loadUrl("javascript:translate('"+ myEditText.getText().toString() + "')");
		}
	}
}