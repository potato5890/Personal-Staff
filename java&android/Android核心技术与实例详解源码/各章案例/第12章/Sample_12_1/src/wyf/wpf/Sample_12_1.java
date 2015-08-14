package wyf.wpf;						//���������

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sample_12_1 extends Activity {
	EditText et;
    @Override
    public void onCreate(Bundle savedInstanceState) { 		//��дonCreate����
        super.onCreate(savedInstanceState);	
        setContentView(R.layout.main);					//���õ�ǰ��Ļ
        Button btn = (Button)findViewById(R.id.btn);
        et = (EditText)findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(Sample_12_1.this.getRequestedOrientation()==-1){		//�ж��Ƿ���Ի��requestedOrientation����
				Toast.makeText(Sample_12_1.this, "ϵͳ����Ļ���޷���ȡ!!", Toast.LENGTH_LONG).show();
			}
			else{
				if(Sample_12_1.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
					Sample_12_1.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
				else if(Sample_12_1.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
					Sample_12_1.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}					
			}
		}
		});
    }
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Toast.makeText(this, "ϵͳ����Ļ�������ı�", Toast.LENGTH_LONG).show();
		updateEditText();		//����EditText��ʾ������
		super.onConfigurationChanged(newConfig);
	}
	public void updateEditText(){
		int o = getRequestedOrientation();	//��ȡ��Ļ����
		switch(o){	//�ж���Ļ��ǰ����
		case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
			et.setText("��ǰ��Ļ����Ϊ��PORTRAIT");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
			et.setText("��ǰ��Ļ����Ϊ��LANDSCAPE");
			break;
		}
	}
}