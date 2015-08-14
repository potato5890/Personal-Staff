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
	KLSDActivity activity;//activity������
	int alpha = 100;//͸����
	
	float span = (float) 1;//�Ѷ�(1-10)
	int status = 0 ; //0��Ϸ�У�1��ͣ��, 2��Ϸʤ��, 3��Ϸʧ��
	int time=0;//ʱ��
	boolean tishi;//�Ƿ���ʾ
	
	Bitmap background;//�󱳾�
	Bitmap small_backgroud;//��������
	Bitmap stop;//��ͣ��ť
	Bitmap change;//���ⰴť
	Bitmap drop;//������ť
	Bitmap help;//��ʾ��ť
	Bitmap go_on;//��ͣͼ��
	Bitmap keyDown;//ѡ�к�Ԫ����ʽ
	Bitmap win;//ʤ��
	Bitmap fail;//ʧ��
	Bitmap select;//ѡ�еĵ�Ԫ��
	Bitmap timeBitmap;//ʱ���м��ð��
	Bitmap heart;//����ʾʱ�򻭵�
	Bitmap exit;//�Ƿ��˳�ͼƬ
	
	Bitmap key_background;//���ְ�������
	Bitmap key_bitmap;//С���ּ���

	Bitmap[] number_bitmap = new Bitmap[10];//Ĭ�ϵ�����ͼƬ 
	Bitmap[] number_input = new Bitmap[10];//���������ͼƬ
	Bitmap[] time_bitmap = new Bitmap[10];//ʱ��
	
	Paint paint;
	boolean drawkey = false;
	int[][] num;//����װ���ɵ����ּ���ȷ��
	int[][] inputNum;//���������������� 
	int[][] outputNum;//�������ϵͳ������
	float scale = (float) 0.8;//���ּ������������ŵı���
	Bitmap[] small_number;
	int r = 39;//���ּ���СԲȦ�İ뾶
	int keyx;
	int keyy;//���ּ��̵�λ��
	int downx;
	int downy;
	ShuDuSuanFa sdsf;//�����������㷨��
	TimeThread tt;//ʱ���߳� 	
	GameViewDrawThread gameViewDrawThread;
	public GameView(KLSDActivity activity) {
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);//���Callback�ӿڵ�ʵ��
		init();
		gameViewDrawThread = new GameViewDrawThread(this);
	}
	public void init(){
		paint = new Paint();//��ʼ������
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		//��ʼ������С����ͼԪ
		small_backgroud= BitmapFactory.decodeResource(getResources(), R.drawable.small_background);
		//��ʼ��������ĸ���ť 
		stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop1);//ֹͣ
		change = BitmapFactory.decodeResource(getResources(), R.drawable.change1);//����
		drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop1);//����
		help = BitmapFactory.decodeResource(getResources(), R.drawable.help1);//��ʾ
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
		win = BitmapFactory.decodeResource(getResources(), R.drawable.win);//ʤ��
		fail = BitmapFactory.decodeResource(getResources(), R.drawable.fail);//ʧ�ܽ���
		select = BitmapFactory.decodeResource(getResources(), R.drawable.select);//ѡ��
		heart = BitmapFactory.decodeResource(getResources(), R.drawable.heart);//��ʾ�ĺ���
		//ʱ���ͼƬ
		Matrix matrix = new Matrix(); 
		matrix.postScale((float)1.5, (float)1.5);//����
		timeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.time);
		Bitmap temp0 = BitmapFactory.decodeResource(getResources(), R.drawable.time0);
		time_bitmap[0] = Bitmap.createBitmap(temp0, 0, 0, temp0.getWidth(), temp0.getHeight(), matrix, true);
		for(int i=1; i<=9; i++){//ѭ��
			time_bitmap[i] = Bitmap.createBitmap(number_input[i], 0, 0, number_input[i].getWidth(), number_input[i].getHeight(), matrix, true);
		}
		//��ʼ�����ּ��̵�ͼƬ
		Matrix matrix2 = new Matrix(); //����Matrix
		matrix2.postScale(scale, scale);
		small_number = new Bitmap[10];//��ʼ��ͼƬ����
		for(int i=0; i<number_bitmap.length; i++){
			small_number[i] = Bitmap.createBitmap(number_input[i], 0, 0, number_input[i].getWidth(), number_input[i].getHeight(), matrix2, true);
		}	
		exit = BitmapFactory.decodeResource(getResources(), R.drawable.exit);//�˳�ͼƬ
		init2();//����������ʼ������
	} 
	private void init2(){
		//��������
		sdsf = new ShuDuSuanFa();
		num = sdsf.getShuDu();
		//��ʼ�����ڴ���������ֵ�����
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
		//����ʱ���߳�
		tt = new TimeThread(this);
		tt.start();
	}	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		//������ͼƬ 
		canvas.drawBitmap(background, 0, 0, paint);
		//������С ����
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				canvas.drawBitmap(small_backgroud, 5+104*j, 25+104*i, paint);
			}
		}
		//��������ĸ���ť
		canvas.drawBitmap(stop, 150,380, paint);
		canvas.drawBitmap(change, 230,380, paint);
		canvas.drawBitmap(drop, 150,420, paint);
		canvas.drawBitmap(help, 230,420, paint);
		if(status == 2){//��Ϸʤ�� 
			tt.flag = false;
			canvas.drawBitmap(win, 40, 160, paint);
		}else if(status == 3){//��Ϸʧ��
			tt.flag = false;
			canvas.drawBitmap(fail, 80, 180, paint);
		}else if(status == 4){//�Ƿ��˳�
			canvas.drawBitmap(exit, 70, 180, paint);	
		}else if(status == 0){//��Ϸ��
			if(tishi == true){
				canvas.drawBitmap(heart, 10, 340, paint);
			}
			//��ȫ������
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
			//���û����������
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
			//��ѡ�еĵ�Ԫ��
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
	        //�����ּ���
			if(drawkey == true){
				canvas.drawBitmap(key_bitmap, keyx, keyy, paint);
			}
		}
		else if(status == 1){//��ͣ��
			tt.flag = false;
			canvas.drawBitmap(go_on, 80, 180, paint);
		}
		//����ʱ��
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
    	//������
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
	public boolean onTouchEvent(MotionEvent event){//���������� 
		float x = event.getX();
		float y = event.getY();
		if(status == 2 || status == 3){//��Ϸʤ����ʧ��ʱ
			activity.myHandler.sendEmptyMessage(2);
		}else if(status == 4){//�Ƿ��˳�ʱ
			if(x>70 && x<105 && y>230 && y<265){//�����
				System.exit(0);
			}
			else if(x>200 && x<235 && y>230 && y<265){//�����
				status = 0;
			}
		}else{
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN://����
					mouseDown(x,y);
					break;
				case MotionEvent.ACTION_UP://̧��
					mouseUP(x,y);
					break;
			}				
		}
		return true;
	 }
	
	private boolean isFinish(){//�ж��Ƿ�����
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
	private void isWin(){//�ж��Ƿ�Ӯ���ı�statusֵ��ʾ��Ӯ
		 int temp;
		 for(int i=0; i<9; i++){
			 for(int j=0; j<9; j++) {
				temp =  inputNum[i][j];
				if(temp != 0){
					if(temp != num[i][j]){//����ȷ�𰸲�ͬ
						status = 3;
						return;
					}
				}
			 }
		 }
		 status = 2;//��Ϸʤ��
	}
	private void mouseDown(float x, float y) {//��갴�µ�ʱ��
		if(drawkey == true){//������ּ��̴��ڵĻ�
			 if(x>keyx&&x<keyx+key_background.getWidth()&&y>keyy&&y<keyy+key_background.getHeight()){//�������ּ�����
				 for(int i=0; i<9; i++){
					 if(x>(((keyx+r)+r*Math.sin((40.0*i)/180*Math.PI))+4)&&x<(((keyx+r)+r*Math.sin((40.0*i)/180*Math.PI))+4+small_number[i].getWidth())
							 &&y>(keyy+(r-r*Math.cos((40.0*i)/180*Math.PI)))&&y<(keyy+(r-r*Math.cos((40.0*i)/180*Math.PI))+small_number[i].getHeight())){
						 inputNum[downy][downx] = i+1;
					 }
				 }
			 }
			 if(isFinish()){//�ж��Ƿ��Ѿ�����,�����ͳ���Ӯ
				 isWin();
				 return;
			 }
			 drawkey = false;
			 return;
		 }
		 //���㵥����λ��
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
		 if(y<330){//��������������ͳ����ּ���
			 if(x>200){
				 this.keyx = (int) (x-key_background.getWidth());
			 }else{
				 this.keyx = (int) x;
			 }
			 this.keyy = (int) y;
			 if(outputNum[downy][downx] == 0){
				 if(tishi == true){
					 inputNum[downy][downx] = num[downy][downx];
					 if(isFinish()){//�ж��Ƿ��Ѿ�����,�����ͳ����
						 isWin();
						 return;
					 }
					 tishi = false;
				 }else{
					 drawkey = true;//��ʾ���ּ���	
					 drawkey();
					 DrawKeyThread dk = new DrawKeyThread(key_bitmap,this);
					 dk.start();
				 }
			 }
		 }
		 if(x>150&&x<200&&y>380&&y<410){//������ͣ
			 stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop2);
			 if(status == 0){
				 status = 1;//��ͣ
			 }else {
				 status = 0;
				 //��������ʱ���߳�
				 tt = new TimeThread(this);
				 tt.start();
			 }
		 }
		 if(x>230&&x<280&&y>380&&y<410){//���»���
			change = BitmapFactory.decodeResource(getResources(), R.drawable.change2);
			time = 0;
			tt.flag = false;
			status = 0;
			init2();//���³�ʼ������
		 }
		 if(x>150&&x<200&&y>420&&y<450){//���·���
			 drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop2);
			 this.status = 4;
		 }
		 if(x>230&&x<280&&y>420&&y<450){//������ʾ
			 help = BitmapFactory.decodeResource(getResources(), R.drawable.help2);
			 tishi = true;
		 }
	}
	private void mouseUP(float x, float y){//���̧���ʱ��
		if(x>150&&x<200&&y>380&&y<410){//����ͣ��̧��
			stop = BitmapFactory.decodeResource(getResources(), R.drawable.stop1);
		}
		if(x>230&&x<280&&y>380&&y<410){//����
			change = BitmapFactory.decodeResource(getResources(), R.drawable.change1);
		}
		if(x>150&&x<200&&y>420&&y<450){//����
			drop = BitmapFactory.decodeResource(getResources(), R.drawable.drop1);
		}
		if(x>230&&x<280&&y>420&&y<450){//��ʾ
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
        boolean retry = true;//ѭ����־λ
        gameViewDrawThread.setFlag(false);
        while (retry) {
            try {
            	gameViewDrawThread.join();
                retry = false;
            } 
            catch (Exception e) {//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
            	e.printStackTrace();
            }
        }
	}
}