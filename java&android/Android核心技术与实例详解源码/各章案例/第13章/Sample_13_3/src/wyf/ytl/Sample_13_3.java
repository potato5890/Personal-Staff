package wyf.ytl;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.LiveFolders;
public class Sample_13_3 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getAction().equals(LiveFolders.ACTION_CREATE_LIVE_FOLDER)){
        	Intent intent = new Intent();
        	intent.setData(Uri.parse("content://contacts/live_folders/people"));
        	intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_BASE_INTENT, new Intent(Intent.ACTION_VIEW,ContactsContract.Contacts.CONTENT_URI));
        	intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, "电话本");
        	intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,Intent.ShortcutIconResource.fromContext(this, R.drawable.png1));//快捷方式的图标
        	intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE, LiveFolders.DISPLAY_MODE_LIST);
        	setResult(RESULT_OK, intent);
        }
        else{
        	setResult(RESULT_CANCELED);
        }
        finish();
    }
}