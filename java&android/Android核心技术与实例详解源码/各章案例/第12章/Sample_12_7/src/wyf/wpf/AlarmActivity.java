package wyf.wpf;			//声明包语句

import android.app.Activity;	//引入相关类
import android.app.AlertDialog;			//引入相关类
import android.content.DialogInterface;	//引入相关类
import android.content.DialogInterface.OnClickListener;	//引入相关类
import android.os.Bundle;	//引入相关类

public class AlarmActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AlertDialog.Builder(AlarmActivity.this)
			.setTitle(R.string.alarmTitle)			//设置标题
			.setMessage(R.string.alarmMsg)			//设置内容
			.setPositiveButton(						//设置按钮
				R.string.alarmButton,
				new OnClickListener() {				//为按钮添加监听器
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AlarmActivity.this.finish();	//调用finish方法关闭Activity
					}
				})
			.create().show();				//显示对话框
	}
}