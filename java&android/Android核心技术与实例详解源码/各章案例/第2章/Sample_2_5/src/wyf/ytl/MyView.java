package wyf.ytl;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
public class MyView extends View{//�̳���View
	Paint paint;//����
	public MyView(Context context) {//������
		super(context);
		paint = new Paint();//��ʼ������
		paint.setColor(Color.WHITE);//���û��ʵ���ɫ
		paint.setTextSize(20);//��������Ĵ�С
		paint.setAntiAlias(true);//�򿪿����
	}
	protected void onDraw(Canvas canvas) {//��д�Ļ��Ʒ���
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);//���Ʊ�����ɫ
		canvas.drawRect(10, 10, 110, 110, paint);//����һ������
		canvas.drawText("�˽���Ϊ�Զ����View", 60, 170, paint);
	}
}