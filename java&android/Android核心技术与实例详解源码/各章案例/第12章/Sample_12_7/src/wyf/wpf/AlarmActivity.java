package wyf.wpf;			//���������

import android.app.Activity;	//���������
import android.app.AlertDialog;			//���������
import android.content.DialogInterface;	//���������
import android.content.DialogInterface.OnClickListener;	//���������
import android.os.Bundle;	//���������

public class AlarmActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AlertDialog.Builder(AlarmActivity.this)
			.setTitle(R.string.alarmTitle)			//���ñ���
			.setMessage(R.string.alarmMsg)			//��������
			.setPositiveButton(						//���ð�ť
				R.string.alarmButton,
				new OnClickListener() {				//Ϊ��ť��Ӽ�����
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AlarmActivity.this.finish();	//����finish�����ر�Activity
					}
				})
			.create().show();				//��ʾ�Ի���
	}
}