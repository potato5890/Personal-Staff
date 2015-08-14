package wyf.ytl;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class WelcomeViewDrawThread extends Thread{
	WelcomeView welcomeView;//welcomeView������
	private int sleepSpan = 100;
	private boolean flag = true;//ѭ�����λ
	private SurfaceHolder surfaceHolder;//surfaceHolder������
	public WelcomeViewDrawThread(WelcomeView welcomeView){//������
		this.welcomeView = welcomeView;//�õ�welcomeView������
		surfaceHolder = welcomeView.getHolder();//�õ�surfaceHolder������
	}
	public void run() {//��д��run����
		Canvas c;//��������
        while (this.flag) {//ѭ��
            c = null;
            try {
            	//������������
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {//ͬ��
                	welcomeView.onDraw(c);//���û��Ʒ���
                }
            } finally {//��finally��֤һ����ִ��
                if (c != null) {
                	//������Ļ��ʾ����
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try{
            	Thread.sleep(sleepSpan);//˯��ָ��������
            }catch(Exception e){//�����쳣
            	e.printStackTrace();//��ӡ�쳣��Ϣ
            }
        }
	}
	public void setFlag(boolean flag){//ѭ�����λ��set����
		this.flag = flag;
	}
}
