package wyf.wpf;			//声明包语句
		
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {	//重写onReceive方法
		Intent i = new Intent(context,AlarmActivity.class);		//创建Intent对象
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				//设置标志位
		context.startActivity(i);								//启动Activity
	}
	
}