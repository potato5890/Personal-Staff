package wyf.wpf;				//���������

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
	int [] imgIds ={			//ͼƬ��Դ��id����
			R.drawable.w1,
			R.drawable.w2,
			R.drawable.w3,
			R.drawable.w4
	};
	int selectedIndex = -1;		//��ѡ�е�ͼƬ��id�����е�����
	BaseAdapter ba = new BaseAdapter() {		//�Զ����BaseAdapter
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(Sample_12_2.this);		//�½�һ��ImageView
			iv.setBackgroundResource(imgIds[position]);			//����ImageView�ı���ͼƬ
			iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setLayoutParams(new Gallery.LayoutParams(120, 120));	//���������Ԫ�صĴ�С
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
        setContentView(R.layout.main);			//���õ�ǰ��Ļ
        Button btnClearWall = (Button)findViewById(R.id.clearWall);	//���Button����
        btnClearWall.setOnClickListener(new View.OnClickListener() {	//���OnClickListener������
			@Override
			public void onClick(View v) {		//��дonClick����
				try {
					Sample_12_2.this.clearWallpaper();	//��ԭ�ֻ���ֽ
				} catch (IOException e) {				//���񲢴�ӡ�쳣
					e.printStackTrace();
				}
			}
		});
        Button btnGetWall = (Button)findViewById(R.id.getWall);	//���Button����
        btnGetWall.setOnClickListener(new View.OnClickListener() {	//ΪButton���OnClickListener������
			@Override
			public void onClick(View v) {
				ImageView iv = (ImageView)findViewById(R.id.currWall);
				iv.setBackgroundDrawable(getWallpaper());		//����ImageView��ʾ������Ϊ��ǰǽֽ
			}
		});
        Gallery g = (Gallery)findViewById(R.id.gallery);			//���Gallery����
        g.setAdapter(ba);											 //����Gallery��BaseAdapter
        g.setSpacing(5);											//����ÿ��Ԫ��֮��ļ��
        g.setOnItemClickListener(new OnItemClickListener() {		//ΪGallery���OnItemClickListener������
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				selectedIndex = position;			//��¼��ѡ�е�ͼƬ����
			}
		});
        Button btnSetWall = (Button)findViewById(R.id.setWall);	//���Button����
        btnSetWall.setOnClickListener(new View.OnClickListener() {	//ΪButton���OnClickListener������
			@Override
			public void onClick(View v) {		//��дonClick����
				Resources r = Sample_12_2.this.getResources();		//���Resources����
				InputStream in = r.openRawResource(imgIds[selectedIndex]);//���InputStream����
				try {
					setWallpaper(in);		//����ǽֽ
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    }
}