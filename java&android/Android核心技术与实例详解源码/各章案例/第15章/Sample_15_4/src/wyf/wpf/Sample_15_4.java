package wyf.wpf;					//���������
	
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Sample_15_4 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnGo = (Button)findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etCity = (EditText)findViewById(R.id.etCity);
				String sCity = etCity.getEditableText().toString().trim();	//���EditText�ؼ�����
				if(sCity.equals("")){		//�ж�����ĳ������Ƿ�Ϊ��
					Toast.makeText(Sample_15_4.this, "��������ȷ�ĳ������ƣ�", Toast.LENGTH_LONG).show();
					return;
				}
				try{
					String urlStr = "http://www.google.com/ig/api?weather=" + sCity;
					urlStr = urlStr.replaceFirst(" ", "%20");		//�滻�ո�Ϊ %20
					URL weatherUrl = new URL(urlStr);		
					parseWeatherInfo(weatherUrl);			//���÷�������������Ϣ
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    }
    //����������������Ϣ
    public void parseWeatherInfo(URL url){
    	try {
			SAXParserFactory sf = SAXParserFactory.newInstance();	//����SAXParserFactory����
			SAXParser sp = sf.newSAXParser();			//����SAXParser����
			XMLReader xr = sp.getXMLReader();			//���XMLReader����
			MyWeatherHandler mwh = new MyWeatherHandler();	//��������XML���ݵ�MyWeatherHandler����
			xr.setContentHandler(mwh);				//����XMLReader��Handler
			xr.parse(new InputSource(url.openStream()));	//����XML�ļ�
			
			StringBuilder sb = new StringBuilder();
			sb.append(mwh.getMain_condition());
			sb.append("\n");
			sb.append(mwh.getTemprature());
			sb.append("\n");
			sb.append(mwh.getHumidity());
			sb.append("\n");
			sb.append(mwh.getWind());
			TextView tv = (TextView)findViewById(R.id.tvResult);
			tv.setText(sb.toString());
			
			URL iconUrl = new URL("http://www.google.com"+mwh.getIcon());	//����ͼƬ·��
			URLConnection con = iconUrl.openConnection();
			InputStream in = con.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(in);
			ImageView iv = (ImageView)findViewById(R.id.ivResult);	//���ImageView����
			iv.setImageBitmap(bmp);			//����ImageView��ͼƬ
			
		}
		catch (Exception e) {							//���񲢴�ӡ�쳣
			e.printStackTrace();
		} 
    }
}