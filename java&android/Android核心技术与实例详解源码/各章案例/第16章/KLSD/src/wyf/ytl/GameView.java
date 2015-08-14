package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	KLSDActivity activity;//activity的引用
	int alpha = 100;//透明度
	
	float span = (float) 1;//难度(1-10)
	int status = 0 ; //0游戏中，1暂停中, 2游戏胜利, 3游戏失败
	int time=0;//时间
	boolean tishi;//是否提示
	
	Bitmap background;//大背景
	Bitmap small_backgroud;//背景方格
	Bitmap stop;//暂停按钮
	Bitmap change;//换题按钮
	Bitmap drop;//放弃按钮
	Bitmap help;//提示按钮
	Bitmap go_on;//暂停图像
	Bitmap keyDown;//选中后单元格样式
	Bitmap win;//胜利
	Bitmap fail;//失败
	Bitmap select;//选中的单元格
	Bitmap timeBitmap;//时间中间的冒号
	Bitmap heart;//有提示时候画的
	Bitmap exit;//是否退出图片
	
	Bitmap key_background;//数字按键背景
	Bitmap key_bitmap;//小数字键盘

	Bitmap[] number_bitmap = new Bitmap[10];//默认的数字图片 
	Bitmap[] number_input = new Bitmap[10];//输入的数字图片
	Bitmap[] time_bitmap = new Bitmap[10];//时间
	
	Paint paint;
	boolean drawkey = false;
	int[][] num;//用来装生成的数字即正确答案
	int[][] inputNum;//用来存放输入的数字 
	int[][] outputNum;//用来存放系统的数字
	float scale = (float) 0.8;//数字键盘上数字缩放的比例
	Bitmap[] small_number;
	int r = 39;//数字键盘小圆圈的半径
	int keyx;
	int keyy;//数字键盘的位置
	int downx;
	int downy;
	ShuDuSuanFa sdsf;//生成数独的算法类
	TimeThread tt;//时间线程 	
	GameViewDrawThread gameViewDrawThread;
	public GameView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);//添加Callback接口的实现
		init();
		gameViewDrawThread = new GameViewDrawThread(this);
	}
	public void init(){
		paint = new Paint();//初始化画笔
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		//初始化背景小方格图元
		small_backgroud= BitmapFactory.decodeResource(getResources(), R.drawable.small_background);
		//初始化下面的四个按钮 
		stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop1);//停止
		change = BitmapFactory.decodeResource(getResources(), R.drawable.change1);//换题
		drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop1);//放弃
		help = BitmapFactory.decodeResource(getResources(), R.drawable.help1);//提示
		key_background = BitmapFactory.decodeResource(getResources(), R.drawable.key_background);
		number_bitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.b0);
		number_bitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.b1); 
		number_bitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.b2);
		number_bitmap[3] = BitmapFactory.decodeResource(getResources(), R.drawable.b3);
		number_bitmap[4] = BitmapFactory.decodeResource(getResources(), R.drawable.b4);
		number_bitmap[5] = BitmapFactory.decodeResource(getResources(), R.drawable.b5);
		number_bitmap[6] = BitmapFactory.decodeResource(getResources(), R.drawable.b6);
		number_bitmap[7] = BitmapFactory.decodeResource(getResources(), R.drawable.b7);
		number_bitmap[8] = BitmapFactory.decodeResource(getResources(), R.drawable.b8);
		number_bitmap[9] = BitmapFactory.decodeResource(getResources(), R.drawable.b9);
		number_input[0] = BitmapFactory.decodeResource(getResources(), R.drawable.a0);
		number_input[1] = BitmapFactory.decodeResource(getResources(), R.drawable.a1); 
		number_input[2] = BitmapFactory.decodeResource(getResources(), R.drawable.a2);
		number_input[3] = BitmapFactory.decodeResource(getResources(), R.drawable.a3);
		number_input[4] = BitmapFactory.decodeResource(getResources(), R.drawable.a4);
		number_input[5] = BitmapFactory.decodeResource(getResources(), R.drawable.a5);
		number_input[6] = BitmapFactory.decodeResource(getResources(), R.drawable.a6);
		number_input[7] = BitmapFactory.decodeResource(getResources(), R.drawable.a7);
		number_input[8] = BitmapFactory.decodeResource(getResources(), R.drawable.a8);
		number_input[9] = BitmapFactory.decodeResource(getResources(), R.drawable.a9);
		go_on = BitmapFactory.decodeResource(getResources(), R.drawable.go_on);
		keyDown = BitmapFactory.decodeResource(getResources(), R.drawable.c0);
		win = BitmapFactory.decodeResource(getResources(), R.drawable.win);//胜利
		fail = BitmapFactory.decodeResource(getResources(), R.drawable.fail);//失败界面
		select = BitmapFactory.decodeResource(getResources(), R.drawable.select);//选择
		heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);//提示的红心
		//时间的图片
		Matrix matrix = new Matrix(); 
		matrix.postScale((float)1.5, (float)1.5);//缩放
		timeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.time);
		Bitmap temp0 = BitmapFactory.decodeResource(getResources(), R.drawable.time0);
		time_bitmap[0] = Bitmap.createBitmap(temp0, 0, 0, temp0.getWidth(), temp0.getHeight(), matrix, true);
		for(int i=1; i<=9; i++){//循环
			time_bitmap[i] = Bitmap.createBitmap(number_input[i], 0, 0, number_input[i].getWidth(), number_input[i].getHeight(), matrix, true);
		}
		//初始化数字键盘的图片
		Matrix matrix2 = new Matrix(); //创建Matrix
		matrix2.postScale(scale, scale);
		small_number = new Bitmap[10];//初始化图片数组
		for(int i=0; i<number_bitmap.length; i++){
			small_number[i] = Bitmap.createBitmap(number_input[i], 0, 0, number_input[i].getWidth(), number_input[i].getHeight(), matrix2, true);
		}	
		exit = BitmapFactory.decodeResource(getResources(), R.drawable.exit);//退出图片
		init2();//调用其他初始化方法
	} 
	private void init2(){
		//生成数独
		sdsf = new ShuDuSuanFa();
		num = sdsf.getShuDu();
		//初始化用于存放输入数字的数组
		inputNum = new int[9][9];
		for(int i=0;i<inputNum.length; i++){
			for(int j=0; j<inputNum[i].length; j++){
				inputNum[i][j] = 0;
			}
		}
		outputNum = new int[9][9];
		for(int i=0;i<num.length; i++){
			for(int j=0; j<num[i].length; j++){
				if(10*Math.random()>span){
					outputNum[i][j] = num[i][j];
				}else {
					outputNum[i][j] = 0;
				}
			}
		}
		//启动时间线程
		tt = new TimeThread(this);
		tt.start();
	}	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		//画背景图片 
		canvas.drawBitmap(background, 0, 0, paint);
		//画背景小 方格
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				canvas.drawBitmap(small_backgroud, 5+104*j, 25+104*i, paint);
			}
		}
		//画下面的四个按钮
		canvas.drawBitmap(stop, 150,380, paint);
		canvas.drawBitmap(change, 230,380, paint);
		canvas.drawBitmap(drop, 150,420, paint);
		canvas.drawBitmap(help, 230,420, paint);
		if(status == 2){//游戏胜利 
			tt.flag = false;
			canvas.drawBitmap(win, 40, 160, paint);
		}else if(status == 3){//游戏失败
			tt.flag = false;
			canvas.drawBitmap(fail, 80, 180, paint);
		}else if(status == 4){//是否退出
			canvas.drawBitmap(exit, 70, 180, paint);	
		}else if(status == 0){//游戏中
			if(tishi == true){
				canvas.drawBitmap(heart, 10, 340, paint);
			}
			//画全部数字
			tt.flag = true;
			int x; 
			int y;
	        for(int i = 0;i < 9;i++){
	            for(int j = 0;j < 9;j++){
	            	x = 16+28*j;
	            	y = 35+29*i;
	            	if(j>=3&&j<6){
	            		x += 20;
	            	}
	            	if(j>=6){
	            		x += 42;
	            	}
	            	if(i>=3&&i<6){
	            		y += 18;
	            	}
	            	if(i>=6){
	            		y += 34;
	            	}
	            	canvas.drawBitmap(number_bitmap[outputNum[i][j]], x, y, paint);
	            } 
	        }
			//画用户输入的数字
	        for(int i = 0;i < 9;i++){
	            for(int j = 0;j < 9;j++){
	            	x = 16+28*j;
	            	y = 35+29*i;
	            	if(j>=3&&j<6){
	            		x += 20;
	            	}
	            	if(j>=6){
	            		x += 42;
	            	}
	            	if(i>=3&&i<6){
	            		y += 18;
	            	}
	            	if(i>=6){
	            		y += 34;
	            	} 
	            	canvas.drawBitmap(number_input[inputNum[i][j]], x, y, paint);
	            } 
	        }
			//画选中的单元格
        	x = 15+28*downx;
        	y = 34+29*downy;
        	if(downx>=3&&downx<6){
        		x += 19;
        	}   
        	if(downx>=6){
        		x += 41;
        	}
        	if(downy>=3&&downy<6){
        		y += 17;
        	}
        	if(downy>=6){
        		y += 34;
        	} 
        	canvas.drawBitmap(select, x, y, paint);
	        //画数字键盘
			if(drawkey == true){
				canvas.drawBitmap(key_bitmap, keyx, keyy, paint);
			}
		}
		else if(status == 1){//暂停中
			tt.flag = false;
			canvas.drawBitmap(go_on, 80, 180, paint);
		}
		//绘制时间
		canvas.drawBitmap(timeBitmap, 60, 400, paint);
		int temp = time/60;
		String timeStr = temp+"";
		if(timeStr.length()<2){
			timeStr = "0" + timeStr;
		}
    	for(int i=0;i<2;i++){
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(time_bitmap[tempScore], 20+i*20, 400, paint);
    	}
    	//画分钟
    	temp = time%60;
		timeStr = temp+"";
		if(timeStr.length()<2){   
			timeStr = "0" + timeStr;
		}
    	for(int i=0;i<2;i++){
    		int tempScore=timeStr.charAt(i)-'0';
    		canvas.drawBitmap(time_bitmap[tempScore], 80+i*20, 400, paint);
    	}		
	}
	public void drawkey(){
		Bitmap b = Bitmap.createBitmap(key_background.getWidth(), key_background.getHeight(), Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawBitmap(key_background, 0, 0, paint);
		for(int i=0; i<9; i++){
			c.drawBitmap(small_number[i+1], (float)((0+r)+r*Math.sin((40.0*i)/180*Math.PI))+2, (float)(0+(r-r*Math.cos((40.0*i)/180*Math.PI))), paint);
		}
		key_bitmap = b;
	}
	public boolean onTouchEvent(MotionEvent event){//触屏监听器 
		float x = event.getX();
		float y = event.getY();
		if(status == 2 || status == 3){//游戏胜利或失败时
			activity.myHandler.sendEmptyMessage(2);
		}else if(status == 4){//是否退出时
			if(x>70 && x<105 && y>230 && y<265){//点击是
				System.exit(0);
			}
			else if(x>200 && x<235 && y>230 && y<265){//点击否
				status = 0;
			}
		}else{
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN://按下
					mouseDown(x,y);
					break;
				case MotionEvent.ACTION_UP://抬起
					mouseUP(x,y);
					break;
			}				
		}
		return true;
	 }
	
	private boolean isFinish(){//判断是否填满
		int count = 0;
		int temp;
		for(int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				temp = inputNum[i][j];
				if(temp != 0){
					count++;
				}
				temp = outputNum[i][j];
				if(temp != 0){
					count++;
				}
			}
		}
		if(count<81){
			return false;
		}else {
			return true;
		}
	}
	private void isWin(){//判断是否赢，改变status值表示输赢
		 int temp;
		 for(int i=0; i<9; i++){
			 for(int j=0; j<9; j++) {
				temp =  inputNum[i][j];
				if(temp != 0){
					if(temp != num[i][j]){//和正确答案不同
						status = 3;
						return;
					}
				}
			 }
		 }
		 status = 2;//游戏胜利
	}
	private void mouseDown(float x, float y) {//鼠标按下的时候
		if(drawkey == true){//如果数字键盘存在的话
			 if(x>keyx&&x<keyx+key_background.getWidth()&&y>keyy&&y<keyy+key_background.getHeight()){//按在数字键盘上
				 for(int i=0; i<9; i++){
					 if(x>(((keyx+r)+r*Math.sin((40.0*i)/180*Math.PI))+4)&&x<(((keyx+r)+r*Math.sin((40.0*i)/180*Math.PI))+4+small_number[i].getWidth())
							 &&y>(keyy+(r-r*Math.cos((40.0*i)/180*Math.PI)))&&y<(keyy+(r-r*Math.cos((40.0*i)/180*Math.PI))+small_number[i].getHeight())){
						 inputNum[downy][downx] = i+1;
					 }
				 }
			 }
			 if(isFinish()){//判断是否已经填满,填满就出输赢
				 isWin();
				 return;
			 }
			 drawkey = false;
			 return;
		 }
		 //计算单击的位置
		 if(x>5&&x<108){
			 downx = (int) ((x-15)/25);
		 }
		 if(x>108&&x<210){
			 downx = (int) ((x-123)/25)+3;
		 }
		 if(x>210&&x<310){
			 downx = (int) ((x-225)/25)+6;
		 }
		 if(y>25&&y<125){
			 downy = (int) ((y-35)/25);
		 }
		 if(y>125&&y<230){
			 downy = (int) ((y-140)/25)+3;
		 }
		 if(y>230&&y<330){
			 downy = (int) ((y-245)/25)+6;
		 }
		 if(y<330){//如果点击不是下面就出数字键盘
			 if(x>200){
				 this.keyx = (int) (x-key_background.getWidth());
			 }else{
				 this.keyx = (int) x;
			 }
			 this.keyy = (int) y;
			 if(outputNum[downy][downx] == 0){
				 if(tishi == true){
					 inputNum[downy][downx] = num[downy][downx];
					 if(isFinish()){//判断是否已经填满,填满就出结果
						 isWin();
						 return;
					 }
					 tishi = false;
				 }else{
					 drawkey = true;//显示数字键盘	
					 drawkey();
					 DrawKeyThread dk = new DrawKeyThread(key_bitmap,this);
					 dk.start();
				 }
			 }
		 }
		 if(x>150&&x<200&&y>380&&y<410){//按下暂停
			 stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop2);
			 if(status == 0){
				 status = 1;//暂停
			 }else {
				 status = 0;
				 //重新启动时间线程
				 tt = new TimeThread(this);
				 tt.start();
			 }
		 }
		 if(x>230&&x<280&&y>380&&y<410){//按下换题
			change = BitmapFactory.decodeResource(getResources(), R.drawable.change2);
			time = 0;
			tt.flag = false;
			status = 0;
			init2();//重新初始化界面
		 }
		 if(x>150&&x<200&&y>420&&y<450){//按下放弃
			 drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop2);
			 this.status = 4;
		 }
		 if(x>230&&x<280&&y>420&&y<450){//按下提示
			 help = BitmapFactory.decodeResource(getResources(), R.drawable.help2);
			 tishi = true;
		 }
	}
	private void mouseUP(float x, float y){//鼠标抬起的时候
		if(x>150&&x<200&&y>380&&y<410){//在暂停处抬起
			stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop1);
		}
		if(x>230&&x<280&&y>380&&y<410){//换题
			change = BitmapFactory.decodeResource(getResources(), R.drawable.change1);
		}
		if(x>150&&x<200&&y>420&&y<450){//放弃
			drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop1);
		}
		if(x>230&&x<280&&y>420&&y<450){//提示
			help = BitmapFactory.decodeResource(getResources(), R.drawable.help1);
		}
	}	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}
	public void surfaceCreated(SurfaceHolder holder) {
		gameViewDrawThread.setFlag(true);
		gameViewDrawThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;//循环标志位
        gameViewDrawThread.setFlag(false);
        while (retry) {
            try {
            	gameViewDrawThread.join();
                retry = false;
            } 
            catch (Exception e) {//不断地循环，直到刷帧线程结束
            	e.printStackTrace();
            }
        }
	}
}