package wyf.wpf;				//���������

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyWeatherHandler extends DefaultHandler{
	private String main_condition;		//�������
	private Integer temprature;			//����
	private String humidity;			//ʪ��
	private String wind;				//���
	private String icon;				//ͼƬ��ַ
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("condition")){
			String value = attributes.getValue("data");	//������������
			setMain_condition(value);					//�������������
		}
		else if(localName.equals("temp_c")){		//
			String value = attributes.getValue("data");	//�������
			setTemprature(Integer.valueOf(value));		//������ֵ���õ���Ա����
		}
		else if(localName.equals("humidity")){		
			String value = attributes.getValue("data");	//���ʪ��
			setHumidity(value);							//��ʪ��ֵ���õ���Ա����
		}
		else if(localName.equals("wind_condition")){
			String value = attributes.getValue("data");	//��ȡ���
			setWind(value);								//�����ֵ���õ���Ա����
		}
		else if(localName.equals("icon")){
			String value = attributes.getValue("data");	//��ȡͼ��
			setIcon(value);								//��ͼ��ֵ���õ���Ա����	
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