package wyf.ytl;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class InfoActivity extends MapActivity implements OnClickListener{
	MapController myMapController = null;//����myMapController������
	MapView myMapView = null;//����MapView������
	double info_lon;//����
	double info_lat;//γ��
	String[] mstxInfo;
	ImageButton deleteButton = null;//ɾ����ť
	ImageButton favouriteButton = null;
	ImageButton backButton = null;//���ذ�ť
	
	int uid;
	public void onCreate(Bundle savedInstanceState) {//Activity����ʱ������
        super.onCreate(savedInstanceState);
		//ȫ�� 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = this.getIntent();//�õ�Intent
		Bundle bundle = intent.getExtras();//�õ�������ݵ�Bundle
		int action = bundle.getInt("action");//�ô�action����
		uid = bundle.getInt("uid");
		mstxInfo = bundle.getStringArray("mstxInfo");
		if(action == 0){//�ҵ��ղ���ĳ����ʳ����ϸ��Ϣ
			this.setContentView(R.layout.favourite_info);
			TextView tv = (TextView)findViewById(R.id.favouriteTitle);
			TextView msDis = (TextView)findViewById(R.id.msDis);
			TextView msName = (TextView)findViewById(R.id.msName);
			TextView msTime = (TextView)findViewById(R.id.msTime);
			deleteButton = (ImageButton)findViewById(R.id.deleteButton);//�õ�ɾ����ť������
			deleteButton.setOnClickListener(this);//��Ӽ���
			backButton = (ImageButton)findViewById(R.id.backButton);//���ذ�ť
			backButton.setOnClickListener(this);//��Ӽ���
			
			tv.setText(mstxInfo[0]);//���ñ���
			msName.setText(mstxInfo[0]);//��ʳ����
			msTime.setText(mstxInfo[4]);//�ϴ�ʱ��
			msDis.setText(mstxInfo[1]);//��ʳ����
			
			info_lon = Double.parseDouble(mstxInfo[2]);//�õ�����
			info_lat = Double.parseDouble(mstxInfo[3]);//�õ�γ��
			myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�myMapView������
			myMapController = myMapView.getController();//���MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//���þ�γ��
			myMapController.setZoom(15);//�������ű���
			myMapView.setBuiltInZoomControls(true);
			
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"�������ƣ�"+mstxInfo[0]);
			List<Overlay> overlays = myMapView.getOverlays();
			overlays.clear();
			overlays.add(myOverlay);

			//������ʾģʽ
//			myMapView.setSatellite(false);//����ģʽ
//			myMapView.setTraffic(false);//·��ģʽ
//			myMapView.setStreetView(false);//�־�ģʽ
		}
		else if(action == 2){//�Ƽ���ĳ����ʳ����ϸ��Ϣ 
			this.setContentView(R.layout.search_info);
			TextView tv = (TextView)findViewById(R.id.favouriteTitle);
			TextView msDis = (TextView)findViewById(R.id.msDis);
			TextView msName = (TextView)findViewById(R.id.msName);
			TextView msTime = (TextView)findViewById(R.id.msTime);
			favouriteButton = (ImageButton)findViewById(R.id.favouriteButton);//�õ�ɾ����ť������
			favouriteButton.setOnClickListener(this);//��Ӽ���
			tv.setText(mstxInfo[0]);//���ñ���
			msName.setText(mstxInfo[0]);//��ʳ����
			msTime.setText(mstxInfo[4]);//�ϴ�ʱ��
			msDis.setText(mstxInfo[1]);//��ʳ����
			
			info_lon = Double.parseDouble(mstxInfo[2]);//�õ�����
			info_lat = Double.parseDouble(mstxInfo[3]);//�õ�γ��
			myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�myMapView������
			myMapController = myMapView.getController();//���MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//���þ�γ��
			myMapController.setZoom(18);//���÷Ŵ�ȼ�
			myMapView.setBuiltInZoomControls(true);
			backButton = (ImageButton)findViewById(R.id.backButton);//���ذ�ť
			backButton.setOnClickListener(this);//��Ӽ���
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"��ʳ���ƣ�"+mstxInfo[0]);
			List<Overlay> overlays = myMapView.getOverlays();
			overlays.clear();
			overlays.add(myOverlay);		
		}
		else if(action == 3){
			this.setContentView(R.layout.search_info);	
			TextView tv = (TextView)findViewById(R.id.favouriteTitle);
			TextView msDis = (TextView)findViewById(R.id.msDis);
			TextView msName = (TextView)findViewById(R.id.msName);
			TextView msTime = (TextView)findViewById(R.id.msTime);
			favouriteButton = (ImageButton)findViewById(R.id.favouriteButton);//�õ�ɾ����ť������
			favouriteButton.setOnClickListener(this);//��Ӽ���
			tv.setText(mstxInfo[1]);//���ñ���
			msName.setText(mstxInfo[1]);//��ʳ����
			msTime.setText(mstxInfo[5]);//�ϴ�ʱ��
			msDis.setText(mstxInfo[2]);//��ʳ����
			
			info_lon = Double.parseDouble(mstxInfo[3]);//�õ�����
			info_lat = Double.parseDouble(mstxInfo[4]);//�õ�γ��
			myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�myMapView������
			myMapController = myMapView.getController();//���MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//���þ�γ��
			myMapController.setZoom(18);//���÷Ŵ�ȼ�
			myMapView.setBuiltInZoomControls(true);
			backButton = (ImageButton)findViewById(R.id.backButton);//���ذ�ť
			backButton.setOnClickListener(this);//��Ӽ���
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"�������ƣ�"+mstxInfo[0]);
			List<Overlay> overlays = myMapView.getOverlays();
			overlays.clear();
			overlays.add(myOverlay);			
		}
	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	protected Dialog onCreateDialog(int id) {//��дonCreateDialog����
		Dialog dialog = null;//����һ��Dialog�������ڷ���
		switch(id){//��id�����ж�
		case 1:
			Builder b = new AlertDialog.Builder(this);
			b.setIcon(R.drawable.logo);//���öԻ����ͼ��
			b.setTitle("��ʾ");//���öԻ���ı���
			b.setMessage("��ȷ��Ҫɾ������ʳ��");	//���öԻ������ʾ����
			b.setPositiveButton(//��Ӱ�ť
				"ȷ��", 
				new DialogInterface.OnClickListener() {//Ϊ��ť��Ӽ�����
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){//�����߳�,����ʱ�Ĺ����ŵ��������߳���
							public void run(){//��дrun����
								Socket s = null;
								DataOutputStream dout = null;
								try {
									s = new Socket("192.168.9.100", 9999);//���ӷ�����
									dout = new DataOutputStream(s.getOutputStream());
									dout.writeUTF("<#DELETEMSTXCOL#>"+mstxInfo[6]+"|"+mstxInfo[5]);
									dout.writeUTF("<#ClientDown#>");
								} catch (Exception e) {//�����쳣
									e.printStackTrace();//��ӡ�쳣
								} finally{//ʹ��finally��֤֮������һ����ִ��
									try{
										if(dout != null){
											dout.close();//�ر������
											dout = null;
										}
									}
									catch(Exception e){//�����쳣
										e.printStackTrace();//��ӡ�쳣
									}
									try{
										if(s != null){
											s.close();//�ر�Socket
											s = null;
										}							
									}
									catch(Exception e){//�����쳣
										e.printStackTrace();//��ӡ�쳣
									}						
								}
							}
						}.start();	
						InfoActivity.this.finish();//�ͷŵ�ǰ��Activity
					}
			});
			b.setNeutralButton("ȡ��", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){}
				}
			);
			dialog = b.create();								//����Dialog����
			break;
		case 2:
			Builder b2 = new AlertDialog.Builder(this);
			b2.setIcon(R.drawable.logo);//���öԻ����ͼ��
			b2.setTitle("��ʾ");//���öԻ���ı���
			b2.setMessage("��ȷ��Ҫ�ղظ���ʳ��");//���öԻ������ʾ����
			b2.setPositiveButton(//��Ӱ�ť
				"ȷ��", 
				new DialogInterface.OnClickListener() {//Ϊ��ť��Ӽ�����
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){//����ʱ�����綯���ŵ��߳���
							public void run(){//��д��run����
								Socket s = null;//����Socket������
								DataOutputStream dout = null;//���������������
								try {
									s = new Socket("192.168.9.100", 9999);//���ӷ�����
									dout = new DataOutputStream(s.getOutputStream());//�õ������
									dout.writeUTF("<#INSERTMSTXCOL#>"+mstxInfo[6]+"|"+uid);
									dout.writeUTF("<#ClientDown#>");
								} catch (Exception e) {//�����쳣
									e.printStackTrace();//��ӡ�쳣
								} finally{//ʹ��finally��֤֮������һ����ִ��
									try{
										if(dout != null){
											dout.close();//�ر������
											dout = null;
										}							
									}
									catch(Exception e){//�����쳣
										e.printStackTrace();//��ӡ�쳣 
									}
									try{
										if(s != null){
											s.close();//�ر�Socket����
											s = null;
										}							
									}
									catch(Exception e){//�����쳣
										e.printStackTrace();//��ӡ�쳣
									}
								}  
							}
						}.start();
						InfoActivity.this.finish();//�ͷŵ�ǰ��Activity	
					}
			});
			b2.setNeutralButton("ȡ��", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){}
				}
			);
			dialog = b2.create();//����Dialog����			
			break;
		default:
			break;
		}
		return dialog;//��������Dialog�Ķ���
	}
	@Override
	public void onClick(View v) {//��ť��������
		if(v == deleteButton){//�������ɾ����ť
			showDialog(1);			//��ʾ��ͨ�Ի���
		}
		else if(v == favouriteButton){//��������ղذ�ť
			showDialog(2);			//��ʾ��ͨ�Ի���
			
		}
		else if(v == backButton){//�ղؽ����еķ��ذ�ť
			this.finish();//�ͷŵ�ǰ��Activity	
		}
	}
}
