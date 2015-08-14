package wyf.wpf;					//声明包语句

import android.app.Activity;			//引入相关类
import android.content.Context;			//引入相关类
import android.location.Criteria;		//引入相关类
import android.location.Location;		//引入相关类
import android.location.LocationListener;	//引入相关类
import android.location.LocationManager;	//引入相关类
import android.os.Bundle;					//引入相关类	
import android.widget.EditText;				//引入相关类	

public class Sample_15_1 extends Activity {
	LocationManager lm;				//声明LocationManager对象的引用
	EditText et;					//声明EditText对象的引用
	LocationListener ll = new LocationListener(){
		@Override
		public void onLocationChanged(Location location) {//重写onLocationChanged方法
			updateView(location);
		}
		@Override
		public void onProviderDisabled(String provider) {	//重写onProviderDisabled方法
			updateView(null);
		}
		@Override
		public void onProviderEnabled(String provider) {	//重写onProviderEnabled方法
	        Location l= lm.getLastKnownLocation(provider);		//获取位置信息
	        updateView(l);			//更新EditText控件的内容
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {	//重写onStatusChanged方法
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {		//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//设置当前屏幕
        et = (EditText)findViewById(R.id.et);		//获得EditText对象
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);	//创建LocationManager对象
        String bestProvider = lm.getBestProvider(getCriteria(), true);		//设置查询条件
        Location l= lm.getLastKnownLocation(bestProvider);		//获取位置信息
        updateView(l);			//更新EditText控件的内容
        lm.requestLocationUpdates(bestProvider, 5000, 8, ll);	//添加LocationListener监听器
    }
    //方法：返回查询条件
    public Criteria getCriteria(){
    	Criteria c = new Criteria();
    	c.setAccuracy(Criteria.ACCURACY_COARSE);	//设置查询精度
    	c.setSpeedRequired(false);					//设置是否要求速度
    	c.setCostAllowed(false);					//设置是否允许产生费用
    	c.setBearingRequired(false);				//设置是否需要得到方向
    	c.setAltitudeRequired(false);				//设置是否需要得到海拔高度
    	c.setPowerRequirement(Criteria.POWER_LOW);	//设置允许的电池消耗级别
    	return c;									//返回查询条件
    }
    //方法：更新EditText中显示的内容
    public void updateView(Location newLocation){
    	if(newLocation !=null){		//判断是否为空
    		et.setText("您现在的位置是\n纬度：");
    		et.append(String.valueOf(newLocation.getLatitude()));	//获得纬度
    		et.append("\n经度：");
    		et.append(String.valueOf(newLocation.getLongitude()));	//获得精度
    	}
    	else{		//如果传入的Location对象为空则清空EditText
    		et.getEditableText().clear();		//清空EditText对象
    	}
    }
}