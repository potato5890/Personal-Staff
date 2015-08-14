package wyf.wpf;				//���������

import android.app.Activity;			//���������
import android.app.Service;			//���������
import android.media.AudioManager;		//���������
import android.media.MediaPlayer;		//���������
import android.os.Bundle;				//���������
import android.view.View;				//���������
import android.widget.Button;			//���������
import android.widget.CompoundButton;		//���������
import android.widget.ToggleButton;		//���������	
import android.widget.CompoundButton.OnCheckedChangeListener;//���������
	
public class Sample_12_4 extends Activity {
	MediaPlayer mp;			//����MediaPlayer����
	AudioManager am;							//����AudioManager����
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//���õ�ǰ��Ļ
        am = (AudioManager)getSystemService(Service.AUDIO_SERVICE);//����AudioManager����
        Button btnPlay = (Button)findViewById(R.id.btnPlay);		//���Button����
        btnPlay.setOnClickListener(new View.OnClickListener() {	//���ü�����
			@Override
			public void onClick(View v) {				//��дonClick����
				try {
					mp = MediaPlayer.create(Sample_12_4.this, R.raw.music);
					mp.setLooping(true);		//����ѭ������
					mp.start();					//��������
				} 
				catch (Exception e) {			//���񲢴�ӡ�쳣
					e.printStackTrace();
				}
			}
		});	
        ToggleButton tbMute = (ToggleButton)findViewById(R.id.tbMute);	//���ToggleButton����
        tbMute.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//��Ӽ�����
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//��дonCheckedChanged����
				am.setStreamMute(AudioManager.STREAM_MUSIC, !isChecked);	//�����Ƿ���
			}
		});
        Button btnUpper = (Button)findViewById(R.id.btnUpper);		//���Button����
        btnUpper.setOnClickListener(new View.OnClickListener() {	//��Ӽ�����
			@Override
			public void onClick(View v) {			//��дonClick����
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 
						AudioManager.FLAG_SHOW_UI);	//��������
			}
		});
        Button btnLower = (Button)findViewById(R.id.btnLower);		//���Button����
        btnLower.setOnClickListener(new View.OnClickListener() {	//��Ӽ�����
			@Override
			public void onClick(View v) {						//��дonClick����
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 
						AudioManager.FLAG_SHOW_UI);		//��������
			}
		});
    }
}