package wyf.wpf;					//声明包语句
import android.app.Activity;		//引入相关类
import android.os.Bundle;			//引入相关类
import android.util.Log;			//引入相关类
public class HelloAndroid2 extends Activity {
	String TAG = "MyLog";		//定义日志标签
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v(TAG, "This is a VERBOSE message");	//输出冗余消息
        Log.d(TAG, "This is a DEBUG message");		//输出调试消息
        Log.i(TAG, "This is an INFO message");		//输出普通消息
        Log.w(TAG, "This is a WARNING message");	//输出警告消息
        Log.e(TAG, "This is a ERROR message");		//输出错误消息
    }
}