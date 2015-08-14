package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity������
	WelcomeViewDrawThread welcomeViewDrawThread;//ˢ֡�߳�
	WelcomeViewGoThread welcomeViewGoThread;//���������߳�
	int[] bitmapsID = new int[]{//ͼƬID
		R.drawable.w1,R.drawable.w2,
		R.drawable.w3,R.drawable.w4,
		R.drawable.w5,R.drawable.w6,
		R.drawable.w7,R.drawable.w8,
		R.drawable.w9,R.drawable.w10,
		R.drawable.w11,R.drawable.w12,
		R.drawable.w13,R.drawable.w14,
		R.drawable.w15,R.drawable.w16,
		R.drawable.w17,R.drawable.w18,
		R.drawable.w19,R.drawable.w20,
		R.drawable.w21,R.drawable.w22,
		R.drawable.w23,R.drawable.w24,
		R.drawable.w25,R.drawable.w26,
		R.drawable.w27,R.drawable.w28,
		R.drawable.w29,R.drawable.w30,
	};
	Bitmap[] bitmaps;//ͼƬ����
	Bitmap[] menuBitmap = new Bitmap[4];
	int drawIndex = 0;//��ǰ���Ƶڼ�֡
	boolean drawString = false;//�Ƿ��������
	int status = 0;//0��ӭ״̬,1�˵�״̬
	Paint paint;//����
	Paint paint2;
	public WelcomeView(KLSDActivity activity) {//������
		super(activity);
		this.activity = activity;//�õ�activity������
		getHolder().addCallback(this);//���Callback�ӿڵ�ʵ��
		initBitmap();//���ó�ʼ������
		welcomeViewDrawThread = new WelcomeViewDrawThread(this);
		welcomeViewGoThread = new WelcomeViewGoThread(this);
	}
	public void initBitmap(){//��ʼ��ͼƬ����
		paint = new Paint();
		paint2 = new Paint();
		paint2.setAlpha(125);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);//�򿪿����
		paint.setTextSize(18);
		bitmaps = new Bitmap[bitmapsID.length];
		for(int i=0; i<bitmaps.length; i++){
			bitmaps[i] = BitmapFactory.decodeResource(getResources(), bitmapsID[i]);
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		for(int i=0; i<menuBitmap.length; i++){//�г�СͼƬ
			menuBitmap[i] = Bitmap.createBitmap(bitmap, 0, 
					(bitmap.getHeight()/menuBitmap.length)*i, 
					bitmap.getWidth(), 
					(bitmap.getHeight()/menuBitmap.length));
		}
		bitmap = null;//�ͷŵ���ͼ		
	}
	protected void onDraw(Canvas canvas) {//���Ʒ���
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);//�屳��
		canvas.drawBitmap(bitmaps[drawIndex], 0, 100, paint);
		if(status == 0){
			if(drawString){
				canvas.drawText("�����Ļ����...", 103, 380, paint);
			}			 
		}else if(status == 1){//�˵�״̬ʱ
			canvas.drawRect(0, 0, 320, 480, paint2);
			for(int i=0; i<menuBitmap.length; i++){
				canvas.drawBitmap(menuBitmap[i], 60, 130+50*i, null);
			}
		}
	}
	public boolean onTouchEvent(MotionEvent event) {//��Ļ��������
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ�������¼�
			double x = event.getX();
			double y = event.getY();
			if(status == 0){
				status = 1;
			}else if(status == 1){
				if(x>60 && x<260 && y>130 && y<160){//��ʼ��Ϸ
					activity.myHandler.sendEmptyMessage(1);
				}else if(x>60 && x<260 && y>180 && y<210){//������Ϸ
					activity.myHandler.sendEmptyMessage(3);
				}else if(x>60 && x<260 && y>230 && y<260){//������Ϸ
					activity.myHandler.sendEmptyMessage(4);
				}else if(x>60 && x<260 && y>280 && y<310){//�˳���Ϸ
					System.exit(0);//ֱ���˳���Ϸ
				}
			}
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {
		welcomeViewDrawThread.setFlag(true);//����ѭ����־λ
		welcomeViewGoThread.setFlag(true);
		welcomeViewDrawThread.start();//�����߳�
		welcomeViewGoThread.start();//�����߳�
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;//ѭ����־λ
        welcomeViewDrawThread.setFlag(false);//����ѭ����־λ
        welcomeViewGoThread.setFlag(false);
        while (retry) {//ѭ��
            try {
            	welcomeViewDrawThread.join();//�õ��߳̽���
            	welcomeViewGoThread.join();//�õ��߳̽���
                retry = false;
            } 
            catch (Exception e) {//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
            	e.printStackTrace();
            }
        }
	}
}