package wyf.ytl;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class GameViewDrawThread extends Thread{
	GameView gameView;//gameView的引用
	private int sleepSpan = 100;
	private boolean flag = true;//循环标记位
	private SurfaceHolder surfaceHolder;//surfaceHolder的引用
	public GameViewDrawThread(GameView gameView){//构造器
		this.gameView = gameView;//得到welcomeView的引用
		surfaceHolder = gameView.getHolder();//得到surfaceHolder的引用
	}
	public void run() {//重写的run方法
		Canvas c;//声明画布
        while (this.flag) {//循环
            c = null;
            try {
            	//锁定整个画布
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {//同步
                	gameView.onDraw(c);//调用绘制方法
                }
            } finally {//用finally保证一定被执行
                if (c != null) {
                	//更新屏幕显示内容
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try{
            	Thread.sleep(sleepSpan);//睡眠指定毫秒数
            }catch(Exception e){//捕获异常
            	e.printStackTrace();//打印异常信息
            }
        }
	}
	public void setFlag(boolean flag){//循环标记位的set方法
		this.flag = flag;
	}
}
