package wyf.wpf;					//���������
import android.app.Activity;			//���������
import android.app.Service;				//���������
import android.os.Bundle;				//���������
import android.os.Vibrator;				//���������
import android.widget.CompoundButton;	//���������
import android.widget.TextView;				//���������
import android.widget.ToggleButton;		//���������
import android.widget.CompoundButton.OnCheckedChangeListener;//���������
public class Sample_12_3 extends Activity {
	Vibrator vibrator;			//����һ��Vibrator����
    @Override
    public void onCreate(Bundle savedInstanceState) {	//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//���õ�ǰ��Ļ
        vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);	//����Vibrator����
        ToggleButton tb1 = (ToggleButton)findViewById(R.id.tb1);		//���ToggleButton����
        tb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//����OnCheckedChangeListener������
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//��дonCheckedChanged����
				if(isChecked){		//�ж�ToggleButton��ѡ��״̬
					vibrator.vibrate(new long[]{1000,50,50,100,50}, -1);	//������
					TextView tv1 = (TextView)findViewById(R.id.tv1);		//���TextView
					tv1.setText(R.string.vibrateOn);					//����TextView�ؼ�����
				}
				else{
					vibrator.cancel();				//�ر���
					TextView tv1 = (TextView)findViewById(R.id.tv1);	//���TextView
					tv1.setText(R.string.vibrateOff);	//����TextView�ؼ�����
				}	
			}
		});
        ToggleButton tb2 = (ToggleButton)findViewById(R.id.tb2);	//���ToggleButton����
        tb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//����OnCheckedChangeListener������
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//��дonCheckedChanged����
				if(isChecked){		//�ж�ToggleButton��ѡ��״̬
					vibrator.vibrate(2500);	//������
					TextView tv2 = (TextView)findViewById(R.id.tv2);	//���TextView
					tv2.setText(R.string.vibrateOn);		//����TextView�ؼ�����
				}
				else{
					vibrator.cancel();	//�ر���
					TextView tv2 = (TextView)findViewById(R.id.tv2);	//���TextView
					tv2.setText(R.string.vibrateOff);	//����TextView�ؼ�����
				}
			}
		});
    }
}