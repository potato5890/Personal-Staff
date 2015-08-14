package wyf.ytl;
public class TimeThread extends Thread{
	GameView gameView;//声明GameView的引用
	boolean flag=true;//循环标志位
	public TimeThread(GameView gameView){//构造器
		this.gameView=gameView;//得到GameView的引用
	}
	public void run(){//重写的run方法
		while(flag){
			gameView.time++;//时间自加
			try{
				Thread.sleep(1000);//睡眠一秒种
			}catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常信息
			}
		}
	}
}