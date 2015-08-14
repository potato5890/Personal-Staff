package wyf.wpf;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Sample_10_7 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);							//���õ�ǰ��Ļ
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){	//�ж��Ƿ����SD��
					File filePath = Environment.getExternalStorageDirectory();	//���sd card��·��
					StatFs stat = new StatFs(filePath.getPath());		//����StatFs����
					long blockSize = stat.getBlockSize();		//���block��size
					float totalBlocks = stat.getBlockCount();		//���������
					int sizeInMb = (int)(blockSize*totalBlocks)/1024/1024;		//
					long availableBlocks = stat.getAvailableBlocks();	//��ÿ�������
					float percent = availableBlocks/totalBlocks;		//��ÿ��ñ���
					percent = (int)(percent*1000);			//��ȥ����С��λ��
					TextView tv = (TextView)findViewById(R.id.tv);
					tv.setText("SD Cardʹ�������\n��������"+sizeInMb+"M��\n����"+(1000-percent)/10.0f+"% ����"+percent/10.f+"%��");
				}
				else{
					Toast.makeText(Sample_10_7.this, "�Բ������Ȳ���洢��", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
}