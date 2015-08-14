package wyf.wpf;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Sample_10_8 extends Activity {
	MediaPlayer mp = new MediaPlayer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					mp.setDataSource("/sdcard/music.mp3");
					mp.prepare();
					mp.start();
					TextView tv = (TextView)findViewById(R.id.tv);
					tv.setText("“Ù¿÷’˝‘⁄≤•∑≈...");
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
    }
}