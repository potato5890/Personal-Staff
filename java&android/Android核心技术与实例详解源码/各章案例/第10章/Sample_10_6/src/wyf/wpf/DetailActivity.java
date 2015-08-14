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
	MyOpenHelper myHelper;			//声明一个MyOpenHelper对象
	final int MENU_ADD = Menu.FIRST;			//声明菜单项的编号
	final int MENU_MODIFY = Menu.FIRST+1;		//声明菜单项的编号
	final int MENU_DELETE = Menu.FIRST+2;		//声明菜单项的编号
	final int MENU_SAVE = Menu.FIRST+3;			//声明菜单项的编号

	int id = -1;					//记录当前显示的联系人id
	int [] textIds ={
		R.id.etName,
		R.id.etPhone,
		R.id.etMobile,
		R.id.etEmail,
		R.id.etPost,
		R.id.etAddr,
		R.id.etComp
	};
	EditText [] textArray;			//存放界面中的EditText控件的数组
	ImageButton ibSave;				//保存按钮
	int status = -1;				//0表示查看信息，1表示添加联系人，2表示修改联系人
	View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {			//保存按钮按下触发该事件
			String [] strArray = new String[textArray.length];
			for(int i=0;i<strArray.length;i++){
				strArray[i] = textArray[i].getText().toString().trim();	//获得用户输入的信息数组
			}
			if(strArray[0].equals("") || strArray[1].equals("")){
				Toast.makeText(DetailActivity.this, "对不起，请将姓名和电话填写完整!", Toast.LENGTH_LONG).show();
			}
			switch(status){		//判断当前的状态
			case 0:				//查询联系人详细信息时按下保存
				updateContact(strArray);		//更新联系人信息
				break;
			case 1:				//新建联系人时按下保存按钮
				insertContact(strArray);		//插入联系人信息
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
		status = intent.getExtras().getInt("cmd");			//读取命令类型
		switch(status){
		case 0:			//查看联系人的详细信息
			id = intent.getExtras().getInt("id");		//获得要显示的联系人的id
			SQLiteDatabase db = myHelper.getWritableDatabase();
			Cursor c = db.query(MyOpenHelper.TABLE_NAME, new String[]{NAME,PHONE,MOBILE,EMAIL,POST,ADDR,COMP}, ID+"=?", new String[]{id+""}, null, null, null);
			if(c.getCount() == 0){			//没有查询到指定的人
				Toast.makeText(this, "对不起，没有找到指定的联系人！", Toast.LENGTH_LONG).show();
			}
			else{				//查询到了这个人
				c.moveToFirst();			//移动到第一条记录
				textArray[0].setText(c.getString(0));			//设置姓名框中的内容
				textArray[1].setText(c.getString(1));			//设置固话框中的内容
				textArray[2].setText(c.getString(2));			//设置手机号码框中的内容
				textArray[3].setText(c.getString(3));			//设置电子邮件框中的内容
				textArray[4].setText(c.getString(4));			//设置电子邮件框中的内容
				textArray[5].setText(c.getString(5));			//设置电子邮件框中的内容
				textArray[6].setText(c.getString(6));			//设置电子邮件框中的内容
			}
			c.close();
			db.close();
			break;
		case 1:					//新建详细人信息
			for(EditText et:textArray){
				et.getEditableText().clear();		//清空各个EditText控件中内容
			}
			break;
		}
	}

	//方法：添加指定联系人
	public void insertContact(String [] strArray){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//获得数据库对象
		ContentValues values = new ContentValues();
		values.put(NAME, strArray[0]);
		values.put(PHONE, strArray[1]);
		values.put(MOBILE, strArray[2]);
		values.put(EMAIL, strArray[3]);	
		values.put(POST, strArray[4]);
		values.put(ADDR, strArray[5]);
		values.put(COMP, strArray[6]);
		long count = db.insert(TABLE_NAME, ID, values);			//插入数据
		db.close();
		if(count == -1){
			Toast.makeText(this, "添加联系人失败！", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "添加联系人成功！", Toast.LENGTH_LONG).show();
		}
	}
	//方法：更新某个联系人信息
	public void updateContact(String [] strArray){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//获得数据库对象
		ContentValues values = new ContentValues();
		values.put(NAME, strArray[0]);
		values.put(PHONE, strArray[1]);
		values.put(MOBILE, strArray[2]);
		values.put(EMAIL, strArray[3]);	
		values.put(POST, strArray[4]);
		values.put(ADDR, strArray[5]);
		values.put(COMP, strArray[6]);
		int count = db.update(TABLE_NAME, values, ID+"=?", new String[]{id+""});	//更新数据库
		db.close();
		if(count == 1){
			Toast.makeText(this, "修改联系人成功！", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "修改联系人失败！", Toast.LENGTH_LONG).show();
		}
	}
}