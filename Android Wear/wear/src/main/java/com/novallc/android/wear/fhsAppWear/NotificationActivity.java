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
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends Activity{
    private static final String TAG = NotificationActivity.class.getSimpleName();

    public static final String EXTRA_TITLE = "text";
    public static final String EXTRA_IMAGE = "image";

    private ImageView mImageView;
    private TextView mTextView = (TextView) findViewById(R.id.text_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //mImageView = (ImageView) findViewById(R.id.image_view);
        mTextView = (TextView) findViewById(R.id.text_view);

        Intent intent = getIntent();
        if (intent != null) {
            mTextView.setText(EXTRA_TITLE);

           // final Asset asset = intent.getParcelableExtra(EXTRA_IMAGE);
        }
    }
}
