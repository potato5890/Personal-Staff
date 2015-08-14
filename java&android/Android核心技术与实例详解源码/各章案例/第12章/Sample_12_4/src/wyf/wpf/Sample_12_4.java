package wyf.wpf;				//声明包语句

import android.app.Activity;			//引入相关类
import android.app.Service;			//引入相关类
import android.media.AudioManager;		//引入相关类
import android.media.MediaPlayer;		//引入相关类
import android.os.Bundle;				//引入相关类
import android.view.View;				//引入相关类
import android.widget.Button;			//引入相关类
import android.widget.CompoundButton;		//引入相关类
import android.widget.ToggleButton;		//引入相关类	
import android.widget.CompoundButton.OnCheckedChangeListener;//引入相关类
	
public class Sample_12_4 extends Activity {
	MediaPlayer mp;			//声明MediaPlayer对象
	AudioManager am;							//声明AudioManager对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//设置当前屏幕
        am = (AudioManager)getSystemService(Service.AUDIO_SERVICE);//创建AudioManager对象
        Button btnPlay = (Button)findViewById(R.id.btnPlay);		//获得Button对象
        btnPlay.setOnClickListener(new View.OnClickListener() {	//设置监听器
			@Override
			public void onClick(View v) {				//重写onClick方法
				try {
					mp = MediaPlayer.create(Sample_12_4.this, R.raw.music);
					mp.setLooping(true);		//设置循环播放
					mp.start();					//播放声音
				} 
				catch (Exception e) {			//捕获并打印异常
					e.printStackTrace();
				}
			}
		});	
        ToggleButton tbMute = (ToggleButton)findViewById(R.id.tbMute);	//获得ToggleButton对象
        tbMute.setOnCheckedChangeListener(new OnCheckedChangeListener() {	//添加监听器
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {	//重写onCheckedChanged方法
				am.setStreamMute(AudioManager.STREAM_MUSIC, !isChecked);	//设置是否静音
			}
		});
        Button btnUpper = (Button)findViewById(R.id.btnUpper);		//获得Button对象
        btnUpper.setOnClickListener(new View.OnClickListener() {	//添加监听器
			@Override
			public void onClick(View v) {			//重写onClick方法
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 
						AudioManager.FLAG_SHOW_UI);	//调高声音
			}
		});
        Button btnLower = (Button)findViewById(R.id.btnLower);		//获得Button对象
        btnLower.setOnClickListener(new View.OnClickListener() {	//添加监听器
			@Override
			public void onClick(View v) {						//重写onClick方法
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 
						AudioManager.FLAG_SHOW_UI);		//调低声音
			}
		});
    }
}