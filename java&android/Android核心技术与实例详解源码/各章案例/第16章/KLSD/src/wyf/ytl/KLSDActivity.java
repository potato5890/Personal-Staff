package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
public class KLSDActivity extends Activity {
	WelcomeView welcomeView;//欢迎动画界面的引用
	GameView gameView;//游戏界面的引用
	AboutView aboutView;//关于界面
	HelpView helpView;//帮助界面
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//欢迎界面发送的消息
        		if(welcomeView != null){
        			welcomeView = null;
        		}
        		initGameView();
        		KLSDActivity.this.setContentView(gameView);
        	}else if(msg.what == 2){//游戏胜利或者失败时点击屏幕
        		if(gameView != null){
        			gameView = null;
        		}
        		initWelcomeView();//初始化欢迎界面
        		KLSDActivity.this.setContentView(welcomeView);//切换到欢迎界面
        	}else if(msg.what == 3){
        		initAboutView();
        		KLSDActivity.this.setContentView(aboutView);//切换到关于界面
        	}else if(msg.what == 4){
        		initHelpView();
        		KLSDActivity.this.setContentView(helpView);//切换到帮助界面
        	}
        }
	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		initWelcomeView();//初始化欢迎界面
		this.setContentView(welcomeView);//切换到欢迎界面
    }
    public void initWelcomeView(){//初始化欢迎界面
    	welcomeView = new WelcomeView(this);
    }
    public void initGameView(){//初始化游戏界面
    	gameView = new GameView(this);
    }
    public void initAboutView(){//初始化关于界面
    	aboutView = new AboutView(this);
    }
    public void initHelpView(){//初始化帮助界面
    	helpView = new HelpView(this);
    }
}