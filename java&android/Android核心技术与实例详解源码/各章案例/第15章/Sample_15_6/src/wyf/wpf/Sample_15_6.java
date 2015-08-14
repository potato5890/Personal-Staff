package wyf.wpf;			//���������

import android.app.Activity;	//���������
import android.content.Intent;		//���������
import android.net.Uri;				//���������
import android.os.Bundle;			//���������
import android.view.View;			//���������
import android.widget.Button;		//���������
import android.widget.EditText;		//���������
import android.widget.Toast;		//���������
	
public class Sample_15_6 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//���ó���ǰ��Ļ
        Button btn = (Button)findViewById(R.id.btn);	//���Button����
        btn.setOnClickListener(new View.OnClickListener() {	//Ϊ��ť��Ӽ�����
			@Override
			public void onClick(View v) {				//��дonClick����
				EditText etLong = (EditText)findViewById(R.id.etLong);	//��ȡEditText�ؼ�
				EditText etLat = (EditText)findViewById(R.id.etLat);	//��ȡEditText�ؼ�
				String sLong = etLong.getEditableText().toString().trim();	//��ȡ����ľ���
				String sLat = etLat.getEditableText().toString().trim();	//��ȡ�����γ��
				if(sLong.equals("") || sLat.equals("")){		//���û�����뾭�Ȼ�γ��
					Toast.makeText(Sample_15_6.this, 
									"��������ȷ�ľ�γ�ȣ�", 
									Toast.LENGTH_LONG).show();	//���������Ϣ
					return;										//����
				}
				String sUrl = "google.streetview:cbll="+sLat+","+sLong;	//����Uri�ַ���
				Intent i = new Intent();							//����Intent����
				i.setAction(Intent.ACTION_VIEW);				//����Intent��Action
				Uri uri = Uri.parse(sUrl);						//����Uri����
				i.setData(uri);									//����Intent��Data
				startActivity(i);								//����Intent�����־��������
			}
		});
    }
}