package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
public class KLSDActivity extends Activity {
	WelcomeView welcomeView;//��ӭ�������������
	GameView gameView;//��Ϸ���������
	AboutView aboutView;//���ڽ���
	HelpView helpView;//��������
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//��ӭ���淢�͵���Ϣ
        		if(welcomeView != null){
        			welcomeView = null;
        		}
        		initGameView();
        		KLSDActivity.this.setContentView(gameView);
        	}else if(msg.what == 2){//��Ϸʤ������ʧ��ʱ�����Ļ
        		if(gameView != null){
        			gameView = null;
        		}
        		initWelcomeView();//��ʼ����ӭ����
        		KLSDActivity.this.setContentView(welcomeView);//�л�����ӭ����
        	}else if(msg.what == 3){
        		initAboutView();
        		KLSDActivity.this.setContentView(aboutView);//�л������ڽ���
        	}else if(msg.what == 4){
        		initHelpView();
        		KLSDActivity.this.setContentView(helpView);//�л�����������
        	}
        }
	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initWelcomeView();//��ʼ����ӭ����
		this.setContentView(welcomeView);//�л�����ӭ����
    }
    public void initWelcomeView(){//��ʼ����ӭ����
    	welcomeView = new WelcomeView(this);
    }
    public void initGameView(){//��ʼ����Ϸ����
    	gameView = new GameView(this);
    }
    public void initAboutView(){//��ʼ�����ڽ���
    	aboutView = new AboutView(this);
    }
    public void initHelpView(){//��ʼ����������
    	helpView = new HelpView(this);
    }
}