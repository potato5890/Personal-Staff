package wyf.ytl;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
public class Sample_2_3 extends Activity {
    MediaPlayer mMediaPlayer; //����
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mMediaPlayer = MediaPlayer.create(this, R.raw.sound);//�õ�������Դ
        setContentView(R.layout.main);//���õ�ǰ��ʾ�Ĳ���
        mMediaPlayer.start();//��������
    }
}