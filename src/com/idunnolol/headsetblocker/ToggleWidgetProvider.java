package com.idunnolol.headsetblocker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;

public class ToggleWidgetProvider extends AppWidgetProvider {

	private static final ComponentName THIS_APPWIDGET = new ComponentName("com.idunnolol.headsetblocker",
			"com.idunnolol.headsetblocker.ToggleWidgetProvider");

	private static final ComponentName BLOCKER = new ComponentName("com.idunnolol.headsetblocker",
			"com.idunnolol.headsetblocker.MediaButtonBlocker");

	private static final String TOGGLE_ACTION = "com.idunnolol.headsetblocker.TOGGLE";

	private static final int F_TRANSITION = 1;

	// Static variable used to prevent click-happy jerks.
	private static boolean isToggling = false;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		final int len = appWidgetIds.length;

		for (int a = 0; a < len; a++) {
			int appWidgetId = appWidgetIds[a];
			appWidgetManager.updateAppWidget(appWidgetId, buildUpdate(context, 0));
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		if (!intent.getAction().equals(TOGGLE_ACTION)) {
			// Otherwise, we toggle when you delete the widget!
			if (Params.LOGGING_ENABLED) {
				Log.i(Params.LOGGING_TAG, "Received intent but it has nothing to do with toggling.");
			}
			return;
		}

		if (Params.LOGGING_ENABLED) {
			Log.i(Params.LOGGING_TAG, "Received intent for toggling.");
		}

		if (!isToggling) {
			isToggling = true;
			updateWidget(context, F_TRANSITION);
			toggleBlocking(context);
			updateWidget(context, 0);
			isToggling = false;
		}
	}

	private RemoteViews buildUpdate(Context context, int flags) {
		Resources r = context.getResources();
		int buttonImageId;
		int textColor;
		if ((flags & F_TRANSITION) != 0) {
			buttonImageId = R.drawable.appwidget_ind_mid;
			textColor = r.getColor(android.R.color.darker_gray);
		}
		else if (isBlocking(context)) {
			buttonImageId = R.drawable.appwidget_ind_light;
			textColor = r.getColor(android.R.color.white);
		}
		else {
			buttonImageId = R.drawable.appwidget_ind_off;
			textColor = r.getColor(android.R.color.darker_gray);
		}
		Intent intent = new Intent(context, ToggleWidgetProvider.class);
		intent.setAction(TOGGLE_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.toggle_widget);
		views.setOnClickPendingIntent(R.id.ToggleButton, pendingIntent);
		views.setImageViewResource(R.id.ToggleIndicator, buttonImageId);
		views.setTextColor(R.id.ToggleText, textColor);
		return views;
	}

	private void updateWidget(Context context, int flags) {
		RemoteViews views = buildUpdate(context, flags);
		final AppWidgetManager gm = AppWidgetManager.getInstance(context);
		gm.updateAppWidget(THIS_APPWIDGET, views);
	}

	private boolean isBlocking(Context context) {
		PackageManager pm = context.getPackageManager();
		return pm.getComponentEnabledSetting(BLOCKER) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
	}

	private void toggleBlocking(Context context) {
		if (Params.LOGGING_ENABLED) {
			Log.i(Params.LOGGING_TAG, "Toggling MEDIA_BUTTON blocking.");
		}

		PackageManager pm = context.getPackageManager();
		int newState = (isBlocking(context)) ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED
				: PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
		pm.setComponentEnabledSetting(BLOCKER, newState, PackageManager.DONT_KILL_APP);
	}
}
