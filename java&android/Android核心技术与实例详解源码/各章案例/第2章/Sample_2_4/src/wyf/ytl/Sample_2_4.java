package wyf.ytl;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
public class Sample_2_4 extends Activity {
	SoundPool soundPool;//���� 
	HashMap<Integer, Integer> soundPoolMap;//�����Ĺ�������
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate�ķ���
        super.onCreate(savedInstanceState);
        initSounds();//��ʼ��������Դ
        setContentView(R.layout.main);//���õ�ǰ��ʾ���û�����
        playSound(1,5);//����5��
    }
	public void initSounds(){//��ʼ��������Դ����
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     soundPoolMap.put(1, soundPool.load(this,R.raw.sound, 1));//��������
	} 
	public void playSound(int sound, int loop) {//���������ķ���
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent/streamVolumeMax;//�õ������Ĵ�С   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);//����
	}
}