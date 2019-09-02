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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotificationListenerService_persistent extends Service{
    Notification.Builder notificationBuilder;
    NotificationManager mNotificationManager;
    private static final String TAG = NotificationListenerService_persistent.class.getSimpleName();
    private static final int NOTIFICATION_ID = 100;
    private Date date;
    //Context context = getApplicationContext();
//    Drawable drawable = this.getResources().getDrawable(R.drawable.framedback);
//    Bitmap notifBack = ((BitmapDrawable) drawable).getBitmap();
//    Drawable framedBackDraw = new BitmapDrawable(this.getResources(), notifBack);
    //curPer = 0=P0; 1=P1; 2=P2; 3=break; 4=P3; 5=P4; 6=P5; 7=lunch; 8=P6
    private final Handler handler = new Handler();
    //End times and comparison constants
    private int c_curDay;
    private int c_schState;
    private int c_curPer;
    private final long DELAY = 1*60*1000;

    private static final String inputFormat="HH:mm";
    private String endsReg[]={"07:40","08:41","09:42","09:55","11:00","12:01","13:02","13:32","14:33"};
    private String startsReg[]={"06:45","07:45","08:46","09:42","10:00","11:05","12:06","13:02","13:37"};
    private String endTut[]={"07:40", "08:36", "09:32", "09:44", "10:41", "11:15", "12:11", "13:07", "13:37", "14:33"};
    private String startTut[]={"06:45", "07:45", "08:41", "09:32", "09:49", "10:46", "11:20", "12:16", "13:07", "13:42"};
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    @Override
    public void onCreate() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                compareDates();
            }
        }, DELAY);
        onDataChanged();
    }

    //Later access with foothill standalone app
    //@Override
    public void onDataChanged() {

                    // Get the data out of the event
                    /*DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    final String title = dataMapItem.getDataMap().getString(Constants.KEY_TITLE);
                    Asset asset = dataMapItem.getDataMap().getAsset(Constants.KEY_IMAGE);*/
                    final String title = "test";




                    // Build the intent to display our custom notification
                    Intent notificationIntent = new Intent(this, NotificationActivity.class);
                    notificationIntent.putExtra(NotificationActivity.EXTRA_TITLE, title);
                    //notificationIntent.putExtra(NotificationActivity.EXTRA_IMAGE, asset);
                    PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                            this,
                            0,
                            notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    // Create the ongoing notification

                    // Use for multipage notification
//                    Notification secondPageNotification =
//                            new Notification.Builder(this)
//                                    .extend(new Notification.WearableExtender()
//                                            .setDisplayIntent(notificationPendingIntent))
//                                    .build();

                   notificationBuilder =
                            new Notification.Builder(this)
                                    .setOngoing(true)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.framedback))
                                    .setContentText("Loading.........");
//                                    .extend(new Notification.WearableExtender()
//                                                    .setDisplayIntent(notificationPendingIntent)
////                                            .addPage(secondPageNotification)
//                                    );
                    mNotificationManager = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
                    mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


                }

    private void compareDates(){
        //Calender/Time handler
        Calendar calendar = Calendar.getInstance();
        c_curDay = calendar.get(Calendar.DAY_OF_WEEK);

        //0=reg; 1=tut; 2=misc
        //TODO Add features for dates, ie. finals, min day, late start

        switch(c_curDay){
            case Calendar.MONDAY:
            case Calendar.WEDNESDAY:
            case Calendar.FRIDAY:
                Log.d("COMPARE", "1");
                c_schState=0;
                break;
            case Calendar.TUESDAY:
            case Calendar.THURSDAY:
               Log.d("COMPARE", "1_0");
                c_schState=1;
                break;
            default:
               Log.d("COMPARE", "1_1");
                c_schState=2;
                break;
        }
        //c_schState=1;
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);

