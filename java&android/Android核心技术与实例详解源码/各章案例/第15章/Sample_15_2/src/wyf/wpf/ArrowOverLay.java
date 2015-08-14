package wyf.wpf;					//���������

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class ArrowOverLay extends Overlay{
	Bitmap bmpArrow;		//����Bitmap���������
	GeoPoint gp;			//����GeoPoint���������
	public ArrowOverLay(GeoPoint gp,Bitmap bmp){
		super();			//���ø��๹����
		this.gp = gp;		//��ʼ��GeoPoint
		bmpArrow = bmp;		//��ʼ��Bitmap
	}
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		if(!shadow){
			Projection proj = mapView.getProjection();		//���Projection����
			Point p = new Point();
			proj.toPixels(gp, p);			//�����ǵ�������ת��Ϊ��Ļ�ϵ�����
			canvas.drawBitmap(bmpArrow, 
					p.x-bmpArrow.getWidth()/2, 
					p.y-bmpArrow.getHeight(), 
					null);					//���Ƽ�ͷͼƬ
		}
	}
}