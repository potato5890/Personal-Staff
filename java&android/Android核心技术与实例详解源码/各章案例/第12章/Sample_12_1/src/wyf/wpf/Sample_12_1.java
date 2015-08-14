package wyf.wpf;						//声明包语句

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sample_12_1 extends Activity {
	EditText et;
    @Override
    public void onCreate(Bundle savedInstanceState) { 		//重写onCreate方法
        super.onCreate(savedInstanceState);	
        setContentView(R.layout.main);					//设置当前屏幕
        Button btn = (Button)findViewById(R.id.btn);
        et = (EditText)findViewById(R.id.et);
        btn.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(Sample_12_1.this.getRequestedOrientation()==-1){		//判断是否可以获得requestedOrientation属性
				Toast.makeText(Sample_12_1.this, "系统的屏幕方无法获取!!", Toast.LENGTH_LONG).show();
			}
			else{
				if(Sample_12_1.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
					Sample_12_1.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
				else if(Sample_12_1.this.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
					Sample_12_1.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				}					
			}
		}
		});
    }
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Toast.makeText(this, "系统的屏幕方向发生改变", Toast.LENGTH_LONG).show();
		updateEditText();		//更新EditText显示的内容
		super.onConfigurationChanged(newConfig);
	}
	public void updateEditText(){
		int o = getRequestedOrientation();	//获取屏幕朝向
		switch(o){	//判断屏幕当前朝向
		case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
			et.setText("当前屏幕朝向为：PORTRAIT");
			break;
		case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
			et.setText("当前屏幕朝向为：LANDSCAPE");
			break;
		}
	}
}