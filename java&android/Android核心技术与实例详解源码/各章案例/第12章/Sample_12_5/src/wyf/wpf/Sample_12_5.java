package wyf.wpf;						//���������

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
	TelephonyManager tm;			//����TelephonyManager���������
	String [] phoneType = null;		//������ʾ�ֻ���ʽ������
	String [] simState = null;		//������ʾSIM��״̬������
	String [] listItems = null;		//�����б��������
	ArrayList<String> listValues = new ArrayList<String>();
	BaseAdapter ba = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(Sample_12_5.this);
			ll.setOrientation(LinearLayout.VERTICAL);		//�������󲼾ֵķֲ���ʽ
			TextView tvItem = new TextView(Sample_12_5.this);
			TextView tvValue = new TextView(Sample_12_5.this);
			tvItem.setTextSize(24);						//���������С
			tvItem.setText(listItems[position]);		//������ʾ������
			tvItem.setGravity(Gravity.LEFT);			//�����ڸ������еĶ��뷽ʽ
			ll.addView(tvItem);
			tvValue.setTextSize(18);					//���������С
			tvValue.setText(listValues.get(position));	//������ʾ������
			tvValue.setPadding(0, 0, 10, 10);			//�������ܱ߽�
			tvValue.setGravity(Gravity.RIGHT);			//�����ڸ������еĶ��뷽ʽ
			ll.addView(tvValue);						//��TextView��ӵ����Բ�����
			return ll;
		}
		@Override
		public long getItemId(int position) {			//��дgetItemId����
			return 0;
		}
		@Override
		public Object getItem(int position) {			//��дgetItem����
			return null;
		}
		@Override
		public int getCount() {							//��дgetCount����
			return listItems.length;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        listItems = getResources().getStringArray(R.array.listItem);		//���XML�ļ��е�����
        simState = getResources().getStringArray(R.array.simState);		//���XML�ļ��е�����
        phoneType = getResources().getStringArray(R.array.phoneType);	//���XML�ļ��е�����
        initListValues();			//��ʼ���б����ֵ
        ListView lv = (ListView)findViewById(R.id.lv);		//���ListView����
        lv.setAdapter(ba);
    }    
    public void initListValues(){			//��������ȡ�����������ֵ
    	listValues.add(tm.getDeviceId());				//��ȡ�豸���
    	listValues.add(tm.getSimCountryIso());			//��ȡSIM������
    	listValues.add(tm.getSimSerialNumber());		//��ȡSIM�����к�	
    	listValues.add(simState[tm.getSimState()]);		//��ȡSIM��״̬
    	listValues.add((tm.getDeviceSoftwareVersion()==null?tm.getDeviceSoftwareVersion():"δ֪"));	//��ȡ����汾
    	listValues.add(tm.getNetworkOperator());		//��ȡ������Ӫ�̴���
    	listValues.add(tm.getNetworkOperatorName());	//��ȡ������Ӫ������
    	listValues.add(phoneType[tm.getPhoneType()]);	//��ȡ�ֻ� ��ʽ
    	listValues.add(tm.getCellLocation().toString());//��ȡ�豸��ǰλ��
    }
}