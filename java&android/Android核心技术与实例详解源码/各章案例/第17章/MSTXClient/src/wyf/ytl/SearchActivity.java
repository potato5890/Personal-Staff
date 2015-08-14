package wyf.ytl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
public class SearchActivity extends Activity  implements OnItemClickListener, OnClickListener{
	int span = 5;//每页多少条
	int currentPageNo = 1;
	int totleNumber = 0;
	
	String infoValues;
	int searchSort;
	String startPrice;
	String endPrice;
	int uid;
	
	Socket s = null;
	DataOutputStream dout;//输出流
	DataInputStream din;//输入流
	ArrayList mstxInfos = null;//美食信息
	ArrayList mstxImages = null;//美食图片
	ListView lv;
	
	ImageButton previousButton;//上一页按钮
	ImageButton next_button;//下一页按钮
	ImageButton back_button;//返回按钮
	
	public Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//重写的方法用于接收Handler消息
			if(msg.what == 1){
				Bundle data = msg.getData();
				mstxInfos = (ArrayList)data.get("mstxInfo");
				mstxImages = (ArrayList)data.get("mstxImages");
		        //为ListView准备内容适配器
				MyBaseAdapter mba = new MyBaseAdapter(mstxInfos,mstxImages);
			    lv.setAdapter(mba);//为ListView设置内容适配器	
			    mba.notifyDataSetChanged();
			}
		}
	};

	TextView searchResultTextView;
    public void onCreate(Bundle savedInstanceState) {//Activity创建时被调用
        super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Intent intent = this.getIntent();//得到Intent
		Bundle bundle = intent.getExtras();//得到Intent中的bundle
		
		infoValues = bundle.getString("infoValues");
		searchSort = bundle.getInt("searchSort");
		startPrice = bundle.getString("startPrice");
		endPrice = bundle.getString("endPrice");
		uid = bundle.getInt("uid");
		new Thread(){
			public void run(){
				try{//连接网络并打开流
			        s = new Socket("192.168.9.100", 9999);
			        dout = new DataOutputStream(s.getOutputStream());
			        din = new DataInputStream(s.getInputStream());
			        sendToServerMsg();
				}
				catch(Exception e){//捕获异常
					e.printStackTrace();//打印异常
				}			
			}
		}.start();

		this.setContentView(R.layout.search_result);
		
		previousButton = (ImageButton)findViewById(R.id.previousButton);
		next_button = (ImageButton)findViewById(R.id.nextButton);
		back_button = (ImageButton)findViewById(R.id.backButton);
		previousButton.setOnClickListener(this);
		next_button.setOnClickListener(this);
		back_button.setOnClickListener(this);
		
		searchResultTextView = (TextView)findViewById(R.id.searchResultTextView);
		searchResultTextView.setText("结果：找到“"+infoValues+"”相关美食"+totleNumber+"个。");
		lv = (ListView)findViewById(R.id.searchResultListView);
		lv.setOnItemClickListener(SearchActivity.this);
		
	    previousButton.setEnabled(false);
	    if((currentPageNo*span)>=totleNumber){
	    	next_button.setEnabled(false);
	    }
    }

    public void sendToServerMsg(){//向服务器发送搜索消息
    	new Thread(){
    		public void run(){
    	    	try{
    	    		dout.writeUTF("<#SEARCH#>"+infoValues+"|"+searchSort+"|"+startPrice+"|"+endPrice+"|"+span+"|"+currentPageNo);//向服务器发送搜索消息
    	    		String msg=din.readUTF();//接收服务器发送来的消息
					if(msg.startsWith("<#SEARCHINFO#>")){//美食搜索结果
						msg=msg.substring(14);//截取子串
						String[] number = msg.split("\\|");//分割字符串
						totleNumber = Integer.parseInt(number[1]);//得到符合搜索添加的美食总个数
						String[][] strs = new String[Integer.parseInt(number[0])][8];
						ArrayList mstxInfos = new ArrayList();
						for(int i=0; i<strs.length; i++){
							String temp = din.readUTF();
							System.out.println(temp);
							String[] str = temp.split("\\|");//分割字符串
							mstxInfos.add(str);
						}

						ArrayList mstxImages = new ArrayList();

						for(int i=0; i<strs.length; i++){
							int size = din.readInt();
							byte[] bs = new byte[size];
							din.read(bs);
							mstxImages.add(bs);
						}
						
						Message message = new Message();//创建消息
						Bundle data = new Bundle();//创建数据
						data.putStringArrayList("mstxInfo",mstxInfos);//向bundle中添加数据
						data.putStringArrayList("mstxImages",mstxImages);//向bundle中添加数据
						message.what = 1;//设置消息的what值
						message.setData(data);//存放数据
						myHandler.sendMessage(message);//发送消息
					}
    	    	}
    	    	catch(Exception e){
    	    		e.printStackTrace();
    	    	}  			
    		}
    	}.start();
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String[] mstxStrs = (String[]) mstxInfos.get(arg2);
		Intent intent = new Intent();//创建Intent
		intent.setClass(SearchActivity.this, InfoActivity.class);
		Bundle bundle = new Bundle();//创建数据Bundle
		bundle.putInt("action", 3);
		bundle.putInt("uid", uid);
		bundle.putStringArray("mstxInfo", mstxStrs);
		intent.putExtras(bundle);//存放数据
		startActivity(intent);//启动Activity
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
			LinearLayout linearLayout1 = new LinearLayout(SearchActivity.this);
			linearLayout1.setOrientation(LinearLayout.HORIZONTAL);//设置朝向	
			linearLayout1.setPadding(5,5,5,5);//设置四周留白

			//初始化ImageView
			ImageView  ii=new ImageView(SearchActivity.this);
			byte[] bs = (byte[])mstxImages.get(arg0);
			Bitmap b = BitmapFactory.decodeByteArray(bs,0,bs.length);
			ii.setImageBitmap(b);

			ii.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片显示方式
			ii.setLayoutParams(new Gallery.LayoutParams(130,90));
			linearLayout1.addView(ii);//添加到LinearLayout中

			LinearLayout linearLayout2 = new LinearLayout(SearchActivity.this);
			linearLayout2.setOrientation(LinearLayout.VERTICAL);//设置朝向	
			linearLayout2.setPadding(5,1,5,1);//设置四周留白
			linearLayout2.setGravity(Gravity.TOP);//设置对其方式

			//初始化TextView,美食名称
			TextView title=new TextView(SearchActivity.this);
			String[] mstxStrs = (String[]) mstxInfos.get(arg0);
			title.setText(mstxStrs[1]);//设置文字
			title.setTextSize(18);//设置字体大小
			title.setTextColor(Color.RED);
			title.setPadding(5,1,5,1);//设置四周留白
			title.setMaxLines(1);//设置行数
			title.setGravity(Gravity.LEFT);//对齐方式
		    linearLayout2.addView(title);//添加到LinearLayout中
		    
			//初始化TextView
			TextView date = new TextView(SearchActivity.this);
			date.setText(mstxStrs[5]);
			date.setTextSize(10);//设置字体大小
			date.setTextColor(Color.GRAY);//文字的颜色
			date.setPadding(5,1,5,1);//设置四周留白
			title.setMaxLines(1);//设置行数
			date.setGravity(Gravity.LEFT);//对齐方式
		    linearLayout2.addView(date);//添加到LinearLayout中
		    
			//初始化TextView
			TextView info_dis = new TextView(SearchActivity.this);
			info_dis.setText(mstxStrs[2]);
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
	public void onClick(View v) {
		if(v == back_button){//返回按钮
			this.finish();
		}
		else if(v == previousButton){//上一页
			currentPageNo = currentPageNo - 1;
			if(currentPageNo == 1){
				previousButton.setEnabled(false);
			}
		    if((currentPageNo*span) < totleNumber){
		    	next_button.setEnabled(true);
		    }
		    sendToServerMsg();
		}
		else if(v == next_button){//下一页
			currentPageNo = currentPageNo + 1;
			if(currentPageNo != 1){
				previousButton.setEnabled(true);
			}
		    if((currentPageNo*span) >= totleNumber){
		    	next_button.setEnabled(false);
		    }
		    sendToServerMsg();
		}
	}

}