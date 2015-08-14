package wyf.wpf;						//声明包语句

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Sample_12_5 extends Activity {
	TelephonyManager tm;			//声明TelephonyManager对象的引用
	String [] phoneType = null;		//声明表示手机制式的数组
	String [] simState = null;		//声明表示SIM卡状态的数组
	String [] listItems = null;		//声明列表项的数组
	ArrayList<String> listValues = new ArrayList<String>();
	BaseAdapter ba = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(Sample_12_5.this);
			ll.setOrientation(LinearLayout.VERTICAL);		//设置现象布局的分布方式
			TextView tvItem = new TextView(Sample_12_5.this);
			TextView tvValue = new TextView(Sample_12_5.this);
			tvItem.setTextSize(24);						//设置字体大小
			tvItem.setText(listItems[position]);		//设置显示的内容
			tvItem.setGravity(Gravity.LEFT);			//设置在父容器中的对齐方式
			ll.addView(tvItem);
			tvValue.setTextSize(18);					//设置字体大小
			tvValue.setText(listValues.get(position));	//设置显示的内容
			tvValue.setPadding(0, 0, 10, 10);			//设置四周边界
			tvValue.setGravity(Gravity.RIGHT);			//设置在父容器中的对齐方式
			ll.addView(tvValue);						//将TextView添加到线性布局中
			return ll;
		}
		@Override
		public long getItemId(int position) {			//重写getItemId方法
			return 0;
		}
		@Override
		public Object getItem(int position) {			//重写getItem方法
			return null;
		}
		@Override
		public int getCount() {							//重写getCount方法
			return listItems.length;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        listItems = getResources().getStringArray(R.array.listItem);		//获得XML文件中的数组
        simState = getResources().getStringArray(R.array.simState);		//获得XML文件中的数组
        phoneType = getResources().getStringArray(R.array.phoneType);	//获得XML文件中的数组
        initListValues();			//初始化列表项的值
        ListView lv = (ListView)findViewById(R.id.lv);		//获得ListView对象
        lv.setAdapter(ba);
    }    
    public void initListValues(){			//方法：获取各个数据项的值
    	listValues.add(tm.getDeviceId());				//获取设备编号
    	listValues.add(tm.getSimCountryIso());			//获取SIM卡国别
    	listValues.add(tm.getSimSerialNumber());		//获取SIM卡序列号	
    	listValues.add(simState[tm.getSimState()]);		//获取SIM卡状态
    	listValues.add((tm.getDeviceSoftwareVersion()==null?tm.getDeviceSoftwareVersion():"未知"));	//获取软件版本
    	listValues.add(tm.getNetworkOperator());		//获取网络运营商代号
    	listValues.add(tm.getNetworkOperatorName());	//获取网络运营商名称
    	listValues.add(phoneType[tm.getPhoneType()]);	//获取手机 制式
    	listValues.add(tm.getCellLocation().toString());//获取设备当前位置
    }
}