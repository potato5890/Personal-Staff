package wyf.wpf;
import static wyf.wpf.MyOpenHelper.*;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DetailActivity extends Activity{
	MyOpenHelper myHelper;			//����һ��MyOpenHelper����
	final int MENU_ADD = Menu.FIRST;			//�����˵���ı��
	final int MENU_MODIFY = Menu.FIRST+1;		//�����˵���ı��
	final int MENU_DELETE = Menu.FIRST+2;		//�����˵���ı��
	final int MENU_SAVE = Menu.FIRST+3;			//�����˵���ı��

	int id = -1;					//��¼��ǰ��ʾ����ϵ��id
	int [] textIds ={
		R.id.etName,
		R.id.etPhone,
		R.id.etMobile,
		R.id.etEmail,
		R.id.etPost,
		R.id.etAddr,
		R.id.etComp
	};
	EditText [] textArray;			//��Ž����е�EditText�ؼ�������
	ImageButton ibSave;				//���水ť
	int status = -1;				//0��ʾ�鿴��Ϣ��1��ʾ�����ϵ�ˣ�2��ʾ�޸���ϵ��
	View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			//���水ť���´������¼�
			String [] strArray = new String[textArray.length];
			for(int i=0;i<strArray.length;i++){
				strArray[i] = textArray[i].getText().toString().trim();	//����û��������Ϣ����
			}
			if(strArray[0].equals("") || strArray[1].equals("")){
				Toast.makeText(DetailActivity.this, "�Բ����뽫�����͵绰��д����!", Toast.LENGTH_LONG).show();
			}
			switch(status){		//�жϵ�ǰ��״̬
			case 0:				//��ѯ��ϵ����ϸ��Ϣʱ���±���
				updateContact(strArray);		//������ϵ����Ϣ
				break;
			case 1:				//�½���ϵ��ʱ���±��水ť
				insertContact(strArray);		//������ϵ����Ϣ
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		textArray = new EditText[textIds.length];
		for(int i=0;i<textIds.length;i++){
			textArray[i] = (EditText)findViewById(textIds[i]);
		}
		ibSave = (ImageButton)findViewById(R.id.ibSave);	
		ibSave.setOnClickListener(myListener);
		myHelper = new MyOpenHelper(this, MyOpenHelper.DB_NAME, null, 1);
		Intent intent = getIntent();
		status = intent.getExtras().getInt("cmd");			//��ȡ��������
		switch(status){
		case 0:			//�鿴��ϵ�˵���ϸ��Ϣ
			id = intent.getExtras().getInt("id");		//���Ҫ��ʾ����ϵ�˵�id
			SQLiteDatabase db = myHelper.getWritableDatabase();
			Cursor c = db.query(MyOpenHelper.TABLE_NAME, new String[]{NAME,PHONE,MOBILE,EMAIL,POST,ADDR,COMP}, ID+"=?", new String[]{id+""}, null, null, null);
			if(c.getCount() == 0){			//û�в�ѯ��ָ������
				Toast.makeText(this, "�Բ���û���ҵ�ָ������ϵ�ˣ�", Toast.LENGTH_LONG).show();
			}
			else{				//��ѯ���������
				c.moveToFirst();			//�ƶ�����һ����¼
				textArray[0].setText(c.getString(0));			//�����������е�����
				textArray[1].setText(c.getString(1));			//���ù̻����е�����
				textArray[2].setText(c.getString(2));			//�����ֻ�������е�����
				textArray[3].setText(c.getString(3));			//���õ����ʼ����е�����
				textArray[4].setText(c.getString(4));			//���õ����ʼ����е�����
				textArray[5].setText(c.getString(5));			//���õ����ʼ����е�����
				textArray[6].setText(c.getString(6));			//���õ����ʼ����е�����
			}
			c.close();
			db.close();
			break;
		case 1:					//�½���ϸ����Ϣ
			for(EditText et:textArray){
				et.getEditableText().clear();		//��ո���EditText�ؼ�������
			}
			break;
		}
	}

	//���������ָ����ϵ��
	public void insertContact(String [] strArray){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//������ݿ����
		ContentValues values = new ContentValues();
		values.put(NAME, strArray[0]);
		values.put(PHONE, strArray[1]);
		values.put(MOBILE, strArray[2]);
		values.put(EMAIL, strArray[3]);	
		values.put(POST, strArray[4]);
		values.put(ADDR, strArray[5]);
		values.put(COMP, strArray[6]);
		long count = db.insert(TABLE_NAME, ID, values);			//��������
		db.close();
		if(count == -1){
			Toast.makeText(this, "�����ϵ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "�����ϵ�˳ɹ���", Toast.LENGTH_LONG).show();
		}
	}
	//����������ĳ����ϵ����Ϣ
	public void updateContact(String [] strArray){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//������ݿ����
		ContentValues values = new ContentValues();
		values.put(NAME, strArray[0]);
		values.put(PHONE, strArray[1]);
		values.put(MOBILE, strArray[2]);
		values.put(EMAIL, strArray[3]);	
		values.put(POST, strArray[4]);
		values.put(ADDR, strArray[5]);
		values.put(COMP, strArray[6]);
		int count = db.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});	//�������ݿ�
		db.close();
		if(count == 1){
			Toast.makeText(this, "�޸���ϵ�˳ɹ���", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "�޸���ϵ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
		}
	}
}