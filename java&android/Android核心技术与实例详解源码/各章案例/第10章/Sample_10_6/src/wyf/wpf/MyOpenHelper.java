package wyf.wpf;			//声明包语句
import android.content.Context;				
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
public class MyOpenHelper extends SQLiteOpenHelper{
	public static final String DB_NAME = "personal_contacts";	//数据库文件名称
	public static final String TABLE_NAME = "contacts";		//表名
	public static final String ID="_id";						//ID
	public static final String NAME="name";					//名称
	public static final String PHONE="phone";					//固定电话
	public static final String MOBILE="mobile";				//手机号码
	public static final String EMAIL="email";					//电子邮件地址
	public static final String POST="post";					//邮政编码
	public static final String ADDR="addr";					//通信地址
	public static final String COMP="comp";					//公司

	public MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);		//调用父类构造器
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//重写onCreate方法
		db.execSQL("create table if not exists "+TABLE_NAME+" ("	//调用execSQL方法创建表
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
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	//重写onUpgrade方法
		
	}
	
}