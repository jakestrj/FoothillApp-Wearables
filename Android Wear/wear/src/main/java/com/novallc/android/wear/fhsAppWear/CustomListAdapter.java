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

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class CustomListAdapter extends WearableListView.Adapter {
	private final List<SettingsItems> items;
	private final LayoutInflater mInflater;


	public CustomListAdapter(Context context, List<SettingsItems> items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
	}

	@Override
	public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int position) {
		ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
		final SettingsItems item = items.get(position);
		TextView textView = itemViewHolder.mItemTextView;
		textView.setText(item.title);
		//CircledImageView mImageView = itemViewHolder.mCircledImageView;
		//mImageView.setImageResource(item.iconRes);

	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	private static class ItemViewHolder extends WearableListView.ViewHolder {
		private TextView mItemTextView;
		private CircledImageView mCircledImageView;

		public ItemViewHolder(View itemView) {
			super(itemView);
			mItemTextView = (TextView) itemView.findViewById(R.id.text);
			//mCircledImageView = (CircledImageView) itemView.findViewById(R.id.image);
		}
	}
}