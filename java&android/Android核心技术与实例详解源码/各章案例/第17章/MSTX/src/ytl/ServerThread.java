package ytl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerThread extends Thread{
	private boolean flag = true;//ѭ����־λ
	ServerSocket ss;//ServerSocket������
	public void run(){//��д��run����
		try {
			ss=new ServerSocket(9999);//������9999�˿�
		} catch (IOException e1) {//�����쳣
			e1.printStackTrace();//��ӡ�쳣��Ϣ
		}
		while(flag){//������ѭ��
			try{
				System.out.println("==========ServerThread============");
				Socket sc=ss.accept();//����ʽ�������ȴ��û�����
				System.out.println("==========a Click Connection============");
				ServerAgent sa = new ServerAgent(sc,this);//���������߳�
				sa.start();//���������߳�
			}
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
		}
	}
	public void setFlag(boolean flag){//����ѭ������
		this.flag = flag;
	}
}