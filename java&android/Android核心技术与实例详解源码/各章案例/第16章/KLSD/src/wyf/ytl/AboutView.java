package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AboutView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity������
	Bitmap aboutBitmap ;
	SurfaceHolder surfaceHolder;//surfaceHolder������
	public AboutView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;
		surfaceHolder = this.getHolder();
		getHolder().addCallback(this);//���Callback�ӿڵ�ʵ��
		aboutBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about);
	}
	public void onDraw(Canvas canvas) {//���Ʒ���
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(aboutBitmap, 0, 10, null);
	}
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			double x = event.getX();
			double y = event.getY();
			if(x>230 && x<310 && y>310 && y<450){//���ȷ����ť
				activity.myHandler.sendEmptyMessage(2);
			}
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {
		Canvas c = null;//��������
        try {
        	//������������
            c = this.surfaceHolder.lockCanvas(null);
            synchronized (this.surfaceHolder) {//ͬ��
            	onDraw(c);//���û��Ʒ���
            }
        } finally {//��finally��֤һ����ִ��
            if (c != null) {
            	//������Ļ��ʾ����
                this.surfaceHolder.unlockCanvasAndPost(c);
            }
        }
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {}
}
