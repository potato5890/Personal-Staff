package wyf.ytl;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
public class PhotoActivity extends Activity implements SurfaceHolder.Callback, OnClickListener{
	SurfaceView mySurfaceView;//SurfaceView的引用
	SurfaceHolder mySurfaceHolder;//SurfaceHolder的引用
	ImageButton photoButton = null;//拍照按钮
	ImageButton backButton = null;
	Camera myCamera;//Camera的引用
	boolean isView = false;//是否在浏览中
	public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
		super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.photo);//设置当前的用户界面
		photoButton = (ImageButton) findViewById(R.id.photoButton);//得到按钮的引用
		backButton = (ImageButton)findViewById(R.id.backButton);
		photoButton.setOnClickListener(this);//为按钮添加监听
		backButton.setOnClickListener(this);//为返回按钮添加监听
		mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);//得到SurfaceView的引用
        mySurfaceHolder = mySurfaceView.getHolder();//获得SurfaceHolder
        mySurfaceHolder.addCallback(this);//添加接口的实现
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	public void initCamera(){//初始化相机方法
		if(!isView){//是否已经显示
    		myCamera = Camera.open();
    	}
		if(myCamera != null && !isView){
            try {
            	Camera.Parameters myParameters = myCamera.getParameters();
            	myParameters.setPictureFormat(PixelFormat.JPEG);
            	myParameters.setPreviewSize(320, 445);//屏幕大小
            	myCamera.setParameters(myParameters);
    			myCamera.setPreviewDisplay(mySurfaceHolder);
    			myCamera.startPreview();//立即运行Preview
    		} 
            catch (IOException e) {//捕获异常
    			e.printStackTrace();//打印错误信息
    		}  
            isView = true;//将是否显示标志位设置成true
    	}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//Activity创建时被调用
		initCamera();//初始化相机
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//Activity释放时被调用
		isView = false;
		if(myCamera != null){
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;			
		}
	}
	@Override
	public void onClick(View v) {//按钮监听方法
		if(v == photoButton){//拍照
			myCamera.takePicture(myShutterCallback, myRawCallback, myjpegCallback);
		}
		else if(v == backButton){//按下返回按钮
			isView = false;
			if(myCamera != null){
				myCamera.stopPreview();
				myCamera.release();
				myCamera = null;			
			}
			PhotoActivity.this.finish();//释放该Activity
		}
	}
	ShutterCallback myShutterCallback = new ShutterCallback(){
		@Override
		public void onShutter(){}
	};
	PictureCallback myRawCallback = new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {}
	};	
	PictureCallback myjpegCallback = new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Intent intent = new Intent();//创建Intent
			Bundle bundle = new Bundle();
			bundle.putByteArray("image", data);//存放数据
			
			intent.putExtras(bundle);//将数据存放到Intent中
			PhotoActivity.this.setResult(RESULT_OK, intent);
			isView = false;
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;
			//initCamera();//初始化相机
			PhotoActivity.this.finish();//释放该Activity
		}
	};

}
