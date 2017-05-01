package com.alarmmangerservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by sotra on 4/29/2017.
 */
public class reminder extends IntentService {


    public reminder() {
        super("alarm service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("intent_recive" ,  intent.getExtras().getString("title"));
        send_declearation_notfication(intent.getExtras().getString("title"));

    }

    private void send_declearation_notfication(String tile  ){
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(ID+"")
                .setAutoCancel(true)
                .setContentText(tile)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setPriority( NotificationCompat.PRIORITY_MAX);
        final Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Calendar.getInstance().getTimeInMillis() /* ID of notification */, notification);

    }
    int ID = 0 ;

}
