package com.idunnolol.headsetblocker.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.idunnolol.headsetblocker.MediaButtonBlocker;
import com.idunnolol.headsetblocker.R;
import com.idunnolol.headsetblocker.ToggleWidgetProvider;

public class HeadsetBlockerActivity extends Activity {

	private Context mContext;

	ToggleButton mToggleButton;

	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			mToggleButton.setEnabled(false);
			MediaButtonBlocker.toggleBlocking(mContext);
			ToggleWidgetProvider.updateWidget(mContext, 0);
			mToggleButton.setEnabled(true);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;

		setContentView(R.layout.activity_headset_blocker);

		mToggleButton = (ToggleButton) findViewById(R.id.toggle_button);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Avoid the OnCheckedChangeListener from firing as we set the current status of
		// the toggle button.
		mToggleButton.setOnCheckedChangeListener(null);
		mToggleButton.setChecked(MediaButtonBlocker.isBlocking(this));
		mToggleButton.setOnCheckedChangeListener(mOnCheckedChangeListener);
	}
}
