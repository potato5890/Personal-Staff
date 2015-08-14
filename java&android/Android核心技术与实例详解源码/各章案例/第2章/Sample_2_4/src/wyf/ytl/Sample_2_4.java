package wyf.ytl;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
public class Sample_2_4 extends Activity {
	SoundPool soundPool;//声音 
	HashMap<Integer, Integer> soundPoolMap;//声音的管理容器
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate的方法
        super.onCreate(savedInstanceState);
        initSounds();//初始化声音资源
        setContentView(R.layout.main);//设置当前显示的用户界面
        playSound(1,5);//播放5次
    }
	public void initSounds(){//初始化声音资源方法
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     soundPoolMap.put(1, soundPool.load(this,R.raw.sound, 1));//加载声音
	} 
	public void playSound(int sound, int loop) {//播放声音的方法
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent/streamVolumeMax;//得到音量的大小   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);//播放
	}
}