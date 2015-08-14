package wyf.ytl;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
public class Sample_2_2 extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(
        		BitmapFactory.decodeResource(getResources(), 
        		R.drawable.img));
        this.setContentView(imageView);
    }
}