package wyf.wpf;			//声明包语句

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Sample_15_2 extends MapActivity {		//继承自MapActivity的子类
	MapView mv;					//声明MapView对象引用
	MapController controller;	//声明MapController对象引用
	Bitmap bmpArrow;			//声明Bitmap对象引用
	RadioButton rbNormal;			//声明RadioButton对象引用
	RadioButton rbSatellite;		//声明RadioButton对象引用
	@Override
	protected void onCreate(Bundle icicle) {		//重写onCreate方法
		super.onCreate(icicle);
		setContentView(R.layout.main);
		bmpArrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		mv = (MapView)findViewById(R.id.mv);			//获得MapView对象
		controller = mv.getController();				//获得MapController对象
		Button btnGo = (Button)findViewById(R.id.btnGo);	//获得Button对象
		mv.setBuiltInZoomControls(true);				//设置是否显示放大缩小按钮
		
		
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etLong = (EditText)findViewById(R.id.etLong);	//获得EditText对象
				EditText etLat = (EditText)findViewById(R.id.etLat);	//获得EditText对象
				String sLong = etLong.getEditableText().toString().trim();	//获得输入的经度
				String sLat = etLat.getEditableText().toString().trim();	//获得输入的纬度
				if(sLong.equals("") || sLat.equals("")){	//判断是否输入空值
					Toast.makeText(Sample_15_2.this, "对不起,请输入正确的经纬度坐标!", Toast.LENGTH_LONG).show();
					return;
				}
				double dLong = Double.parseDouble(sLong);
				double dLat = Double.parseDouble(sLat);
				updateMapView(dLat, dLong);		//调用方法更新MapView
			}
		});
		btnGo.performClick();
		RadioGroup rg = (RadioGroup)findViewById(R.id.rg);		//获得RadioGroup对象
		rbNormal = (RadioButton)findViewById(R.id.normal);	//获得RadioButton对象
		rbSatellite = (RadioButton)findViewById(R.id.satellite);	//获得RadioButton对象
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == rbNormal.getId()){		//判断按下的是否是正常视图
//					mv.setSatellite(false);
					mv.setTraffic(true);
				}
				else if(checkedId == rbSatellite.getId()){	//判断按下的是否为卫星视图
//					mv.setSatellite(true);
					mv.setStreetView(true);
				}
			}
		});
	}
	@Override
	protected boolean isRouteDisplayed() {		//重写isRouteDisplayed方法
		return false;
	}
	//方法:更新MapView的视图
	public void updateMapView(double dLat,double dLong){
		GeoPoint gp = new GeoPoint((int)(dLat*1E6), (int)(dLong*1E6));
		mv.displayZoomControls(true);		//设置显示放大缩小按钮
		controller.animateTo(gp);			//将地图移动到指定的地理位置
		List<Overlay> ol = mv.getOverlays();	//获得MapView的
		ol.clear();
		ol.add(new ArrowOverLay(gp,bmpArrow));		//添加一个新的Overlay
	}
}