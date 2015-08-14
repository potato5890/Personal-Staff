package wyf.wpf;					//声明包语句

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class ArrowOverLay extends Overlay{
	Bitmap bmpArrow;		//声明Bitmap对象的引用
	GeoPoint gp;			//声明GeoPoint对象的引用
	public ArrowOverLay(GeoPoint gp,Bitmap bmp){
		super();			//调用父类构造器
		this.gp = gp;		//初始化GeoPoint
		bmpArrow = bmp;		//初始化Bitmap
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if(!shadow){
			Projection proj = mapView.getProjection();		//获得Projection对象
			Point p = new Point();
			proj.toPixels(gp, p);			//将真是地理坐标转化为屏幕上的坐标
			canvas.drawBitmap(bmpArrow, 
					p.x-bmpArrow.getWidth()/2, 
					p.y-bmpArrow.getHeight(), 
					null);					//绘制箭头图片
		}
	}
}