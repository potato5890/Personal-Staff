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
	SurfaceView mySurfaceView;//SurfaceView������
	SurfaceHolder mySurfaceHolder;//SurfaceHolder������
	ImageButton photoButton = null;//���հ�ť
	ImageButton backButton = null;
	Camera myCamera;//Camera������
	boolean isView = false;//�Ƿ��������
	public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
		super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.photo);//���õ�ǰ���û�����
		photoButton = (ImageButton) findViewById(R.id.photoButton);//�õ���ť������
		backButton = (ImageButton)findViewById(R.id.backButton);
		photoButton.setOnClickListener(this);//Ϊ��ť��Ӽ���
		backButton.setOnClickListener(this);//Ϊ���ذ�ť��Ӽ���
		mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);//�õ�SurfaceView������
        mySurfaceHolder = mySurfaceView.getHolder();//���SurfaceHolder
        mySurfaceHolder.addCallback(this);//��ӽӿڵ�ʵ��
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	public void initCamera(){//��ʼ���������
		if(!isView){//�Ƿ��Ѿ���ʾ
    		myCamera = Camera.open();
    	}
		if(myCamera != null && !isView){
            try {
            	Camera.Parameters myParameters = myCamera.getParameters();
            	myParameters.setPictureFormat(PixelFormat.JPEG);
            	myParameters.setPreviewSize(320, 445);//��Ļ��С
            	myCamera.setParameters(myParameters);
    			myCamera.setPreviewDisplay(mySurfaceHolder);
    			myCamera.startPreview();//��������Preview
    		} 
            catch (IOException e) {//�����쳣
    			e.printStackTrace();//��ӡ������Ϣ
    		}  
            isView = true;//���Ƿ���ʾ��־λ���ó�true
    	}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//Activity����ʱ������
		initCamera();//��ʼ�����
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//Activity�ͷ�ʱ������
		isView = false;
		if(myCamera != null){
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;			
		}
	}
	@Override
	public void onClick(View v) {//��ť��������
		if(v == photoButton){//����
			myCamera.takePicture(myShutterCallback, myRawCallback, myjpegCallback);
		}
		else if(v == backButton){//���·��ذ�ť
			isView = false;
			if(myCamera != null){
				myCamera.stopPreview();
				myCamera.release();
				myCamera = null;			
			}
			PhotoActivity.this.finish();//�ͷŸ�Activity
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
			Intent intent = new Intent();//����Intent
			Bundle bundle = new Bundle();
			bundle.putByteArray("image", data);//�������
			
			intent.putExtras(bundle);//�����ݴ�ŵ�Intent��
			PhotoActivity.this.setResult(RESULT_OK, intent);
			isView = false;
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;
			//initCamera();//��ʼ�����
			PhotoActivity.this.finish();//�ͷŸ�Activity
		}
	};

}
