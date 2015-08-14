package wyf.ytl;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
public class Sample_2_3 extends Activity {
    MediaPlayer mMediaPlayer; //声音
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mMediaPlayer = MediaPlayer.create(this, R.raw.sound);//得到声音资源
        setContentView(R.layout.main);//设置当前显示的布局
        mMediaPlayer.start();//播放声音
    }
}