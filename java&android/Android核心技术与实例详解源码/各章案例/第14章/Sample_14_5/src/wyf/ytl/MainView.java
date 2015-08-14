package wyf.ytl;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
public class MainView extends View{
	Paint paint;//声明画笔的引用
	Bitmap big;//大圆的图片
	Bitmap small;//小水泡的图片
	int big_X = 33;//大圆的x坐标
	int big_Y = 33;//大圆的y坐标
	int small_X;//小水泡的x坐标
	int small_Y;//小水泡的y坐标
	public MainView(Context context, AttributeSet attrs) {//构造器
		super(context, attrs);
		init();//初始化资源
	}
	public void init(){//初始化方法
		paint = new Paint();//初始化画笔
		big = BitmapFactory.decodeResource(getResources(), R.drawable.big);//初始化大圆的图片
		small = BitmapFactory.decodeResource(getResources(), R.drawable.small);//初始化水泡的图片
		small_X = big_X + big.getWidth()/2 - small.getWidth()/2;//初始化水泡的x坐标 
		small_Y = big_Y + big.getHeight()/2 - small.getHeight()/2;//初始化水泡的y坐标
	}
	@Override
	protected void onDraw(Canvas canvas) {//绘制方法
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);//设置背景色为白色
		paint.setColor(Color.BLUE);//设置画笔颜色
		paint.setStyle(Style.STROKE); //设置画笔为不填充
		canvas.drawRect(5, 5, 315, 315, paint);//画外边框
		canvas.drawBitmap(big, big_X, big_Y, paint);//绘制大圆 
		canvas.drawBitmap(small, small_X, small_Y, paint);//绘制水泡	
		
		RectF oval = new RectF(big_X+big.getWidth()/2-10, 
				big_Y+big.getHeight()/2-10, 
				big_X+big.getWidth()/2+10,
				big_Y+big.getHeight()/2+10);
		canvas.drawOval(oval, paint);//绘制基准线(圆)
	}	
}
