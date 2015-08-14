package wyf.wpf;					//声明包语句
	
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
				String sCity = etCity.getEditableText().toString().trim();	//获得EditText控件内容
				if(sCity.equals("")){		//判断输入的城市名是否为空
					Toast.makeText(Sample_15_4.this, "请输入正确的城市名称！", Toast.LENGTH_LONG).show();
					return;
				}
				try{
					String urlStr = "http://www.google.com/ig/api?weather=" + sCity;
					urlStr = urlStr.replaceFirst(" ", "%20");		//替换空格为 %20
					URL weatherUrl = new URL(urlStr);		
					parseWeatherInfo(weatherUrl);			//调用方法解析天气信息
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    }
    //方法：解析天气信息
    public void parseWeatherInfo(URL url){
    	try {
			SAXParserFactory sf = SAXParserFactory.newInstance();	//创建SAXParserFactory对象
			SAXParser sp = sf.newSAXParser();			//创建SAXParser对象
			XMLReader xr = sp.getXMLReader();			//获得XMLReader对象
			MyWeatherHandler mwh = new MyWeatherHandler();	//创建解析XML内容的MyWeatherHandler对象
			xr.setContentHandler(mwh);				//设置XMLReader的Handler
			xr.parse(new InputSource(url.openStream()));	//解析XML文件
			
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
			
			URL iconUrl = new URL("http://www.google.com"+mwh.getIcon());	//生成图片路径
			URLConnection con = iconUrl.openConnection();
			InputStream in = con.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(in);
			ImageView iv = (ImageView)findViewById(R.id.ivResult);	//获得ImageView对象
			iv.setImageBitmap(bmp);			//设置ImageView的图片
			
		}
		catch (Exception e) {							//捕获并打印异常
			e.printStackTrace();
		} 
    }
}