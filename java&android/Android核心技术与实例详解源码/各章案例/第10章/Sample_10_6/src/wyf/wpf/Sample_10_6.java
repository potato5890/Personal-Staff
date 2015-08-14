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
	MyOpenHelper myHelper;		//����MyOpenHelper����
	String [] contactsName;		//�������ڴ����ϵ������������
	String [] contactsPhone;	//�������ڴ����ϵ�˵绰������
	int [] contactsId;			//�������ڴ����ϵ��id������
	final int MENU_ADD = Menu.FIRST;			//�����˵�ѡ�е�ID
	final int MENU_DELETE = Menu.FIRST+1;		//�����˵���ı��
	final int DIALOG_DELETE = 0;		//ȷ��ɾ���Ի����ID
	ListView lv;						//����ListView����
	BaseAdapter myAdapter = new BaseAdapter(){
		@Override
		public int getCount() {
			if(contactsName != null){		//����������鲻Ϊ��
				return contactsName.length;
			}
			else {
				return 0;					//�����������Ϊ���򷵻�0
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
			tv2.setGravity(Gravity.BOTTOM|Gravity.RIGHT);	//����TextView�ؼ��ڸ������е�λ��
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
				intent.putExtra("cmd", 0);		//0�����ѯ��ϵ�ˣ�1���������ϵ��
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
    //��������ȡ������ϵ�˵�����
    public void getBasicInfo(MyOpenHelper helper){
    	SQLiteDatabase db = helper.getWritableDatabase();		//��ȡ���ݿ�����
    	Cursor c = db.query(TABLE_NAME, new String[]{ID,NAME,PHONE}, null, null, null, null, ID);
    	int idIndex = c.getColumnIndex(ID);
    	int nameIndex = c.getColumnIndex(NAME);		//��������е��к�
    	int phoneIndex = c.getColumnIndex(PHONE);	//��õ绰�е����
    	contactsName = new String[c.getCount()];			//�������������String�������
    	contactsId = new int[c.getCount()];			//�������id��int�������
    	contactsPhone = new String[c.getCount()];	//�������phone���������
    	int i=0;			//����һ��������
    	for(c.moveToFirst();!(c.isAfterLast());c.moveToNext()){
    		contactsName[i] = c.getString(nameIndex);			//��������ӵ�String������
    		contactsId[i] = c.getInt(idIndex);
    		contactsPhone[i] = c.getString(phoneIndex);			//���̶��绰��ӵ�String������
    		i++;
    	}
    	c.close();				//�ر�Cursor����
    	db.close();				//�ر�SQLiteDatabase����
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, R.string.menu_add)
			.setIcon(R.drawable.add);			//��ӡ���ӡ��˵�ѡ��
		menu.add(0, MENU_DELETE, 0, R.string.menu_delete)
			.setIcon(R.drawable.delete);		//��ӡ�ɾ�����˵�ѡ��
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){		//�жϰ��µĲ˵�ѡ��
		case MENU_ADD:			//������Ӱ�ť
			Intent intent= new Intent(wyf.wpf.Sample_10_6.this,wyf.wpf.DetailActivity.class);
			intent.putExtra("cmd", 1);
			startActivity(intent);
			break;
		case MENU_DELETE:				//������ɾ��ѡ��
			showDialog(DIALOG_DELETE);	//��ʾȷ��ɾ���Ի���
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch(id){			//�ԶԻ���ID�����ж�
		case DIALOG_DELETE:		//����ɾ��ȷ�϶Ի���
			Builder b = new AlertDialog.Builder(this);	
			b.setIcon(R.drawable.dialog_delete);		//���öԻ���ͼ��
			b.setTitle("��ʾ");							//���öԻ������
			b.setMessage(R.string.dialog_message);		//���öԻ�������
			b.setPositiveButton(
					R.string.ok,
					new OnClickListener() {				//����ȷ��ɾ����ť
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
	//������ɾ��ָ����ϵ��
	public void deleteContact(int id){
		SQLiteDatabase db = myHelper.getWritableDatabase();		//������ݿ����
		db.delete(TABLE_NAME, ID+"=?", new String[]{id+""});
		db.close();
	}
    

}