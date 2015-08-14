package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
public class DrawKeyThread extends Thread {
	GameView gameView;//gameView的引用
	int span = 40;//透明度的更改步数
	boolean flag = true;;//循环标志位
	Bitmap key_bitmap;//图片
	int alpha=0;//透明度
	public DrawKeyThread(Bitmap key_bitmap,GameView gameView){//构造器
		this.key_bitmap = key_bitmap;//得到图片的引用
		this.gameView = gameView;//得到gameView的引用
	}
	public void run(){//重写的run方法
		while(flag){//循环
			Bitmap b = Bitmap.createBitmap(key_bitmap.getWidth(), key_bitmap.getHeight(), Config.ARGB_8888);
			Canvas c = new Canvas(b);//创建画布
			Paint p = new Paint();//创建画笔
			p.setAlpha(alpha);//设置透明度
			c.drawBitmap(key_bitmap, 0, 0, p);//绘制图片
			gameView.key_bitmap = b;
			alpha+=span;//改变透明度
			if(alpha>255){//当透明度过大时
				alpha = 255;
				flag = false;//停止循环
			}
			try{
				Thread.sleep(100);//睡眠指定毫秒数
			}catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常信息
			}
		}
	}
}
