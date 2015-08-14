package wyf.wpf;				//���������
import org.apache.http.protocol.HTTP;//���������
import android.app.Activity;			//���������
import android.os.Bundle;			//���������
import android.view.View;			//���������
import android.webkit.WebView;		//���������
import android.widget.Button;		//���������
import android.widget.EditText;		//���������
import android.widget.Toast;		//���������
public class Sample_10_5 extends Activity {
	Button btnExe;			//����Button����
	EditText etHtml;		//����EditText����
	WebView wv;				//����WebView����
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//���õ�ǰ��Ļ
        btnExe = (Button)findViewById(R.id.btn);	//���Button����
        etHtml = (EditText)findViewById(R.id.et);	//���EditText����
        wv = (WebView)findViewById(R.id.wv);		//���WebView����
        btnExe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {			//��дonClick����
				String code = etHtml.getText().toString().trim();	//���EditText�ؼ�������
//				String code = "<html><head></head><body><a href=http://www.google.com>����</a></body></html>";
				try{
					wv.loadData(code, "text/html", HTTP.UTF_8);		//����ִ��HTML����
				}
				catch(Exception e){
					Toast.makeText(Sample_10_5.this, "����"+e.getMessage(), Toast.LENGTH_LONG);//��ӡ������Ϣ
				}
			}
		});
    }
}