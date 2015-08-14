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
	MapController myMapController = null;//声明myMapController的引用
	MapView myMapView = null;//声明MapView的引用
	double info_lon;//经度
	double info_lat;//纬度
	String[] mstxInfo;
	ImageButton deleteButton = null;//删除按钮
	ImageButton favouriteButton = null;
	ImageButton backButton = null;//返回按钮
	
	int uid;
	public void onCreate(Bundle savedInstanceState) {//Activity创建时被调用
        super.onCreate(savedInstanceState);
		//全屏 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = this.getIntent();//得到Intent
		Bundle bundle = intent.getExtras();//得到存放数据的Bundle
		int action = bundle.getInt("action");//得带action数据
		uid = bundle.getInt("uid");
		mstxInfo = bundle.getStringArray("mstxInfo");
		if(action == 0){//我的收藏中某个美食的详细信息
			this.setContentView(R.layout.favourite_info);
			TextView tv = (TextView)findViewById(R.id.favouriteTitle);
			TextView msDis = (TextView)findViewById(R.id.msDis);
			TextView msName = (TextView)findViewById(R.id.msName);
			TextView msTime = (TextView)findViewById(R.id.msTime);
			deleteButton = (ImageButton)findViewById(R.id.deleteButton);//得到删除按钮的引用
			deleteButton.setOnClickListener(this);//添加监听
			backButton = (ImageButton)findViewById(R.id.backButton);//返回按钮
			backButton.setOnClickListener(this);//添加监听
			
			tv.setText(mstxInfo[0]);//设置标题
			msName.setText(mstxInfo[0]);//美食名称
			msTime.setText(mstxInfo[4]);//上传时间
			msDis.setText(mstxInfo[1]);//美食描述
			
			info_lon = Double.parseDouble(mstxInfo[2]);//得到经度
			info_lat = Double.parseDouble(mstxInfo[3]);//得到纬度
			myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
			myMapController = myMapView.getController();//获得MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//设置经纬度
			myMapController.setZoom(15);//设置缩放比例
			myMapView.setBuiltInZoomControls(true);
			
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"店铺名称："+mstxInfo[0]);
			List<Overlay> overlays = myMapView.getOverlays();
			overlays.clear();
			overlays.add(myOverlay);

			//设置显示模式
//			myMapView.setSatellite(false);//卫星模式
//			myMapView.setTraffic(false);//路况模式
//			myMapView.setStreetView(false);//街景模式
		}
		else if(action == 2){//推荐中某个美食的详细信息 
			this.setContentView(R.layout.search_info);
			TextView tv = (TextView)findViewById(R.id.favouriteTitle);
			TextView msDis = (TextView)findViewById(R.id.msDis);
			TextView msName = (TextView)findViewById(R.id.msName);
			TextView msTime = (TextView)findViewById(R.id.msTime);
			favouriteButton = (ImageButton)findViewById(R.id.favouriteButton);//得到删除按钮的引用
			favouriteButton.setOnClickListener(this);//添加监听
			tv.setText(mstxInfo[0]);//设置标题
			msName.setText(mstxInfo[0]);//美食名称
			msTime.setText(mstxInfo[4]);//上传时间
			msDis.setText(mstxInfo[1]);//美食描述
			
			info_lon = Double.parseDouble(mstxInfo[2]);//得到经度
			info_lat = Double.parseDouble(mstxInfo[3]);//得到纬度
			myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
			myMapController = myMapView.getController();//获得MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//设置经纬度
			myMapController.setZoom(18);//设置放大等级
			myMapView.setBuiltInZoomControls(true);
			backButton = (ImageButton)findViewById(R.id.backButton);//返回按钮
			backButton.setOnClickListener(this);//添加监听
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"美食名称："+mstxInfo[0]);
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
			favouriteButton = (ImageButton)findViewById(R.id.favouriteButton);//得到删除按钮的引用
			favouriteButton.setOnClickListener(this);//添加监听
			tv.setText(mstxInfo[1]);//设置标题
			msName.setText(mstxInfo[1]);//美食名称
			msTime.setText(mstxInfo[5]);//上传时间
			msDis.setText(mstxInfo[2]);//美食描述
			
			info_lon = Double.parseDouble(mstxInfo[3]);//得到经度
			info_lat = Double.parseDouble(mstxInfo[4]);//得到纬度
			myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
			myMapController = myMapView.getController();//获得MapController
			GeoPoint gp = new GeoPoint((int)(info_lat*1E6), (int)(info_lon*1E6));
			myMapController.animateTo(gp);//设置经纬度
			myMapController.setZoom(18);//设置放大等级
			myMapView.setBuiltInZoomControls(true);
			backButton = (ImageButton)findViewById(R.id.backButton);//返回按钮
			backButton.setOnClickListener(this);//添加监听
			
			MyBallonOverlay myOverlay = new MyBallonOverlay(gp,"店铺名称："+mstxInfo[0]);
			List<Overlay> overlays = myMapView.getOverlays();
			overlays.clear();
			overlays.add(myOverlay);			
		}
	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	protected Dialog onCreateDialog(int id) {//重写onCreateDialog方法
		Dialog dialog = null;//声明一个Dialog对象用于返回
		switch(id){//对id进行判断
		case 1:
			Builder b = new AlertDialog.Builder(this);
			b.setIcon(R.drawable.logo);//设置对话框的图标
			b.setTitle("提示");//设置对话框的标题
			b.setMessage("您确定要删除该美食？");	//设置对话框的显示内容
			b.setPositiveButton(//添加按钮
				"确定", 
				new DialogInterface.OnClickListener() {//为按钮添加监听器
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){//创建线程,将耗时的工作放到单独的线程中
							public void run(){//重写run方法
								Socket s = null;
								DataOutputStream dout = null;
								try {
									s = new Socket("192.168.9.100", 9999);//连接服务器
									dout = new DataOutputStream(s.getOutputStream());
									dout.writeUTF("<#DELETEMSTXCOL#>"+mstxInfo[6]+"|"+mstxInfo[5]);
									dout.writeUTF("<#ClientDown#>");
								} catch (Exception e) {//捕获异常
									e.printStackTrace();//打印异常
								} finally{//使用finally保证之后的语句一定被执行
									try{
										if(dout != null){
											dout.close();//关闭输出流
											dout = null;
										}
									}
									catch(Exception e){//捕获异常
										e.printStackTrace();//打印异常
									}
									try{
										if(s != null){
											s.close();//关闭Socket
											s = null;
										}							
									}
									catch(Exception e){//捕获异常
										e.printStackTrace();//打印异常
									}						
								}
							}
						}.start();	
						InfoActivity.this.finish();//释放当前的Activity
					}
			});
			b.setNeutralButton("取消", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){}
				}
			);
			dialog = b.create();								//生成Dialog对象
			break;
		case 2:
			Builder b2 = new AlertDialog.Builder(this);
			b2.setIcon(R.drawable.logo);//设置对话框的图标
			b2.setTitle("提示");//设置对话框的标题
			b2.setMessage("您确定要收藏该美食？");//设置对话框的显示内容
			b2.setPositiveButton(//添加按钮
				"确定", 
				new DialogInterface.OnClickListener() {//为按钮添加监听器
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new Thread(){//将耗时的网络动作放到线程中
							public void run(){//重写的run方法
								Socket s = null;//声明Socket的引用
								DataOutputStream dout = null;//声明输出流的引用
								try {
									s = new Socket("192.168.9.100", 9999);//连接服务器
									dout = new DataOutputStream(s.getOutputStream());//得到输出流
									dout.writeUTF("<#INSERTMSTXCOL#>"+mstxInfo[6]+"|"+uid);
									dout.writeUTF("<#ClientDown#>");
								} catch (Exception e) {//捕获异常
									e.printStackTrace();//打印异常
								} finally{//使用finally保证之后的语句一定被执行
									try{
										if(dout != null){
											dout.close();//关闭输出流
											dout = null;
										}							
									}
									catch(Exception e){//捕获异常
										e.printStackTrace();//打印异常 
									}
									try{
										if(s != null){
											s.close();//关闭Socket连接
											s = null;
										}							
									}
									catch(Exception e){//捕获异常
										e.printStackTrace();//打印异常
									}
								}  
							}
						}.start();
						InfoActivity.this.finish();//释放当前的Activity	
					}
			});
			b2.setNeutralButton("取消", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){}
				}
			);
			dialog = b2.create();//生成Dialog对象			
			break;
		default:
			break;
		}
		return dialog;//返回生成Dialog的对象
	}
	@Override
	public void onClick(View v) {//按钮监听方法
		if(v == deleteButton){//点击的是删除按钮
			showDialog(1);			//显示普通对话框
		}
		else if(v == favouriteButton){//点击的是收藏按钮
			showDialog(2);			//显示普通对话框
			
		}
		else if(v == backButton){//收藏界面中的返回按钮
			this.finish();//释放当前的Activity	
		}
	}
}
