package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HelpView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity的引用
	Bitmap helpBitmap ;
	SurfaceHolder surfaceHolder;//surfaceHolder的引用
	public HelpView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;//得到activity的引用
		surfaceHolder = this.getHolder();//获得surfaceHolder
		getHolder().addCallback(this);//添加Callback接口的实现
		helpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.help);
	}
	public void onDraw(Canvas canvas) {//绘制方法
		canvas.drawColor(Color.WHITE);//背景色
		canvas.drawBitmap(helpBitmap, 0, 10, null);//绘制图片
	}	
	public boolean onTouchEvent(MotionEvent event) {//键盘监听方法
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			double x = event.getX();
			double y = event.getY();//得到坐标
			if(x>220 && x<310 && y>430 && y<460){//点击确定按钮
				activity.myHandler.sendEmptyMessage(2);
			}
		}
		return super.onTouchEvent(event);
	}	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {//View创建时被调用
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