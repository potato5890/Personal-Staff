package wyf.wpf;
import static wyf.wpf.MyOpenHelper.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Sample_10_6 extends Activity {
	MyOpenHelper myHelper;		//声明MyOpenHelper对象
	String [] contactsName;		//声明用于存放联系人姓名的数组
	String [] contactsPhone;	//声明用于存放联系人电话的数组
	int [] contactsId;			//声明用于存放联系人id的数组
	final int MENU_ADD = Menu.FIRST;			//声明菜单选行的ID
	final int MENU_DELETE = Menu.FIRST+1;		//声明菜单项的编号
	final int DIALOG_DELETE = 0;		//确认删除对话框的ID
	ListView lv;						//声明ListView对象
	BaseAdapter myAdapter = new BaseAdapter(){
		@Override
		public int getCount() {
			if(contactsName != null){		//如果姓名数组不为空
				return contactsName.length;
			}
			else {
				return 0;					//如果姓名数组为空则返回0
			}
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(Sample_10_6.this);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			TextView tv = new TextView(Sample_10_6.this);
			tv.setText(contactsName[position]);
			tv.setTextSize(32);
			tv.setTextColor(Color.BLACK);
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			TextView tv2 = new TextView(Sample_10_6.this);
			tv2.setText("["+contactsPhone[position]+"]");
			tv2.setTextSize(28);
			tv2.setTextColor(Color.BLACK);
			tv2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv2.setGravity(Gravity.BOTTOM|Gravity.RIGHT);	//设置TextView控件在父容器中的位置
			ll.addView(tv);
			ll.addView(tv2); 
			return ll;
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        myHelper = new MyOpenHelper(this, DB_NAME, null, 1);
        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent= new Intent(wyf.wpf.Sample_10_6.this,wyf.wpf.DetailActivity.class);
				intent.putExtra("cmd", 0);		//0代表查询联系人，1代表添加联系人
				intent.putExtra("id", contactsId[position]);
				startActivity(intent);
			}
		});
    }
    @Override
	protected void onResume() {
		getBasicInfo(myHelper);
		myAdapter.notifyDataSetChanged();
		super.onResume();
	}
    //方法：获取所有联系人的姓名
    public void getBasicInfo(MyOpenHelper helper){
    	SQLiteDatabase db = helper.getWritableDatabase();		//获取数据库连接
    	Cursor c = db.query(TABLE_NAME, new String[]{ID,NAME,PHONE}, null, null, null, null, ID);
    	int idIndex = c.getColumnIndex(ID);
    	int nameIndex = c.getColumnIndex(NAME);		//获得姓名列的列号
    	int phoneIndex = c.getColumnIndex(PHONE);	//获得电话列的序号
    	contactsName = new String[c.getCount()];			//创建存放姓名的String数组对象
    	contactsId = new int[c.getCount()];			//创建存放id的int数组对象
    	contactsPhone = new String[c.getCount()];	//创建存放phone的数组对象
    	int i=0;			//声明一个计数器
    	for(c.moveToFirst();!(c.isAfterLast());c.moveToNext()){
    		contactsName[i] = c.getString(nameIndex);			//将姓名添加到String数组中
    		contactsId[i] = c.getInt(idIndex);
    		contactsPhone[i] = c.getString(phoneIndex);			//将固定电话添加到String数组中
    		i++;
    	}
    	c.close();				//关闭Cursor对象
    	db.close();				//关闭SQLiteDatabase对象
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, R.string.menu_add)
			.setIcon(R.drawable.add);			//添加“添加”菜单选项
		menu.add(0, MENU_DELETE, 0, R.string.menu_delete)
			.setIcon(R.drawable.delete);		//添加“删除”菜单选项
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){		//判断按下的菜单选项
		case MENU_ADD:			//按下添加按钮
			Intent intent= new Intent(wyf.wpf.Sample_10_6.this,wyf.wpf.DetailActivity.class);
			intent.putExtra("cmd", 1);
			startActivity(intent);
			break;
		case MENU_DELETE:				//按下了删除选项
			showDialog(DIALOG_DELETE);	//显示确认删除对话框
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){			//对对话框ID进行判断
		case DIALOG_DELETE:		//创建删除确认对话框
			Builder b = new AlertDialog.Builder(this);	
			b.setIcon(R.drawable.dialog_delete);		//设置对话框图标
			b.setTitle("提示");							//设置对话框标题
			b.setMessage(R.string.dialog_message);		//设置对话框内容
			b.setPositiveButton(
					R.string.ok,
					new OnClickListener() {				//点下确认删除按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int position = Sample_10_6.this.lv.getSelectedItemPosition();
							deleteContact(contactsId[position]);
							getBasicInfo(myHelper);
							myAdapter.notifyDataSetChanged();
						}
					});
			b.setNegativeButton(
					R.string.cancel,
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {}
					});
			dialog = b.create();
			break;
		}
		return dialog;
	}	
	//方法：删除指定联系人
	public void deleteContact(int id){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//获得数据库对象
		db.delete(TABLE_NAME, ID+"=?", new String[]{id+""});
		db.close();
	}
    

}