//        dateCMP1=parseDate(startsReg[0]);
//        dateCMP2=parseDate(endsReg[0]);
        date=parseDate(hour+":"+minute);//(hour+":"+minute)
        //TODO Compress first timeDifference update to reduce length
        switch(c_schState){
            case 0:
                if(date.before(parseDate(endsReg[0]))&&date.after(parseDate(startsReg[0]))) {
                    c_curPer=0;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[1]))&&date.after(parseDate(startsReg[1]))) {
                    c_curPer=1;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[2]))&&date.after(parseDate(startsReg[2]))) {
                    c_curPer=2;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[3]))&&date.after(parseDate(startsReg[3]))) {
                    c_curPer=3;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[4]))&&date.after(parseDate(startsReg[4]))) {
                    c_curPer=4;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[5]))&&date.after(parseDate(startsReg[5]))) {
                    c_curPer=5;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[6]))&&date.after(parseDate(startsReg[6]))) {
                    c_curPer=6;checkRemainingTimeReg();
                }
                else if(date.before(parseDate(endsReg[7]))&&date.after(parseDate(startsReg[7]))) {
                    c_curPer=7;checkRemainingTimeReg();
                }else
                    updateContentText("", "No Sessions");

                break;
            case 1:
                if(date.before(parseDate(endTut[0]))&&date.after(parseDate(startTut[0]))) {
                    c_curPer=0;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[1]))&&date.after(parseDate(startTut[1]))) {
                    c_curPer=1;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[2]))&&date.after(parseDate(startTut[2]))) {
                    c_curPer=2;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[3]))&&date.after(parseDate(startTut[3]))) {
                    c_curPer=3;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[4]))&&date.after(parseDate(startTut[4]))) {
                    c_curPer=4;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[5]))&&date.after(parseDate(startTut[5]))) {
                    c_curPer=5;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[6]))&&date.after(parseDate(startTut[6]))) {
                    c_curPer=6;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[7]))&&date.after(parseDate(startTut[7]))) {
                    c_curPer=7;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[8]))&&date.after(parseDate(startTut[8]))) {
                    c_curPer=8;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[9]))&&date.after(parseDate(startTut[9]))) {
                    c_curPer=9;checkRemainingTimeTut();
                }
                else if(date.before(parseDate(endTut[10]))&&date.after(parseDate(startTut[10]))) {
                    c_curPer=10;checkRemainingTimeTut();
                }else
                    updateContentText("", "No Sessions");

                break;
            case 2:
                updateContentText("", "No Sessions");
                break;
        }
        /*if(date.before(dateCMP2)&&date.after(dateCMP1)){
            Log.d("COMPARE", "SUCCESS");
            updateContentText("test");

        }*/
    }

    private Date parseDate(String date){
        try{
            return inputParser.parse(date);
        }catch (java.text.ParseException e){
            return new Date(0);
        }
    }

    private boolean updateContentText(String timeString, String updateString){
        notificationBuilder.setContentText(Html.fromHtml("<b>"+timeString+"</b> " + updateString));
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        return true;
    }

    private boolean checkRemainingTimeReg(){
        final int _curPer=c_curPer;
        //Has to be re-instantiated, or decl. final
        long timeDifference;
        timeDifference = (parseDate(endsReg[_curPer]).getTime() / 60000 - date.getTime() / 60000);
        Log.d("COMPARE", Long.toString(timeDifference));
        if(timeDifference<10){
            updateContentText("00:0" + Long.toString(timeDifference), " Remaining in Session");
        }else if(timeDifference>60&&timeDifference<70){
            updateContentText("01:0" + Long.toString(timeDifference), " Remaining in Session");
        }else if(timeDifference>70){
            updateContentText("01:" + Long.toString(timeDifference), " Remaining in Session");
        }else
            updateContentText("00:" + Long.toString(timeDifference), " Remaining in Session");

        return true;
    }

    private boolean checkRemainingTimeTut(){
        final int _curPer=c_curPer;
        //Has to be re-instantiated, or decl. final
        long timeDifference;
        timeDifference = (parseDate(endTut[_curPer]).getTime() / 60000 - date.getTime() / 60000);
        Log.d("COMPARE", Long.toString(timeDifference));
        if(timeDifference<10){
            updateContentText("00:0" + Long.toString(timeDifference), " Remaining in Session");
        }else if(timeDifference>60&&timeDifference<70){
            updateContentText("01:0" + Long.toString(timeDifference), " Remaining in Session");
        }else if(timeDifference>70){
            updateContentText("01:" + Long.toString(timeDifference), " Remaining in Session");
        }else
            updateContentText("00:" + Long.toString(timeDifference), " Remaining in Session");

        return true;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        //Log.i(TAG, "Service onBind");
        return null;
    }
//    @Override
//    public void onMessageReceived(MessageEvent messageEvent) {
//        if (messageEvent.getPath().equals(Constants.PATH_DISMISS)) {
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.cancel(NOTIFICATION_ID);
//        }
//    }
}
