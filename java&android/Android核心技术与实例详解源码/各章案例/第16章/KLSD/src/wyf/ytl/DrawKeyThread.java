package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
public class DrawKeyThread extends Thread {
	GameView gameView;//gameView������
	int span = 40;//͸���ȵĸ��Ĳ���
	boolean flag = true;;//ѭ����־λ
	Bitmap key_bitmap;//ͼƬ
	int alpha=0;//͸����
	public DrawKeyThread(Bitmap key_bitmap,GameView gameView){//������
		this.key_bitmap = key_bitmap;//�õ�ͼƬ������
		this.gameView = gameView;//�õ�gameView������
	}
	public void run(){//��д��run����
		while(flag){//ѭ��
			Bitmap b = Bitmap.createBitmap(key_bitmap.getWidth(), key_bitmap.getHeight(), Config.ARGB_8888);
			Canvas c = new Canvas(b);//��������
			Paint p = new Paint();//��������
			p.setAlpha(alpha);//����͸����
			c.drawBitmap(key_bitmap, 0, 0, p);//����ͼƬ
			gameView.key_bitmap = b;
			alpha+=span;//�ı�͸����
			if(alpha>255){//��͸���ȹ���ʱ
				alpha = 255;
				flag = false;//ֹͣѭ��
			}
			try{
				Thread.sleep(100);//˯��ָ��������
			}catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
		}
	}
}
