package wyf.ytl;
public class TimeThread extends Thread{
	GameView gameView;//����GameView������
	boolean flag=true;//ѭ����־λ
	public TimeThread(GameView gameView){//������
		this.gameView=gameView;//�õ�GameView������
	}
	public void run(){//��д��run����
		while(flag){
			gameView.time++;//ʱ���Լ�
			try{
				Thread.sleep(1000);//˯��һ����
			}catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
		}
	}
}