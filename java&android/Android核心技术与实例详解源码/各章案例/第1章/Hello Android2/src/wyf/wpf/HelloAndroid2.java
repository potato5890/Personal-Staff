package wyf.wpf;					//���������
import android.app.Activity;		//���������
import android.os.Bundle;			//���������
import android.util.Log;			//���������
public class HelloAndroid2 extends Activity {
	String TAG = "MyLog";		//������־��ǩ
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v(TAG, "This is a VERBOSE message");	//���������Ϣ
        Log.d(TAG, "This is a DEBUG message");		//���������Ϣ
        Log.i(TAG, "This is an INFO message");		//�����ͨ��Ϣ
        Log.w(TAG, "This is a WARNING message");	//���������Ϣ
        Log.e(TAG, "This is a ERROR message");		//���������Ϣ
    }
}