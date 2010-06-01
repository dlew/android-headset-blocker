package com.idunnolol.headsetblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MediaButtonBlocker extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Params.LOGGING_ENABLED) {
			Log.i(Params.LOGGING_TAG, "Blocking MEDIA_BUTTON action.");
		}

		// Abort the broadcast.  No one will ever believe you.
		abortBroadcast();
	}
}
