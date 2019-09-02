package com.novallc.android.wear.fhsAppWear;

/*
 * Copyright 2016. Jake Johnson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WearableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements WearableListView.ClickListener {

	private WearableListView listView;
	private String[] mItems = {"Regular Bell", "Tutorial Bell", "Late Start", "Double Assembly", "Minimum Day", "Finals Day"};
	private int[] mImages = {R.drawable.ic_home, R.drawable.ic_event,
			R.drawable.ic_settings, R.drawable.ic_home, R.drawable.ic_event};
	private TextView mHeader;
	private TextView mTextView;
	private static final int CONTENT_VIEW_ID = 10101010;
	private DismissOverlayView mDismissOverlay;
	private RelativeLayout container;
	int curSelect;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Intent intent_persNotif = new Intent(MainActivity.this, NotificationListenerService_persistent.class);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//DismissOverlayView element
		mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
		//mDismissOverlay.setIntroText(R.string.long_press_intro);
		mDismissOverlay.showIntroIfNecessary();

		// list header handler
		mHeader = (TextView) findViewById(R.id.header);
		listView = (WearableListView) findViewById(R.id.list);
		loadAdapter();

		startService(intent_persNotif);

		/*//Persistent notification, can be dismissed
		ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggButton);
		toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if (isChecked){
					if(isServiceRunning() == true){}
					else {
					startService(intent_persNotif);}
				}
				else{
					if(isServiceRunning()==false){}
					else {stopService(intent_persNotif);}
				}
			}
		});*/

	}

	private boolean isServiceRunning(){
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			if("com.novallc.android.wear.fhs_bellSchedule.NotificationListenerService_persistent".equals(service.service.getClassName())){
				return true;
			}
		}
		return false;
	}

	private void loadAdapter() {
		List<SettingsItems> items = new ArrayList<>();
		for (int i=0; i<mItems.length; i++){
			items.add(new SettingsItems(mItems[i]));
		}

		CustomListAdapter mAdapter = new CustomListAdapter(this, items);


		listView.setAdapter(mAdapter);
		listView.addOnScrollListener(mOnScrollListener);
		listView.setClickListener(this);
	}


	@Override
	public void onClick(WearableListView.ViewHolder viewHolder) {
		//mTextView = new TextView(R.id.result);
		//container = (RelativeLayout)findViewById(R.id.frame_layout);

		Intent intent_frag0 = new Intent(MainActivity.this, fragment0.class);
		switch (viewHolder.getPosition()) {
			case 0:
				//Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
				curSelect=0;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
			case 1:
				curSelect=1;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
			case 2:
				curSelect=2;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
			case 3:
				curSelect=3;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
			case 4:
				curSelect=4;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
			case 5:
				curSelect=5;
				intent_frag0.putExtra("curSelect", curSelect);
				startActivity(intent_frag0);
				break;
		}
	}

	@Override
	public void onTopEmptyRegionClick() {
		//Prevent NullPointerException
	}

	// title/header scrolling
	private WearableListView.OnScrollListener mOnScrollListener =
			new WearableListView.OnScrollListener() {
				@Override
				public void onAbsoluteScrollChange(int i) {
					//maintain base y-pos
					if (i > 0) {
						mHeader.setY(-i);
					}
				}

				@Override
				public void onScroll(int i) {
					// Placeholder
				}

				@Override
				public void onScrollStateChanged(int i) {
					// Placeholder
				}

				@Override
				public void onCentralPositionChanged(int i) {
					// Placeholder
				}
			};
}