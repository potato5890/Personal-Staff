package wyf.ytl;
public class WelcomeViewGoThread extends Thread{
	WelcomeView welcomeView;//welcomeView������
	private int sleepSpan = 150;
	private boolean flag = true;//ѭ�����λ
	public WelcomeViewGoThread(WelcomeView welcomeView) {//������
		this.welcomeView = welcomeView;//�õ�welcomeView������
	}
	public void run() {//��д��run������
		 while (flag) {//ѭ��
			 welcomeView.drawIndex++;//�Լ�
			 if(welcomeView.drawIndex>welcomeView.bitmapsID.length-1){
				 welcomeView.drawIndex = welcomeView.bitmapsID.length-10;
			 }
			 if(welcomeView.drawIndex%5 == 0){
				 welcomeView.drawString = !welcomeView.drawString;
			 }
			 try{
				 Thread.sleep(sleepSpan);//˯��
			 }catch(Exception e){
				 e.printStackTrace();//��ӡ�쳣��Ϣ
			 }
		 }
	}
	public void setFlag(boolean flag){//ѭ����־λ�����÷���
		this.flag = flag;
	}
}