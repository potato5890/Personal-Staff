package wyf.ytl;
import java.io.IOException;
import java.util.List;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
public class Sample_15_3 extends MapActivity implements OnClickListener{
	EditText myEditText1 = null;//����EditText������
	EditText myEditText2 = null;//����EditText������
	MapView myMapView = null;//����MapView������
	Button myButton = null;//ȷ����ť
	Button fromButton = null;//��ʾ��ʼ�㰴ť
	Button toButton = null;//��ʾĿ��㰴ť
	MapController myMapController = null;//����myMapController������
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ���û�����
        myEditText1 = (EditText) this.findViewById(R.id.myEditText1);//�õ�myEditText1������
        myEditText2 = (EditText) this.findViewById(R.id.myEditText2);//�õ�myEditText2������
        myButton = (Button) this.findViewById(R.id.myButton);
        fromButton = (Button) this.findViewById(R.id.fromButton);//�õ�fromButton��ť
        toButton = (Button) this.findViewById(R.id.toButton);//�õ�toButton��ť
        myButton.setOnClickListener(this);//ΪmyButton��Ӽ���
        toButton.setOnClickListener(this);//ΪtoButton��Ӽ���
        fromButton.setOnClickListener(this);//ΪfromButton��Ӽ���
        myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�MapView
        myMapController = myMapView.getController();//���MapController
        myMapController.setZoom(18);
    }
	@Override
	public void onClick(View v) {//��д�ļ�������
		if(v == myButton){//���ȷ����ť
			String fromAddress = myEditText1.getText().toString();//�õ��û��������ʼ��
			String toAddress = myEditText2.getText().toString();//�õ��û�������յ�
			GeoPoint formGeoPoint = getGeoPointByAddress(fromAddress);//�õ���ʼ���GeoPoint
			GeoPoint toGeoPoint = getGeoPointByAddress(toAddress);//�õ��յ��GeoPoint
			Intent intent = new Intent();//����һ��Intent
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setData(//��������
				Uri.parse(
					"http://maps.google.com/maps?f=d&saddr="
					+ geoPointToString(formGeoPoint) +"&daddr="
					+geoPointToString(toGeoPoint)+"&h1=cn"
				)
			);
			startActivity(intent);//������ͼ��Activity
		}
		else if(v == fromButton){//������ʾ��㰴ť
			String fromAddress = myEditText1.getText().toString();//�õ��û��������ʼ��
			GeoPoint formGeoPoint = getGeoPointByAddress(fromAddress);//�õ���ʼ���GeoPoint
			if(formGeoPoint != null){
				myMapController.animateTo(formGeoPoint);//��ʾ��formGeoPoint��
				
			}
		}
		else if(v == toButton){//������ʾ�յ㰴ť
			String toAddress = myEditText2.getText().toString();//�õ��û�������յ�
			GeoPoint toGeoPoint = getGeoPointByAddress(toAddress);//�õ��յ��GeoPoint
			if(toGeoPoint != null){//����Ϊ��ʱ
				myMapController.animateTo(toGeoPoint);//��ʾ��toGeoPoint��
			}
		}
	}
	//��GeoPoint��ľ�γ��ת������Ҫ���ַ���
	private String geoPointToString(GeoPoint gp){ 
		String result=""; //����һ���ַ���
		try { 
			if (gp != null){//��GeoPoint��Ϊ��ʱ
				double geoLatitude = (int)gp.getLatitudeE6()/1E6; //�õ���γ��
				double geoLongitude = (int)gp.getLongitudeE6()/1E6; 
				result = String.valueOf(geoLatitude)+","+String.valueOf(geoLongitude); 
			} 
	    } 
	    catch(Exception e){ //�����쳣
	    	e.printStackTrace(); //��ӡ�쳣
	    } 
	    return result; //���ؽ��
	} 	
	public GeoPoint getGeoPointByAddress(String address){//ͨ����ַ�õ�GeoPoint
		GeoPoint geoPoint = null;//����GeoPoint����
		if(address != ""){
			Geocoder myGeocoder = new Geocoder(this);//����һ��Geocoder
			List<Address> addressList = null;//����addressList
			try {
				addressList = myGeocoder.getFromLocationName(address, 1);//�õ�Address
				if(!addressList.isEmpty()){
					Address tempAddress = addressList.get(0);//��ȡ��һ��Address
					double myLatitude = tempAddress.getLatitude()*1E6;//���㾭γ��
					double myLongitude = tempAddress.getLongitude()*1E6;
					geoPoint = new GeoPoint((int)myLatitude, (int)myLongitude);//����GeoPoint
				}
			} catch (IOException e) {//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
		}
		return geoPoint;//����GeoPoint
	}
	@Override
	protected boolean isRouteDisplayed() {//��д��MapActivity�еķ���
		return false;
	}	
}