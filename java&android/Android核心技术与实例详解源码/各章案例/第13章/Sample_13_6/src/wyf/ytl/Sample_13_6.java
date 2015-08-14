package wyf.ytl;
import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.ImageView;
public class Sample_13_6 extends Activity implements SurfaceHolder.Callback, OnClickListener{
	SurfaceView mySurfaceView;//SurfaceView������
	SurfaceHolder mySurfaceHolder;//SurfaceHolder������
	Button button1;//�򿪰�ť
	Button button2;//�رհ�ť
	Button button3;//���հ�ť
	Camera myCamera;//Camera������
	boolean isView = false;//�Ƿ��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);//�õ�SurfaceView������
        button1 = (Button) findViewById(R.id.button1);//�õ���ť������
        button2 = (Button) findViewById(R.id.button2);//�õ���ť������
        button3 = (Button) findViewById(R.id.button3);//�õ���ť������
        button1.setOnClickListener(this);//Ϊ��ť��Ӽ���
        button2.setOnClickListener(this);//Ϊ��ť��Ӽ���
        button3.setOnClickListener(this);//Ϊ��ť��Ӽ���
        mySurfaceHolder = mySurfaceView.getHolder();//���SurfaceHolder
        mySurfaceHolder.addCallback(this);//��ӽӿڵ�ʵ��
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);        
    }
	@Override
	public void onClick(View v) {
		if(v == button1){//�򿪰�ť
			initCamera();//��ʼ�����
		}
		else if(v == button2){
			if(myCamera != null && isView){//��������ʾʱ
				isView = false;
				myCamera.stopPreview();
				myCamera.release();
				myCamera = null;
			}
		}
		else if(v == button3){//����
			myCamera.takePicture(myShutterCallback, myRawCallback, myjpegCallback);
		}
	}
	public void initCamera(){
		if(!isView){
    		myCamera = Camera.open();
    	}
    	if(myCamera != null && !isView){
            try {
            	Camera.Parameters myParameters = myCamera.getParameters();
            	myParameters.setPictureFormat(PixelFormat.JPEG);
            	myParameters.setPreviewSize(200, 200);//��Ļ��С
            	myCamera.setParameters(myParameters);
    			myCamera.setPreviewDisplay(mySurfaceHolder);
    			myCamera.startPreview();//��������Preview
    		} 
            catch (IOException e) {//�����쳣
    			e.printStackTrace();//��ӡ������Ϣ
    		}  	
    		isView = true;
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
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			ImageView myImageView = (ImageView) findViewById(R.id.myImageView);
			myImageView.setImageBitmap(bm);//��ͼƬ��ʾ���·���ImageView��
			isView = false;
			myCamera.stopPreview();
			myCamera.release();
			myCamera = null;
			initCamera();//��ʼ�����
		}
	};	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}
}