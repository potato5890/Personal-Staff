package wyf.ytl;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
public class Sample_13_4 extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(),R.layout.my_layout);
		myRemoteViews.setImageViewResource(R.id.myImageView, R.drawable.png1);
		ComponentName myComponentName = new ComponentName(context,Sample_13_4.class);
		AppWidgetManager myAppWidgetManager = AppWidgetManager.getInstance(context);
		myAppWidgetManager.updateAppWidget(myComponentName,myRemoteViews);
	}
}