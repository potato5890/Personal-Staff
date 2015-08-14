package wyf.ytl;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class Sample_13_2 extends Activity implements OnClickListener{
    Button myButton1;//������ť������
    Button myButton2;//������ť������
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ���û�����
        myButton1 = (Button) findViewById(R.id.myButton1);//�õ���ť������
        myButton2 = (Button) findViewById(R.id.myButton2);//�õ���ť������
        myButton1.setOnClickListener(this);//��Ӽ���
        myButton2.setOnClickListener(this);//��Ӽ���
    }
	@Override
	public void onClick(View v) {
		if(v == myButton1){//�����˰�ťʱ
			Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			String myTitle = getResources().getString(R.string.myTitle);//����
			Parcelable icon = 
				Intent.ShortcutIconResource.fromContext(this, R.drawable.png1);//ͼ��
			//���������ݷ�ʽ�����Intent,�ô�����������Ŀ�ݷ�ʽ���ٴ������ó���
			Intent myIntent = new Intent(this, Sample_13_2.class);
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, myTitle);//��ݷ�ʽ�ı���
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//��ݷ�ʽ��ͼ��
			addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);//��ݷ�ʽ�Ķ���
			sendBroadcast(addIntent);//���͹㲥
		}
		else if(v == myButton2){//�������˳���ť
			System.exit(0);//�˳�ϵͳ 
		}
	}
}