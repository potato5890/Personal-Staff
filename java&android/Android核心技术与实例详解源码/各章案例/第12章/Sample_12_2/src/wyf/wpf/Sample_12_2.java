package wyf.wpf;				//声明包语句

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class Sample_12_2 extends Activity {
	int [] imgIds ={			//图片资源的id数组
			R.drawable.w1,
			R.drawable.w2,
			R.drawable.w3,
			R.drawable.w4
	};
	int selectedIndex = -1;		//被选中的图片在id数组中的索引
	BaseAdapter ba = new BaseAdapter() {		//自定义的BaseAdapter
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(Sample_12_2.this);		//新建一个ImageView
			iv.setBackgroundResource(imgIds[position]);			//设置ImageView的背景图片
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setLayoutParams(new Gallery.LayoutParams(120, 120));	//设置相框中元素的大小
			return iv;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public int getCount() {
			return imgIds.length;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//设置当前屏幕
        Button btnClearWall = (Button)findViewById(R.id.clearWall);	//获得Button对象
        btnClearWall.setOnClickListener(new View.OnClickListener() {	//添加OnClickListener监听器
			@Override
			public void onClick(View v) {		//重写onClick方法
				try {
					Sample_12_2.this.clearWallpaper();	//还原手机壁纸
				} catch (IOException e) {				//捕获并打印异常
					e.printStackTrace();
				}
			}
		});
        Button btnGetWall = (Button)findViewById(R.id.getWall);	//获得Button对象
        btnGetWall.setOnClickListener(new View.OnClickListener() {	//为Button添加OnClickListener监听器
			@Override
			public void onClick(View v) {
				ImageView iv = (ImageView)findViewById(R.id.currWall);
				iv.setBackgroundDrawable(getWallpaper());		//设置ImageView显示的内容为当前墙纸
			}
		});
        Gallery g = (Gallery)findViewById(R.id.gallery);			//获得Gallery对象
        g.setAdapter(ba);											 //设置Gallery的BaseAdapter
        g.setSpacing(5);											//设置每个元素之间的间距
        g.setOnItemClickListener(new OnItemClickListener() {		//为Gallery添加OnItemClickListener监听器
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				selectedIndex = position;			//记录被选中的图片索引
			}
		});
        Button btnSetWall = (Button)findViewById(R.id.setWall);	//获得Button对象
        btnSetWall.setOnClickListener(new View.OnClickListener() {	//为Button添加OnClickListener监听器
			@Override
			public void onClick(View v) {		//重写onClick方法
				Resources r = Sample_12_2.this.getResources();		//获得Resources对象
				InputStream in = r.openRawResource(imgIds[selectedIndex]);//获得InputStream对象
				try {
					setWallpaper(in);		//设置墙纸
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    }
}