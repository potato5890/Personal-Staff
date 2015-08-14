package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HelpView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity������
	Bitmap helpBitmap ;
	SurfaceHolder surfaceHolder;//surfaceHolder������
	public HelpView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;//�õ�activity������
		surfaceHolder = this.getHolder();//���surfaceHolder
		getHolder().addCallback(this);//���Callback�ӿڵ�ʵ��
		helpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.help);
	}
	public void onDraw(Canvas canvas) {//���Ʒ���
		canvas.drawColor(Color.WHITE);//����ɫ
		canvas.drawBitmap(helpBitmap, 0, 10, null);//����ͼƬ
	}	
	public boolean onTouchEvent(MotionEvent event) {//���̼�������
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			double x = event.getX();
			double y = event.getY();//�õ�����
			if(x>220 && x<310 && y>430 && y<460){//���ȷ����ť
				activity.myHandler.sendEmptyMessage(2);
			}
		}
		return super.onTouchEvent(event);
	}	
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {//View����ʱ������
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