package wyf.wpf;				//声明包语句

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyWeatherHandler extends DefaultHandler{
	private String main_condition;		//天气情况
	private Integer temprature;			//气温
	private String humidity;			//湿度
	private String wind;				//风况
	private String icon;				//图片地址
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("condition")){
			String value = attributes.getValue("data");	//获得天气总情况
			setMain_condition(value);					//设置天气总情况
		}
		else if(localName.equals("temp_c")){		//
			String value = attributes.getValue("data");	//获得气温
			setTemprature(Integer.valueOf(value));		//将气温值设置到成员变量
		}
		else if(localName.equals("humidity")){		
			String value = attributes.getValue("data");	//获得湿度
			setHumidity(value);							//将湿度值设置到成员变量
		}
		else if(localName.equals("wind_condition")){
			String value = attributes.getValue("data");	//获取风况
			setWind(value);								//将风况值设置到成员变量
		}
		else if(localName.equals("icon")){
			String value = attributes.getValue("data");	//获取图标
			setIcon(value);								//将图标值设置到成员变量	
		}
	}
	public String getMain_condition() {
		return main_condition;
	}
	public void setMain_condition(String mainCondition) {
		main_condition = mainCondition;
	}
	public Integer getTemprature() {
		return temprature;
	}
	public void setTemprature(Integer temprature) {
		this.temprature = temprature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}