package wyf.wpf;							//���������
import java.io.DataInputStream;				//���������
import java.net.Socket;						//���������
import android.app.Activity;				//���������
import android.os.Bundle;					//���������
import android.widget.EditText;				//���������
public class Sample_10_1_Client extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//���õ�ǰ��Ļ
        connectToServer();						//���ӷ����
    }
    public void connectToServer(){					//���������ӿͻ���
    	try{
    		Socket socket = new Socket("192.168.9.100", 8888);//����Socket����
    		DataInputStream din = new DataInputStream(socket.getInputStream());	//���DataInputStream����
    		String msg = din.readUTF();				 			//��ȡ����˷�������Ϣ
    		EditText et = (EditText)findViewById(R.id.et);		//���EditText����
    		et.setText(msg);									//����EditText����
    	}
    	catch(Exception e){										//���񲢴�ӡ�쳣
    		e.printStackTrace();
    	}
    }
}