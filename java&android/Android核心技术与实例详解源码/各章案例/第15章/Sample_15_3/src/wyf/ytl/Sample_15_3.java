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
	EditText myEditText1 = null;//声明EditText的引用
	EditText myEditText2 = null;//声明EditText的引用
	MapView myMapView = null;//声明MapView的引用
	Button myButton = null;//确定按钮
	Button fromButton = null;//显示起始点按钮
	Button toButton = null;//显示目标点按钮
	MapController myMapController = null;//声明myMapController的引用
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        myEditText1 = (EditText) this.findViewById(R.id.myEditText1);//得到myEditText1的引用
        myEditText2 = (EditText) this.findViewById(R.id.myEditText2);//得到myEditText2的引用
        myButton = (Button) this.findViewById(R.id.myButton);
        fromButton = (Button) this.findViewById(R.id.fromButton);//得到fromButton按钮
        toButton = (Button) this.findViewById(R.id.toButton);//得到toButton按钮
        myButton.setOnClickListener(this);//为myButton添加监听
        toButton.setOnClickListener(this);//为toButton添加监听
        fromButton.setOnClickListener(this);//为fromButton添加监听
        myMapView = (MapView) this.findViewById(R.id.myMapView);//得到MapView
        myMapController = myMapView.getController();//获得MapController
        myMapController.setZoom(18);
    }
	@Override
	public void onClick(View v) {//重写的监听方法
		if(v == myButton){//点击确定按钮
			String fromAddress = myEditText1.getText().toString();//得到用户输入的起始点
			String toAddress = myEditText2.getText().toString();//得到用户输入的终点
			GeoPoint formGeoPoint = getGeoPointByAddress(fromAddress);//得到起始点的GeoPoint
			GeoPoint toGeoPoint = getGeoPointByAddress(toAddress);//得到终点的GeoPoint
			Intent intent = new Intent();//创建一个Intent
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setData(//设置数据
				Uri.parse(
					"http://maps.google.com/maps?f=d&saddr="
					+ geoPointToString(formGeoPoint) +"&daddr="
					+geoPointToString(toGeoPoint)+"&h1=cn"
				)
			);
			startActivity(intent);//启动地图的Activity
		}
		else if(v == fromButton){//按下显示起点按钮
			String fromAddress = myEditText1.getText().toString();//得到用户输入的起始点
			GeoPoint formGeoPoint = getGeoPointByAddress(fromAddress);//得到起始点的GeoPoint
			if(formGeoPoint != null){
				myMapController.animateTo(formGeoPoint);//显示到formGeoPoint点
				
			}
		}
		else if(v == toButton){//按下显示终点按钮
			String toAddress = myEditText2.getText().toString();//得到用户输入的终点
			GeoPoint toGeoPoint = getGeoPointByAddress(toAddress);//得到终点的GeoPoint
			if(toGeoPoint != null){//当不为空时
				myMapController.animateTo(toGeoPoint);//显示到toGeoPoint点
			}
		}
	}
	//将GeoPoint里的经纬度转换成需要的字符串
	private String geoPointToString(GeoPoint gp){ 
		String result=""; //声明一个字符串
		try { 
			if (gp != null){//当GeoPoint不为空时
				double geoLatitude = (int)gp.getLatitudeE6()/1E6; //得到经纬度
				double geoLongitude = (int)gp.getLongitudeE6()/1E6; 
				result = String.valueOf(geoLatitude)+","+String.valueOf(geoLongitude); 
			} 
	    } 
	    catch(Exception e){ //捕获异常
	    	e.printStackTrace(); //打印异常
	    } 
	    return result; //返回结果
	} 	
	public GeoPoint getGeoPointByAddress(String address){//通过地址得到GeoPoint
		GeoPoint geoPoint = null;//声明GeoPoint引用
		if(address != ""){
			Geocoder myGeocoder = new Geocoder(this);//创建一个Geocoder
			List<Address> addressList = null;//声明addressList
			try {
				addressList = myGeocoder.getFromLocationName(address, 1);//得到Address
				if(!addressList.isEmpty()){
					Address tempAddress = addressList.get(0);//获取第一个Address
					double myLatitude = tempAddress.getLatitude()*1E6;//计算经纬度
					double myLongitude = tempAddress.getLongitude()*1E6;
					geoPoint = new GeoPoint((int)myLatitude, (int)myLongitude);//创建GeoPoint
				}
			} catch (IOException e) {//捕获异常
				e.printStackTrace();//打印异常
			}
		}
		return geoPoint;//返回GeoPoint
	}
	@Override
	protected boolean isRouteDisplayed() {//重写的MapActivity中的方法
		return false;
	}	
}