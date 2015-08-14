package wyf.wpf;				//声明包语句
import org.apache.http.protocol.HTTP;//引入相关类
import android.app.Activity;			//引入相关类
import android.os.Bundle;			//引入相关类
import android.view.View;			//引入相关类
import android.webkit.WebView;		//引入相关类
import android.widget.Button;		//引入相关类
import android.widget.EditText;		//引入相关类
import android.widget.Toast;		//引入相关类
public class Sample_10_5 extends Activity {
	Button btnExe;			//声明Button对象
	EditText etHtml;		//声明EditText对象
	WebView wv;				//声明WebView对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//设置当前屏幕
        btnExe = (Button)findViewById(R.id.btn);	//获得Button对象
        etHtml = (EditText)findViewById(R.id.et);	//获得EditText对象
        wv = (WebView)findViewById(R.id.wv);		//获得WebView对象
        btnExe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {			//重写onClick方法
				String code = etHtml.getText().toString().trim();	//获得EditText控件中内容
//				String code = "<html><head></head><body><a href=http://www.google.com>点我</a></body></html>";
				try{
					wv.loadData(code, "text/html", HTTP.UTF_8);		//加载执行HTML代码
				}
				catch(Exception e){
					Toast.makeText(Sample_10_5.this, "错误："+e.getMessage(), Toast.LENGTH_LONG);//打印出错信息
				}
			}
		});
    }
}