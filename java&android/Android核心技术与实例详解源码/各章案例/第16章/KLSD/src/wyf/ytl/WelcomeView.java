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
	KLSDActivity activity;//activity的引用
	WelcomeViewDrawThread welcomeViewDrawThread;//刷帧线程
	WelcomeViewGoThread welcomeViewGoThread;//动画生成线程
	int[] bitmapsID = new int[]{//图片ID
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
	Bitmap[] bitmaps;//图片数组
	Bitmap[] menuBitmap = new Bitmap[4];
	int drawIndex = 0;//当前绘制第几帧
	boolean drawString = false;//是否绘制文字
	int status = 0;//0欢迎状态,1菜单状态
	Paint paint;//画笔
	Paint paint2;
	public WelcomeView(KLSDActivity activity) {//构造器
		super(activity);
		this.activity = activity;//得到activity的引用
		getHolder().addCallback(this);//添加Callback接口的实现
		initBitmap();//调用初始化方法
		welcomeViewDrawThread = new WelcomeViewDrawThread(this);
		welcomeViewGoThread = new WelcomeViewGoThread(this);
	}
	public void initBitmap(){//初始化图片方法
		paint = new Paint();
		paint2 = new Paint();
		paint2.setAlpha(125);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);//打开抗锯齿
		paint.setTextSize(18);
		bitmaps = new Bitmap[bitmapsID.length];
		for(int i=0; i<bitmaps.length; i++){
			bitmaps[i] = BitmapFactory.decodeResource(getResources(), bitmapsID[i]);
		}
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu);
		for(int i=0; i<menuBitmap.length; i++){//切成小图片
			menuBitmap[i] = Bitmap.createBitmap(bitmap, 0, 
					(bitmap.getHeight()/menuBitmap.length)*i, 
					bitmap.getWidth(), 
					(bitmap.getHeight()/menuBitmap.length));
		}
		bitmap = null;//释放掉大图		
	}
	protected void onDraw(Canvas canvas) {//绘制方法
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);//清背景
		canvas.drawBitmap(bitmaps[drawIndex], 0, 100, paint);
		if(status == 0){
			if(drawString){
				canvas.drawText("点击屏幕继续...", 103, 380, paint);
			}			 
		}else if(status == 1){//菜单状态时
			canvas.drawRect(0, 0, 320, 480, paint2);
			for(int i=0; i<menuBitmap.length; i++){
				canvas.drawBitmap(menuBitmap[i], 60, 130+50*i, null);
			}
		}
	}
	public boolean onTouchEvent(MotionEvent event) {//屏幕监听方法
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下事件
			double x = event.getX();
			double y = event.getY();
			if(status == 0){
				status = 1;
			}else if(status == 1){
				if(x>60 && x<260 && y>130 && y<160){//开始游戏
					activity.myHandler.sendEmptyMessage(1);
				}else if(x>60 && x<260 && y>180 && y<210){//关于游戏
					activity.myHandler.sendEmptyMessage(3);
				}else if(x>60 && x<260 && y>230 && y<260){//帮助游戏
					activity.myHandler.sendEmptyMessage(4);
				}else if(x>60 && x<260 && y>280 && y<310){//退出游戏
					System.exit(0);//直接退出游戏
				}
			}
		}
		return super.onTouchEvent(event);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3){}
	public void surfaceCreated(SurfaceHolder arg0) {
		welcomeViewDrawThread.setFlag(true);//设置循环标志位
		welcomeViewGoThread.setFlag(true);
		welcomeViewDrawThread.start();//启动线程
		welcomeViewGoThread.start();//启动线程
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {
        boolean retry = true;//循环标志位
        welcomeViewDrawThread.setFlag(false);//设置循环标志位
        welcomeViewGoThread.setFlag(false);
        while (retry) {//循环
            try {
            	welcomeViewDrawThread.join();//得到线程结束
            	welcomeViewGoThread.join();//得到线程结束
                retry = false;
            } 
            catch (Exception e) {//不断地循环，直到刷帧线程结束
            	e.printStackTrace();
            }
        }
	}
}