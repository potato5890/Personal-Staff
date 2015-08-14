package wyf.wpf;								//声明包语句
import android.app.Activity;					//引入相关类
import android.os.Bundle;						//引入相关类
import android.view.View;							//引入相关类
import android.view.Window;						//引入相关类
import android.webkit.URLUtil;					//引入相关类
import android.webkit.WebChromeClient;			//引入相关类
import android.webkit.WebView;				//引入相关类
import android.webkit.WebViewClient;		//引入相关类
import android.widget.Button;				//引入相关类
import android.widget.EditText;				//引入相关类
import android.widget.Toast;				//引入相关类
public class Sample_10_4 extends Activity {
	WebView wv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);
        wv = (WebView)findViewById(R.id.wv);
        wv.setWebChromeClient(new WebChromeClient(){		//为WebView设置WebChromeClient
			@Override
			public void onProgressChanged(WebView view, int newProgress) {//重写onProgressChanged方法
				Sample_10_4.this.setProgress(newProgress*100);
			}
        });
        wv.setWebViewClient(new WebViewClient() {			//为WebView设置WebViewClient
        	public void onReceivedError(WebView view, 
        			int errorCode, String description, String failingUrl) {//重写onReceivedError方法
        		Toast.makeText(Sample_10_4.this, "Sorry!" + description, Toast.LENGTH_SHORT).show();
        	}
        });
Button btn = (Button)findViewById(R.id.btn);   	//获取Button对象     
btn.setOnClickListener(new View.OnClickListener() {	//为Button对象设置OnClickListener监听器
	@Override
	public void onClick(View v) {
		
		EditText et = (EditText)findViewById(R.id.et);		//获得WebView对象
		String url = et.getText().toString().trim();
		if(URLUtil.isNetworkUrl(url)){		//判断是否是网址
			wv.loadUrl(url);
		}
		else{
			Toast.makeText(Sample_10_4.this, "对不起，您输入的网址有错。", Toast.LENGTH_SHORT).show();
			et.requestFocus();			//将焦点移到EditText
		}
	}
});
//设置前进后退按钮
Button btnForward = (Button)findViewById(R.id.btnForward);
btnForward.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		if(wv.canGoForward()){		//判断是否能够前进
			wv.goForward();
		}
		else{
			Toast.makeText(Sample_10_4.this, "对不起，您现在不能前进！", Toast.LENGTH_SHORT).show();
		}
	}
});
Button btnBack = (Button)findViewById(R.id.btnBack);
btnBack.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		if(wv.canGoBack()){		//判断是否能够前进
			wv.goBack();
		}
		else{
			Toast.makeText(Sample_10_4.this, "对不起，您现在不能后退！", Toast.LENGTH_SHORT).show();
		}
	}
});
    }
}