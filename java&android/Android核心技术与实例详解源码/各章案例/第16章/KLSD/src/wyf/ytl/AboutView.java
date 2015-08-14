package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AboutView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity的引用
	Bitmap aboutBitmap ;
	SurfaceHolder surfaceHolder;//surfaceHolder的引用
	public AboutView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;
		surfaceHolder = this.getHolder();
		getHolder().addCallback(this);//添加Callback接口的实现
		aboutBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about);
	}
	public void onDraw(Canvas canvas) {//绘制方法
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(aboutBitmap, 0, 10, null);
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			double x = event.getX();
			double y = event.getY();
			if(x>230 && x<310 && y>310 && y<450){//点击确定按钮
				activity.myHandler.sendEmptyMessage(2);
			}
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {
		Canvas c = null;//声明画布
        try {
        	//锁定整个画布
            c = this.surfaceHolder.lockCanvas(null);
            synchronized (this.surfaceHolder) {//同步
            	onDraw(c);//调用绘制方法
            }
        } finally {//用finally保证一定被执行
            if (c != null) {
            	//更新屏幕显示内容
                this.surfaceHolder.unlockCanvasAndPost(c);
            }
        }
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {}
}
