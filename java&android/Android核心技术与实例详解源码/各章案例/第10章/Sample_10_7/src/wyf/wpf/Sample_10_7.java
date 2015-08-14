package wyf.wpf;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Sample_10_7 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);							//设置当前屏幕
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){	//判断是否插入SD卡
					File filePath = Environment.getExternalStorageDirectory();	//获得sd card的路径
					StatFs stat = new StatFs(filePath.getPath());		//创建StatFs对象
					long blockSize = stat.getBlockSize();		//获得block的size
					float totalBlocks = stat.getBlockCount();		//获得总容量
					int sizeInMb = (int)(blockSize*totalBlocks)/1024/1024;		//
					long availableBlocks = stat.getAvailableBlocks();	//获得可用容量
					float percent = availableBlocks/totalBlocks;		//获得可用比例
					percent = (int)(percent*1000);			//舍去多余小数位数
					TextView tv = (TextView)findViewById(R.id.tv);
					tv.setText("SD Card使用情况：\n总容量："+sizeInMb+"M。\n已用"+(1000-percent)/10.0f+"% 可用"+percent/10.f+"%。");
				}
				else{
					Toast.makeText(Sample_10_7.this, "对不起，请先插入存储卡", Toast.LENGTH_LONG).show();
				}
			}
		});
    }
}