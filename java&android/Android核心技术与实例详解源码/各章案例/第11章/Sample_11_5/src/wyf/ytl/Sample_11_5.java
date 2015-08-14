package wyf.ytl;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class Sample_11_5 extends Activity {
	int[] numButtonIds={//���ְ�ť��ID����
    	R.id.Button00,R.id.Button01,R.id.Button02,    
    	R.id.Button03,R.id.Button04,R.id.Button05,
    	R.id.Button06,R.id.Button07,R.id.Button08,
    	R.id.Button09
    };
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button bDel=(Button)this.findViewById(R.id.Button_del);
        bDel.setOnClickListener(//Ϊɾ����ť��Ӽ�����
                //OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
                new View.OnClickListener(){ 
                	public void onClick(View v){ 
						EditText et=(EditText)findViewById(R.id.EditText01);
						String num=et.getText().toString();
						num=(num.length()>1)?num.substring(0,num.length()-1):"";
						et.setText(num);//��֯�ַ���
                	} 
        });   
        Button bDial=(Button)this.findViewById(R.id.Button_dial);
        bDial.setOnClickListener(//Ϊ���Ű�ť��Ӽ�����
                //OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
                new View.OnClickListener(){ 
                	public void onClick(View v){ 
                		//��ȡ����ĵ绰����
                		EditText et=(EditText)findViewById(R.id.EditText01);
                		String num=et.getText().toString();
                		//���ݻ�ȡ�ĵ绰���봴��Intent����
                		Intent dial = new Intent();
                		dial.setAction("android.intent.action.CALL");
                		dial.setData(Uri.parse("tel://"+num));
                		startActivity(dial);  //�����绰��Activity 
                	} 
                }
        );    
        Button bCancel=(Button)this.findViewById(R.id.Button_cancel);
        bCancel.setOnClickListener(//Ϊ�˳���ť��Ӽ�����
                //OnClickListenerΪView���ڲ��ӿڣ���ʵ���߸������������¼�
        	new View.OnClickListener(){ 
        		public void onClick(View v){ 
        			Sample_11_5.this.finish();//�Ƿ񴰿�
        		} 
        	}
        );    
        View.OnClickListener numListener=new  View.OnClickListener(){
        	public void onClick(View v){ //Ϊ0-9���ְ�ť����������
        		Button tempb=(Button)v;//�õ���ť������
        		EditText et=(EditText)findViewById(R.id.EditText01);//�õ�EditText������
        		et.append(tempb.getText());//��֯�ַ���
            }
        };
        for(int id:numButtonIds){//Ϊ�������ְ�ť��Ӽ�����
        	Button tempb=(Button)this.findViewById(id);//�õ���ť
        	tempb.setOnClickListener(numListener);//��Ӽ���
        }
    }
}