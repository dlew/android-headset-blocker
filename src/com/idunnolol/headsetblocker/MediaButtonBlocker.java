package com.idunnolol.headsetblocker;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class MediaButtonBlocker extends BroadcastReceiver {

	private static final ComponentName BLOCKER = new ComponentName("com.idunnolol.headsetblocker",
			"com.idunnolol.headsetblocker.MediaButtonBlocker");

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Params.LOGGING_ENABLED) {
			Log.i(Params.LOGGING_TAG, "Blocking MEDIA_BUTTON action.");
		}

		// Abort the broadcast.  No one will ever believe you.
		abortBroadcast();
	}

	public static boolean isBlocking(Context context) {
		PackageManager pm = context.getPackageManager();
		return pm.getComponentEnabledSetting(BLOCKER) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
	}

	public static void toggleBlocking(Context context) {
		if (Params.LOGGING_ENABLED) {
			Log.i(Params.LOGGING_TAG, "Toggling MEDIA_BUTTON blocking.");
		}

		PackageManager pm = context.getPackageManager();
		int newState = (isBlocking(context)) ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED
				: PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
		pm.setComponentEnabledSetting(BLOCKER, newState, PackageManager.DONT_KILL_APP);
	}
}
