package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class Sample_11_2 extends Activity {
	Button myButton;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myButton = (Button) findViewById(R.id.myButton);
        myButton.setOnClickListener(
    		new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
    		}
        );
    }
}