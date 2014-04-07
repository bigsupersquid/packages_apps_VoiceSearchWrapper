package com.squid.voicesearchwrapper;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AndroidWidget extends AppWidgetProvider {

	private static final String WRAPPER_ACTION = "com.squid.voicesearchwrapper.wrapperactivity";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {	

		ComponentName thisWidget = new ComponentName(context,
				AndroidWidget.class);

		// Obtain all instances of our widget
		// Remember that you can have as many instances of the same widget as you want on the home screen 

		int[] allWidgetInstancesIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetInstancesIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget_layout);

			// Create an intent that when received will launch the WrapperActivity
			Intent intent = new Intent(context, AndroidWidget.class);
			intent.setAction(WRAPPER_ACTION);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			// Set up the onClickListener of the widget
			// Now, when the widget is pressed the pendingIntent will be sent

			remoteViews.setOnClickPendingIntent(R.id.ic_mic_button, pendingIntent);

			appWidgetManager.updateAppWidget(widgetId, remoteViews);

		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

	@Override
	public void onReceive(final Context context, Intent intent) {

		// If the intent is the one that we've defined to launch the wrapper
		// then create and launch the WrapperActivity
		if (intent.getAction().equals(WRAPPER_ACTION)) {

			Intent wrapperIntent = new Intent(context, WrapperActivity.class);
			wrapperIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(wrapperIntent);

		}

		super.onReceive(context, intent);

	}

}