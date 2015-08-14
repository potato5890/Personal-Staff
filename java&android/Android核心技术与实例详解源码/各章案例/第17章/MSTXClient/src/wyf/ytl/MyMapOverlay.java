package wyf.ytl;

import java.util.List;

import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

//覆盖整个地图捕捉触控事件的OverLay
class MyMapOverlay extends Overlay{
	boolean flagMove=false;
    @Override 
    public boolean onTouchEvent(MotionEvent event, MapView mv) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){//若移动了触控笔则设置移动标志为true
        	flagMove=true;
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN){//若抬起了触控笔则设置移动标志为false
        	flagMove=false;
        }
        else if (MyBallonOverlay.currentBallon==null&&
        		 !flagMove&&
        		 event.getAction() == MotionEvent.ACTION_UP ){
        	//若没有当前选中的气球也没有移动触控笔且触控笔抬起则获取此处的经纬度并添加新气球
        	GeoPoint gp = mv.getProjection().fromPixels(
        		 (int) event.getX(),  //触控笔在屏幕上的X坐标
        		 (int) event.getY()   //触控笔在屏幕上的Y坐标
            );
        	//显示点击处的经纬度
            String latStr=Math.round(gp.getLatitudeE6()/1000.00)/1000.0+"";//纬度
        	String longStr=Math.round(gp.getLongitudeE6()/1000.00)/1000.0+"";//经度        	
        	//清除其他气球的showWindow标记
        	List<Overlay> overlays = mv.getOverlays(); 
        	for(Overlay ol:overlays){//清除其他气球的showWindow标记
        		if(ol instanceof MyBallonOverlay){
        			overlays.remove(ol);
        		}
        	} 
        	//在点击位置添加新气球
        	MyBallonOverlay mbo=new MyBallonOverlay(
        			gp,//气球的坐标
        			"店铺位置为：\n经度："+longStr+"\n纬度："+latStr//气球的信息
        	); 
            overlays.add(mbo); 
            return true;
        }
        return false;
    }
}