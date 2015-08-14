package wyf.ytl;

import java.util.List;

import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

//����������ͼ��׽�����¼���OverLay
class MyMapOverlay extends Overlay{
	boolean flagMove=false;
    @Override 
    public boolean onTouchEvent(MotionEvent event, MapView mv) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){//���ƶ��˴��ر��������ƶ���־Ϊtrue
        	flagMove=true;
        }
        else if(event.getAction() == MotionEvent.ACTION_DOWN){//��̧���˴��ر��������ƶ���־Ϊfalse
        	flagMove=false;
        }
        else if (MyBallonOverlay.currentBallon==null&&
        		 !flagMove&&
        		 event.getAction() == MotionEvent.ACTION_UP ){
        	//��û�е�ǰѡ�е�����Ҳû���ƶ����ر��Ҵ��ر�̧�����ȡ�˴��ľ�γ�Ȳ����������
        	GeoPoint gp = mv.getProjection().fromPixels(
        		 (int) event.getX(),  //���ر�����Ļ�ϵ�X����
        		 (int) event.getY()   //���ر�����Ļ�ϵ�Y����
            );
        	//��ʾ������ľ�γ��
            String latStr=Math.round(gp.getLatitudeE6()/1000.00)/1000.0+"";//γ��
        	String longStr=Math.round(gp.getLongitudeE6()/1000.00)/1000.0+"";//����        	
        	//������������showWindow���
        	List<Overlay> overlays = mv.getOverlays(); 
        	for(Overlay ol:overlays){//������������showWindow���
        		if(ol instanceof MyBallonOverlay){
        			overlays.remove(ol);
        		}
        	} 
        	//�ڵ��λ�����������
        	MyBallonOverlay mbo=new MyBallonOverlay(
        			gp,//���������
        			"����λ��Ϊ��\n���ȣ�"+longStr+"\nγ�ȣ�"+latStr//�������Ϣ
        	); 
            overlays.add(mbo); 
            return true;
        }
        return false;
    }
}