package wyf.ytl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;


public class MainActivity extends TabActivity implements OnClickListener,OnItemClickListener{
	static Bitmap bitmap;//气球图片
	
	byte[] image = null;
	private TabHost myTabhost;//声明TabHost的引用
	ImageButton searchButton = null;//搜索按钮
	ImageButton updateButton = null;//上传按钮
	ImageView msImage;
	EditText searchEditText;//搜索关键字
	ClientNetThread clientNetThread = null;//网络处理线程
	ListView lv;
	ArrayList mstxInfos = null;//美食信息
	ArrayList mstxImages = null;//美食图片
	List mstxSorts = null;//美食种类
	int uid ;//用户ID
	
	EditText infoPrice1 = null;
	EditText infoPrice2 = null;
	
	Spinner msSort = null;//美食搜索种类
	
	//上传界面各个控件
	EditText msName = null;//美食名称
	Spinner  msSort2 = null;//美食种类
	EditText msPrice = null;//美食价格
	EditText msDis = null;//美食描述
	EditText msLocation = null;//美食经纬度控件
	EditText mshotelName = null;//店铺名称

	ProgressDialog myDialog = null;//进度框

	double lat;
	double lon;
	
	String titleStr = "";//跑马灯字符串
	int place = 1;
	public Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//重写的方法用于接收Handler消息
			super.handleMessage(msg);
			if(msg.what == 1 || msg.what == 2 ||msg.what == 3){//得到美食搜索结果
				Bundle data = msg.getData();
				mstxInfos = (ArrayList)data.get("mstxInfo");
				mstxImages = (ArrayList)data.get("mstxImages");
		        //为ListView准备内容适配器
				MyBaseAdapter mba = new MyBaseAdapter(mstxInfos,mstxImages);
			    lv.setAdapter(mba);//为ListView设置内容适配器	
			    mba.notifyDataSetChanged();
			}
			else if(msg.what == 4){//跑马灯向外出来时
				MainActivity.this.setTitle(titleStr.substring(0, place));
			}
			else if(msg.what == 5){//跑马灯向里进去时
				MainActivity.this.setTitle(titleStr.substring(place, titleStr.length()));
			}
			else if(msg.what == 6){//清空美食上传界面
				msName.setText("");
				msDis.setText("");
				mshotelName.setText("");
				msLocation.setText("点击我获取经纬度");
				msPrice.setText("");
				image = null;
				msImage.setImageResource(R.drawable.click_to_photo);				
			}
			else if(msg.what == 7){//美食种类
				Bundle data = msg.getData();
				mstxSorts = (ArrayList)data.get("mstxSorts");
				BaseAdapter ba = new BaseAdapter(){						//为Spinner准备内容适配器
					@Override
					public int getCount() {
						return mstxSorts.size();
					}
					@Override
					public Object getItem(int arg0) {//重写的getItem方法				
						return null; 
					}
					@Override
					public long getItemId(int arg0) {//重写的getItemId方法
						return 0; 
					}
					@Override
					public View getView(int arg0, View arg1, ViewGroup arg2) {
						//初始化LinearLayout
						LinearLayout ll=new LinearLayout(MainActivity.this);
						ll.setOrientation(LinearLayout.HORIZONTAL);	//设置朝向	
						String[] str = (String[])mstxSorts.get(arg0);	
						//初始化TextView
						TextView tv=new TextView(MainActivity.this);						
						tv.setText(str[1]);//设置内容
						tv.setTextColor(R.color.textword2);
						tv.setTextSize(20);
						ll.addView(tv);	//添加到LinearLayout中						
						return ll;//将LinearLayout返回
					}
				};
				msSort2.setAdapter(ba);
		        ba.notifyDataSetChanged();
			}
			else if(msg.what == 8){//搜索界面的美食种类
				Bundle data = msg.getData();
				mstxSorts = (ArrayList)data.get("mstxSorts");
				BaseAdapter ba = new BaseAdapter(){						//为Spinner准备内容适配器
					@Override
					public int getCount() {
						return mstxSorts.size()+1;
					}
					@Override
					public Object getItem(int arg0) {//重写的getItem方法				
						return null; 
					}
					@Override
					public long getItemId(int arg0) {//重写的getItemId方法
						return 0; 
					}
					@Override
					public View getView(int arg0, View arg1, ViewGroup arg2) {
						String[] str;
						if(arg0 == 0){
							str = new String[]{"-1","所有种类"};
						}
						else {
							str = (String[])mstxSorts.get(arg0-1);
						}
						//初始化LinearLayout
						LinearLayout ll=new LinearLayout(MainActivity.this);
						ll.setOrientation(LinearLayout.HORIZONTAL);	//设置朝向	
							
						//初始化TextView
						TextView tv=new TextView(MainActivity.this);						
						tv.setText(str[1]);//设置内容
						tv.setTextColor(R.color.textword2);
						tv.setTextSize(20);
						ll.addView(tv);	//添加到LinearLayout中						
						return ll;//将LinearLayout返回
					}
				};
				msSort.setAdapter(ba);
		        ba.notifyDataSetChanged();				
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //初始化气球图片
        bitmap= BitmapFactory.decodeResource(this.getResources(), R.drawable.ballon);
        
		//全屏 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		clientNetThread = new ClientNetThread(this);//初始化网络处理线程
		clientNetThread.start();//启动线程
		
		Intent intent = this.getIntent();//创建一个Intent
		Bundle bundle = intent.getExtras();//得到存放数据的Bundle
		String u_name = bundle.getString("u_name");//得到Bundle中的u_name数据
		uid = Integer.parseInt(bundle.getString("uid"));//得到用户编号
		titleStr = u_name+", 美食网欢迎您！您的ID号为"+uid;
		new Thread(){//该线程用于标题栏跑马灯的实现
			public void run(){
				boolean control = true;
				while(true){
					if(control){//出来时
						myHandler.sendEmptyMessage(4);
						try{
							Thread.sleep(300);//睡觉300毫秒
						}
						catch(Exception e){//捕获异常 
							e.printStackTrace();//打印异常
						}
						if(place >= titleStr.length()){
							place = 1;
							control = false;
						}
						else {
							place++;
						}
					}
					else{//进去
						myHandler.sendEmptyMessage(5);//发送Handler消息
						try{
							Thread.sleep(200);//睡觉300毫秒
						}
						catch(Exception e){//捕获异常 
							e.printStackTrace();//打印异常
						}
						if(place >= titleStr.length()){
							place = 1;
							control = true;
						}
						else {
							place++;//将place加一
						}
					}
				}
			}
		}.start();
		myTabhost=this.getTabHost();//从TabActivity上面获取放置Tab的TabHost
		LayoutInflater.from(this).inflate(R.layout.index, myTabhost.getTabContentView(), true);
		
		myTabhost.addTab(//添加一个选项
			myTabhost.newTabSpec("收藏")	//创建一个TabSpec
				.setIndicator("收藏", getResources().getDrawable(R.drawable.png1))
				.setContent(R.id.favourite)//设置此选项的布局文件
		);
		myTabhost.addTab(//添加一个选项
			myTabhost.newTabSpec("搜索")	//创建一个TabSpec
				.setIndicator("搜索", getResources().getDrawable(R.drawable.png2))
				.setContent(R.id.search)//设置此选项的布局文件
		);
		myTabhost.addTab(//添加一个选项
			myTabhost.newTabSpec("推荐")	//创建一个TabSpec
				.setIndicator("推荐", getResources().getDrawable(R.drawable.png3))
				.setContent(R.id.recommend)//设置此选项的布局文件
		); 
		myTabhost.addTab(//添加一个选项 
			myTabhost.newTabSpec("上传")	//创建一个TabSpec
				.setIndicator("上传", getResources().getDrawable(R.drawable.png4))
				.setContent(R.id.update)//设置此选项的布局文件
		);
		
		myTabhost.setOnTabChangedListener(new OnTabChangeListener(){//为Tab添加监听
			@Override
			public void onTabChanged(String tabId) {//事件处理方法
				if(tabId.equals("收藏")){//收藏界面
					System.out.println("favourite");
			        //初始化ListView
			        lv=(ListView)findViewById(R.id.searchlistView01);
			        lv.setOnItemClickListener(MainActivity.this);
					try {
						clientNetThread.dout.writeUTF("<#FAVOURITE#>"+uid);//向服务器发送消息
					} catch (IOException e) {//捕获异常
						e.printStackTrace();//打印异常
					}
				}
				else if(tabId.equals("搜索")){//搜索界面
					msSort = (Spinner)findViewById(R.id.mySort);//得到美食种类控件
					new Thread(){
						public void run(){
							try {
								clientNetThread.dout.writeUTF("<#MSTXSORT#>1");//向服务器发送消息
							} catch (IOException e) {//捕获异常
								e.printStackTrace();//打印异常
							}							
						}
					}.start();
					searchButton = (ImageButton)findViewById(R.id.searchButton);//得到搜索按钮的引用
					searchEditText = (EditText)findViewById(R.id.infoValues);//得到搜索关键字文本框引用
					searchButton.setOnClickListener(MainActivity.this);//为按钮添加监听
					infoPrice1 = (EditText)findViewById(R.id.infoPrice1);
					infoPrice2 = (EditText)findViewById(R.id.infoPrice2);
				}
				else if(tabId.equals("推荐")){//推荐界面
					lv=(ListView)findViewById(R.id.searchlistView03);
					lv.setOnItemClickListener(MainActivity.this);
					new Thread(){
						public void run(){
							try {
								clientNetThread.dout.writeUTF("<#RECOMMEND#>"+uid);//向服务器发送消息
							} catch (IOException e) {//捕获异常
								e.printStackTrace();//打印异常
							}							
						}
					}.start();
				}
				else if(tabId.equals("上传")){//上传界面

					updateButton = (ImageButton)findViewById(R.id.updateButton);//得到按钮的引用
					updateButton.setOnClickListener(MainActivity.this);//添加按钮监听

					msName = (EditText)findViewById(R.id.msName);//得到美食名称控件
					msSort2 = (Spinner)findViewById(R.id.mySort2);//得到美食种类控件
					msPrice = (EditText)findViewById(R.id.msPrice);//得到美食价格控件
					msDis = (EditText)findViewById(R.id.msDis);//得到美食描述控件
					msLocation = (EditText)findViewById(R.id.msLocation);//得到美食经度控件
					mshotelName = (EditText)findViewById(R.id.mshotelName);
					
					
					msLocation.setOnClickListener(MainActivity.this);
					msImage = (ImageView)findViewById(R.id.msImage);//得到图片
					msImage.setClickable(true);//设置其可以点击
					msImage.setOnClickListener(MainActivity.this);//添加监听
					new Thread(){
						public void run(){
							try {
								clientNetThread.dout.writeUTF("<#MSTXSORT#>2");//向服务器发送消息
							} catch (IOException e) {//捕获异常
								e.printStackTrace();//打印异常
							}							
						}
					}.start();
				}
			}
		});
		
		myTabhost.setCurrentTab(1);//设置当前显示的Tab
    }
	@Override
	public void onClick(View view) {//按钮监听方法
		if(view == searchButton){//搜索按钮被按下时
			String infoValues = searchEditText.getText().toString();
			if(infoValues.trim().equals("")){//搜索关键字为空时
				Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
				return;
			}
			int searchSort;
			int temp = msSort.getSelectedItemPosition();
			if(temp == 0){
				searchSort = -1;
			}
			else {
				searchSort = Integer.parseInt(((String[])mstxSorts.get(temp-1))[0]);
			}
			
			String startPrice = infoPrice1.getText().toString().trim();
			String endPrice = infoPrice2.getText().toString().trim();
		
			if(!startPrice.equals("") && !endPrice.equals("")){
				if(Integer.parseInt(startPrice) > Integer.parseInt(endPrice)){
					Toast.makeText(this, "请输入正确的价格区间", Toast.LENGTH_SHORT).show();
					return;
				}				
			}
			
			
			Intent intent = new Intent();
			Bundle data = new Bundle();
			data.putString("infoValues", infoValues);
			data.putInt("searchSort", searchSort);
			data.putString("startPrice", startPrice);
			data.putString("endPrice", endPrice);
			data.putInt("uid", uid);
			intent.putExtras(data);
			intent.setClass(MainActivity.this, SearchActivity.class);
			startActivity(intent);
		}
		else if(view == msImage){//点击图片
			myDialog = ProgressDialog.show(this, "进度", "正在加载...",true);
			new Thread(){//创建线程
				public void run(){
					Intent intent = new Intent();//创建Intent
					intent.setClass(MainActivity.this, PhotoActivity.class);
					startActivityForResult(intent, 0);	
					
					myDialog.dismiss();
				}
			}.start();			
		}
		else if(view == msLocation){//按下获取经纬度的文本框
			myDialog = ProgressDialog.show(this, "进度", "正在加载...",true);
			new Thread(){
				public void run(){
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MyMapActivity.class);
					startActivityForResult(intent, 1);		

					myDialog.dismiss();
				}
			}.start();			
		}
		else if(view == updateButton){//按下上传按钮
			String info_title = msName.getText().toString();
			String info_dis = msDis.getText().toString();
			String info_loc = msLocation.getText().toString();
			String info_price = msPrice.getText().toString();
			String info_hotel = mshotelName.getText().toString();
			
			
			if(info_title.trim().equals("")){//名称为空时
				Toast.makeText(MainActivity.this, "请输入美食名称", Toast.LENGTH_LONG).show();
				return;					
			}
			else if(info_dis.trim().equals("")){//描述为空时
				Toast.makeText(MainActivity.this, "请输入美食描述", Toast.LENGTH_LONG).show();
				return;	
			}
			else if(info_price.trim().equals("")){//价格为空时
				Toast.makeText(MainActivity.this, "请输入美食价格", Toast.LENGTH_LONG).show();
				return;	
			}	
			else if(info_hotel.trim().equals("")){//店铺位置为空时
				Toast.makeText(MainActivity.this, "请输入饭店位置", Toast.LENGTH_LONG).show();
				return;			
			}
			else if(info_loc.trim().equals("")){//经度为空时
				Toast.makeText(MainActivity.this, "请通过位置按钮获得店铺所在的位置", Toast.LENGTH_LONG).show();
				return;	
			}
			else if(image == null){//美食图片不存在时
				Toast.makeText(MainActivity.this, "请通过拍照按钮拍摄美食图片", Toast.LENGTH_LONG).show();
				return;
			}
			new Thread(){
				public void run(){
					Looper.prepare();//一定要加
					String[] tempStr = (String[])mstxSorts.get(msSort2.getSelectedItemPosition());
					try {
						myDialog = ProgressDialog.show(MainActivity.this, "进度", "正在加载...",true);
				
						clientNetThread.dout.writeUTF("<#INSERTMSTXINFO#>"+
								msName.getText().toString()+"|"+msDis.getText().toString()+"|"
								+lon+"|"+lat+"|"+uid+"|"+tempStr[0]+"|"+
								msPrice.getText().toString()+"|"+mshotelName.getText().toString());

						int size = image.length;//图片数组的长度
						clientNetThread.dout.writeInt(size);//向服务器发送数组的长度
						clientNetThread.dout.write(image);//向服务器发送图片字节数组
						clientNetThread.dout.flush();//清空缓冲区,保证之前的数据发送出去

						myHandler.sendEmptyMessage(6);
					}
					catch (IOException e) {//捕获异常
						e.printStackTrace();//打印异常信息
					}
					finally{
						if(myDialog != null){//当myDialog不为空时
							myDialog.dismiss();
						}
					}
				}
			}.start();
		}
	}
	@Override
	protected void onDestroy() {//Activity被摧毁时被调用
		super.onDestroy();
		try {
			clientNetThread.dout.writeUTF("<#ClientDown#>");//通知服务器客户端下线
			clientNetThread.flag = false;
		} catch (IOException e) {//捕获异常
			e.printStackTrace();//打印异常信息
		}
	}
	public class MyBaseAdapter extends BaseAdapter{
		ArrayList mstxInfos = null;
		ArrayList mstxImages = null;
        public MyBaseAdapter(ArrayList mstxInfos, ArrayList mstxImages) {//构造器
            super();   
            this.mstxInfos = mstxInfos;   
            this.mstxImages = mstxImages;   
        } 
		@Override
		public int getCount() {//总共多少个选项
			return mstxInfos.size();
		}
		@Override
		public Object getItem(int arg0) {return null;}
		@Override
		public long getItemId(int arg0) {return 0;}
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {//动态生成每个下拉项对应的View
			//初始化LinearLayout
			LinearLayout linearLayout1 = new LinearLayout(MainActivity.this);
			linearLayout1.setOrientation(LinearLayout.HORIZONTAL);//设置朝向	
			linearLayout1.setPadding(5,5,5,5);//设置四周留白

			//初始化ImageView
			ImageView  ii=new ImageView(MainActivity.this);
			byte[] bs = (byte[])mstxImages.get(arg0);
			Bitmap b = BitmapFactory.decodeByteArray(bs,0,bs.length);
			ii.setImageBitmap(b);

			ii.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片显示方式
			ii.setLayoutParams(new Gallery.LayoutParams(130,90));
			linearLayout1.addView(ii);//添加到LinearLayout中

			LinearLayout linearLayout2 = new LinearLayout(MainActivity.this);
			linearLayout2.setOrientation(LinearLayout.VERTICAL);//设置朝向	
			linearLayout2.setPadding(5,1,5,1);//设置四周留白
			linearLayout2.setGravity(Gravity.TOP);//设置对其方式
			
			//初始化TextView,美食名称
			TextView title=new TextView(MainActivity.this);
			String[] mstxStrs = (String[]) mstxInfos.get(arg0);
			title.setText(mstxStrs[0]);//设置文字
			title.setTextSize(18);//设置字体大小
			title.setTextColor(Color.RED);
			title.setPadding(5,1,5,1);//设置四周留白
			title.setMaxLines(1);//设置行数
			title.setGravity(Gravity.LEFT);//对齐方式
		    linearLayout2.addView(title);//添加到LinearLayout中
		    
			//初始化TextView
			TextView date = new TextView(MainActivity.this);
			date.setText(mstxStrs[4]);
			date.setTextSize(10);//设置字体大小
			date.setTextColor(Color.GRAY);//文字的颜色
			date.setPadding(5,1,5,1);//设置四周留白
			title.setMaxLines(1);//设置行数
			date.setGravity(Gravity.LEFT);//对齐方式
		    linearLayout2.addView(date);//添加到LinearLayout中
		    
			//初始化TextView
			TextView info_dis = new TextView(MainActivity.this);
			info_dis.setText(mstxStrs[1]);
			info_dis.setTextSize(13);//设置字体大小
			info_dis.setTextColor(Color.BLACK);//文字的颜色
			info_dis.setPadding(5,1,5,1);//设置四周留白
			info_dis.setGravity(Gravity.LEFT);//对齐方式
			info_dis.setMaxLines(3);//最大行数
		    linearLayout2.addView(info_dis);//添加到LinearLayout中
		    linearLayout1.addView(linearLayout2);
			return linearLayout1;
		}	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 0){//拍照的结果
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();//得到数据
				image = bundle.getByteArray("image");//得到字节数组
				Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
				msImage.setImageBitmap(bm);//设置显示图片
			}
		}
		else if(requestCode == 1){//获取经纬度的结果
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();//得到数据
				lat = bundle.getDouble("lat");//得到经纬度
				lon = bundle.getDouble("lon");
				msLocation.setText("经度为："+lon+"\n纬度为："+lat);//设置经纬度到文本框中
			}		
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {//ListView监听方法
		String[] mstxStrs = (String[]) mstxInfos.get(arg2);
		int action = myTabhost.getCurrentTab();//当前是哪个Tab
		Intent intent = new Intent();//创建Intent
		intent.setClass(MainActivity.this, InfoActivity.class);
		Bundle bundle = new Bundle();//创建数据Bundle
		bundle.putInt("action", action);
		bundle.putInt("uid", uid);
		bundle.putStringArray("mstxInfo", mstxStrs);
		intent.putExtras(bundle);//存放数据
		startActivity(intent);//启动Activity
	}
}