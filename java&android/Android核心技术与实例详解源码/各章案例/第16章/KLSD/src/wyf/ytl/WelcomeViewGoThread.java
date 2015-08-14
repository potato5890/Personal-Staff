package wyf.ytl;
public class WelcomeViewGoThread extends Thread{
	WelcomeView welcomeView;//welcomeView的引用
	private int sleepSpan = 150;
	private boolean flag = true;//循环标记位
	public WelcomeViewGoThread(WelcomeView welcomeView) {//构造器
		this.welcomeView = welcomeView;//得到welcomeView的引用
	}
	public void run() {//重写的run方法法
		 while (flag) {//循环
			 welcomeView.drawIndex++;//自加
			 if(welcomeView.drawIndex>welcomeView.bitmapsID.length-1){
				 welcomeView.drawIndex = welcomeView.bitmapsID.length-10;
			 }
			 if(welcomeView.drawIndex%5 == 0){
				 welcomeView.drawString = !welcomeView.drawString;
			 }
			 try{
				 Thread.sleep(sleepSpan);//睡眠
			 }catch(Exception e){
				 e.printStackTrace();//打印异常信息
			 }
		 }
	}
	public void setFlag(boolean flag){//循环标志位的设置方法
		this.flag = flag;
	}
}