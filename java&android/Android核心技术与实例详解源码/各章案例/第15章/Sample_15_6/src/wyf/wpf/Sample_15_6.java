package wyf.wpf;			//声明包语句

import android.app.Activity;	//引入相关类
import android.content.Intent;		//引入相关类
import android.net.Uri;				//引入相关类
import android.os.Bundle;			//引入相关类
import android.view.View;			//引入相关类
import android.widget.Button;		//引入相关类
import android.widget.EditText;		//引入相关类
import android.widget.Toast;		//引入相关类
	
public class Sample_15_6 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//设置程序当前屏幕
        Button btn = (Button)findViewById(R.id.btn);	//获得Button对象
        btn.setOnClickListener(new View.OnClickListener() {	//为按钮添加监听器
			@Override
			public void onClick(View v) {				//重写onClick方法
				EditText etLong = (EditText)findViewById(R.id.etLong);	//获取EditText控件
				EditText etLat = (EditText)findViewById(R.id.etLat);	//获取EditText控件
				String sLong = etLong.getEditableText().toString().trim();	//获取输入的经度
				String sLat = etLat.getEditableText().toString().trim();	//获取输入的纬度
				if(sLong.equals("") || sLat.equals("")){		//如果没有输入经度或纬度
					Toast.makeText(Sample_15_6.this, 
									"请输入正确的经纬度！", 
									Toast.LENGTH_LONG).show();	//输出错误信息
					return;										//返回
				}
				String sUrl = "google.streetview:cbll="+sLat+","+sLong;	//生成Uri字符串
				Intent i = new Intent();							//创建Intent对象
				i.setAction(Intent.ACTION_VIEW);				//设置Intent的Action
				Uri uri = Uri.parse(sUrl);						//生成Uri对象
				i.setData(uri);									//设置Intent的Data
				startActivity(i);								//发出Intent启动街景服务程序
			}
		});
    }
}