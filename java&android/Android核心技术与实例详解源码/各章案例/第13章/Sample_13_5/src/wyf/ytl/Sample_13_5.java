package wyf.ytl;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Sample_13_5 extends Activity implements OnClickListener{
	Button start;//Â¼Òô°´Å¥
	Button stop;//Í£Ö¹°´Å¥
	File myFile ;
	MediaRecorder myMediaRecorder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		if(v == start){//°´ÏÂÂ¼Òô°´Å¥
			if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
				Toast.makeText(Sample_13_5.this, "Çë¼à²âÄÚ´æ¿¨", Toast.LENGTH_SHORT).show();
				return;
			}
			try {
				myFile = File.createTempFile("Sample_13_5", ".amr", Environment.getExternalStorageDirectory());
				myMediaRecorder = new MediaRecorder();
				myMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				myMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				myMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				myMediaRecorder.setOutputFile(myFile.getAbsolutePath());
				myMediaRecorder.prepare();
				myMediaRecorder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(v == stop){//°´ÏÂÍ£Ö¹°´Å¥
			if(myFile != null){
				myMediaRecorder.stop();
				myMediaRecorder.release();
				myMediaRecorder = null;
			}
		}
	}
}