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
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;


public class fragment0 extends Activity{

    private GestureDetectorCompat mDetector;

    //reg-tut-late-assem-min-final
    private String[] schedule_strings = new String[]{
        "P0..................6:45 - 7:40am\nP1..................7:45 - 8:41am\nP2..................8:46 - 9:42am\nBreak..................9:42 - 9:55am\nP3..................10:00 - 11:00am\nP4..................11:05 - 12:01pm\nP5..................12:06 - 1:02pm\nLunch...............1:02 - 1:32pm\nP6...............1:37 - 2:33pm",
        "P0..................6:45 - 7:40am\nP1..................7:45 - 8:36am\nP2..................8:41 - 9:32am\nBreak..................9:32 - 9:44am\nP3..................9:49 - 10:41am\nTutorial..................10:46 - 11:15\nP4..................11:20 - 12:11pm\nP5..................12:16 - 1:07pm\nLunch...............1:07 - 1:37pm\nP6...............1:42 - 2:33pm",
        "P0..................8:40 - 9:20am\nP1..................9:25 - 10:05am\nP2..................10:10 - 10:50am\nBreak..................10:50 - 11:00am\nP3..................11:05 - 11:48am\nP4..................11:53 - 12:33pm\nP5..................12:38 - 1:18pm\nLunch...............1:18 - 1:48pm\nP6...............1:53 - 2:33pm",
        "P0..................6:45 - 7:40am\nP1..................7:45 - 8:32am\nP2 (A1)...............8:37 - 9:27am\nP2 (A2)...............9:37 - 10:27am\nBreak..................10:27 - 10:39am\nP3..................10:44 - 11:31am\nP4..................11:36 - 12:23pm\nP5..................12:28 - 1:14pm\nLunch...............1:14 - 1:44pm\nP6...............1:49 - 2:35pm",
        "P0..................7:02 - 7:40am\nP1..................7:45 - 8:22am\nP2..................8:27 - 9:04am\nP3..................9:09 - 9:46am\nBreak..................9:46 - 9:58am\nP4..................10:03 - 10:40pm\nP5..................10:45 - 11:22pm\nP6............11:27 - 12:05pm",
        "P1/2/3..............7:45 - 9:45am\nBreak..................9:45 - 9:55am\nP4/5/6-0..............10:00 - 12:00am"};



//    public fragment0(){
//        //Return public constructor
//    }
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag0);
        //Log.d("GESTURE", "fragment0 CREATION");
        mDetector = new GestureDetectorCompat(this, new GestureListener());
        Bundle b = getIntent().getExtras();
        TextView primText = (TextView)findViewById(R.id.result);
        TextView headerText = (TextView)findViewById(R.id.header_frag);
        switch(b.getInt("curSelect")){
            case 0:
                primText.setText(schedule_strings[0]);
                headerText.setText("Regular Bell Schedule");
                break;
            case 1:
                primText.setText(schedule_strings[1]);
                headerText.setText("Tutorial Bell Schedule");
                break;
            case 2:
                primText.setText(schedule_strings[2]);
                headerText.setText("Late Start Schedule");
                break;
            case 3:
                primText.setText(schedule_strings[3]);
                headerText.setText("Assembly Schedule");
                break;
            case 4:
                primText.setText(schedule_strings[4]);
                headerText.setText("Minimum Day Schedule");
                break;
            case 5:
                primText.setText(schedule_strings[5]);
                headerText.setText("Finals Bell Schedule");
                break;

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener{
        //Handle 'swipe left' action only using onFling

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){

            if(e1.getX() < e2.getX()){
                //Logging for debugging
//                Log.d("GESTURE", "Left to Right swipe: "+ e1.getX() + " - " + e2.getX());
//                Log.d("SPEED", String.valueOf(velocityX) + " pixels/second");

                //Switch activity
                Intent intent = new Intent(fragment0.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }



//    @Override
//    public boolean onDown(MotionEvent e) {
//        Log.d("GESTURE", " onDown");
//        return true;
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        Log.d("GESTURE", " onSingleTapUp");
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//        Log.d("GESTURE", " onShowPress");
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//        Log.d("GESTURE", " onLongPress");
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//
//        Log.d("GESTURE", " onScroll");
//        if (e1.getY() < e2.getY()){
//            Log.d("GESTURE", " Scroll Down");
//        }
//        if(e1.getY() > e2.getY()){
//            Log.d("GESTURE", " Scroll Up");
//        }
//        return true;
//    }


}
