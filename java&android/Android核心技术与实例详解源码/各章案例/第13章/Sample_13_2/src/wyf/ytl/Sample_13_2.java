package wyf.ytl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class Sample_13_2 extends Activity implements OnClickListener{
    Button myButton1;//声明按钮的引用
    Button myButton2;//声明按钮的引用
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        myButton1 = (Button) findViewById(R.id.myButton1);//得到按钮的引用
        myButton2 = (Button) findViewById(R.id.myButton2);//得到按钮的引用
        myButton1.setOnClickListener(this);//添加监听
        myButton2.setOnClickListener(this);//添加监听
    }
	@Override
	public void onClick(View v) {
		if(v == myButton1){//按下了按钮时
			Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			String myTitle = getResources().getString(R.string.myTitle);//标题
			Parcelable icon = 
				Intent.ShortcutIconResource.fromContext(this, R.drawable.png1);//图标
			//创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
			Intent myIntent = new Intent(this, Sample_13_2.class);
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, myTitle);//快捷方式的标题
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//快捷方式的图标
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);//快捷方式的动作
			sendBroadcast(addIntent);//发送广播
		}
		else if(v == myButton2){//按下了退出按钮
			System.exit(0);//退出系统 
		}
	}
}