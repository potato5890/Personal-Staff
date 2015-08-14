package wyf.ytl;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
public class MyView extends View{//继承自View
	Paint paint;//画笔
	public MyView(Context context) {//构造器
		super(context);
		paint = new Paint();//初始化画笔
		paint.setColor(Color.WHITE);//设置画笔的颜色
		paint.setTextSize(20);//设置字体的大小
		paint.setAntiAlias(true);//打开抗锯齿
	}
	protected void onDraw(Canvas canvas) {//重写的绘制方法
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);//绘制背景颜色
		canvas.drawRect(10, 10, 110, 110, paint);//绘制一个矩形
		canvas.drawText("此界面为自定义的View", 60, 170, paint);
	}
}