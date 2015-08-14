package wyf.wpf;			//���������
import android.content.Context;				
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class MyOpenHelper extends SQLiteOpenHelper{
	public static final String DB_NAME = "personal_contacts";	//���ݿ��ļ�����
	public static final String TABLE_NAME = "contacts";		//����
	public static final String ID="_id";						//ID
	public static final String NAME="name";					//����
	public static final String PHONE="phone";					//�̶��绰
	public static final String MOBILE="mobile";				//�ֻ�����
	public static final String EMAIL="email";					//�����ʼ���ַ
	public static final String POST="post";					//��������
	public static final String ADDR="addr";					//ͨ�ŵ�ַ
	public static final String COMP="comp";					//��˾

	public MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		//���ø��๹����
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//��дonCreate����
		db.execSQL("create table if not exists "+TABLE_NAME+" ("	//����execSQL����������
				+ ID + " integer primary key,"
				+ NAME + " varchar,"
				+ PHONE+" varchar,"
				+ MOBILE + " varchar,"
				+ EMAIL + " varchar,"
				+ POST + " varchar,"
				+ ADDR + " varchar,"
				+ COMP + " varchar)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	//��дonUpgrade����
		
	}
	
}