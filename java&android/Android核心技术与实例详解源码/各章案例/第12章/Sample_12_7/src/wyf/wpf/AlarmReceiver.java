package wyf.wpf;			//���������
		
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {	//��дonReceive����
		Intent i = new Intent(context,AlarmActivity.class);		//����Intent����
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);				//���ñ�־λ
		context.startActivity(i);								//����Activity
	}
	
}