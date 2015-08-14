package wyf.wpf;					//���������

import android.app.Activity;			//���������
import android.content.Context;			//���������
import android.location.Criteria;		//���������
import android.location.Location;		//���������
import android.location.LocationListener;	//���������
import android.location.LocationManager;	//���������
import android.os.Bundle;					//���������	
import android.widget.EditText;				//���������	

public class Sample_15_1 extends Activity {
	LocationManager lm;				//����LocationManager���������
	EditText et;					//����EditText���������
	LocationListener ll = new LocationListener(){
		@Override
		public void onLocationChanged(Location location) {//��дonLocationChanged����
			updateView(location);
		}
		@Override
		public void onProviderDisabled(String provider) {	//��дonProviderDisabled����
			updateView(null);
		}
		@Override
		public void onProviderEnabled(String provider) {	//��дonProviderEnabled����
	        Location l= lm.getLastKnownLocation(provider);		//��ȡλ����Ϣ
	        updateView(l);			//����EditText�ؼ�������
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {	//��дonStatusChanged����
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {		//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);				//���õ�ǰ��Ļ
        et = (EditText)findViewById(R.id.et);		//���EditText����
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);	//����LocationManager����
        String bestProvider = lm.getBestProvider(getCriteria(), true);		//���ò�ѯ����
        Location l= lm.getLastKnownLocation(bestProvider);		//��ȡλ����Ϣ
        updateView(l);			//����EditText�ؼ�������
        lm.requestLocationUpdates(bestProvider, 5000, 8, ll);	//���LocationListener������
    }
    //���������ز�ѯ����
    public Criteria getCriteria(){
    	Criteria c = new Criteria();
    	c.setAccuracy(Criteria.ACCURACY_COARSE);	//���ò�ѯ����
    	c.setSpeedRequired(false);					//�����Ƿ�Ҫ���ٶ�
    	c.setCostAllowed(false);					//�����Ƿ������������
    	c.setBearingRequired(false);				//�����Ƿ���Ҫ�õ�����
    	c.setAltitudeRequired(false);				//�����Ƿ���Ҫ�õ����θ߶�
    	c.setPowerRequirement(Criteria.POWER_LOW);	//��������ĵ�����ļ���
    	return c;									//���ز�ѯ����
    }
    //����������EditText����ʾ������
    public void updateView(Location newLocation){
    	if(newLocation !=null){		//�ж��Ƿ�Ϊ��
    		et.setText("�����ڵ�λ����\nγ�ȣ�");
    		et.append(String.valueOf(newLocation.getLatitude()));	//���γ��
    		et.append("\n���ȣ�");
    		et.append(String.valueOf(newLocation.getLongitude()));	//��þ���
    	}
    	else{		//��������Location����Ϊ�������EditText
    		et.getEditableText().clear();		//���EditText����
    	}
    }
}