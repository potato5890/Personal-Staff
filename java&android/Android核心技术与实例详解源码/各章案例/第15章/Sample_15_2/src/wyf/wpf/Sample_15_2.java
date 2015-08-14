package wyf.wpf;			//���������

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Sample_15_2 extends MapActivity {		//�̳���MapActivity������
	MapView mv;					//����MapView��������
	MapController controller;	//����MapController��������
	Bitmap bmpArrow;			//����Bitmap��������
	RadioButton rbNormal;			//����RadioButton��������
	RadioButton rbSatellite;		//����RadioButton��������
	@Override
	protected void onCreate(Bundle icicle) {		//��дonCreate����
		super.onCreate(icicle);
		setContentView(R.layout.main);
		bmpArrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
		mv = (MapView)findViewById(R.id.mv);			//���MapView����
		controller = mv.getController();				//���MapController����
		Button btnGo = (Button)findViewById(R.id.btnGo);	//���Button����
		mv.setBuiltInZoomControls(true);				//�����Ƿ���ʾ�Ŵ���С��ť
		
		
		btnGo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etLong = (EditText)findViewById(R.id.etLong);	//���EditText����
				EditText etLat = (EditText)findViewById(R.id.etLat);	//���EditText����
				String sLong = etLong.getEditableText().toString().trim();	//�������ľ���
				String sLat = etLat.getEditableText().toString().trim();	//��������γ��
				if(sLong.equals("") || sLat.equals("")){	//�ж��Ƿ������ֵ
					Toast.makeText(Sample_15_2.this, "�Բ���,��������ȷ�ľ�γ������!", Toast.LENGTH_LONG).show();
					return;
				}
				double dLong = Double.parseDouble(sLong);
				double dLat = Double.parseDouble(sLat);
				updateMapView(dLat, dLong);		//���÷�������MapView
			}
		});
		btnGo.performClick();
		RadioGroup rg = (RadioGroup)findViewById(R.id.rg);		//���RadioGroup����
		rbNormal = (RadioButton)findViewById(R.id.normal);	//���RadioButton����
		rbSatellite = (RadioButton)findViewById(R.id.satellite);	//���RadioButton����
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == rbNormal.getId()){		//�жϰ��µ��Ƿ���������ͼ
//					mv.setSatellite(false);
					mv.setTraffic(true);
				}
				else if(checkedId == rbSatellite.getId()){	//�жϰ��µ��Ƿ�Ϊ������ͼ
//					mv.setSatellite(true);
					mv.setStreetView(true);
				}
			}
		});
	}
	@Override
	protected boolean isRouteDisplayed() {		//��дisRouteDisplayed����
		return false;
	}
	//����:����MapView����ͼ
	public void updateMapView(double dLat,double dLong){
		GeoPoint gp = new GeoPoint((int)(dLat*1E6), (int)(dLong*1E6));
		mv.displayZoomControls(true);		//������ʾ�Ŵ���С��ť
		controller.animateTo(gp);			//����ͼ�ƶ���ָ���ĵ���λ��
		List<Overlay> ol = mv.getOverlays();	//���MapView��
		ol.clear();
		ol.add(new ArrowOverLay(gp,bmpArrow));		//���һ���µ�Overlay
	}
}