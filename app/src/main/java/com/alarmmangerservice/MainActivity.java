package com.alarmmangerservice;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 262 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // prepare trigger time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 19);

        // prepare intent to send
        Intent intent = new Intent(MainActivity.this,reminder.class);
        intent.putExtra("title","lecture io");
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this,0,intent ,PendingIntent.FLAG_UPDATE_CURRENT);

        // get alarm service
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // make repating alarm ( type, trigger , interval  , pending intent )
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY*7 ,pendingIntent);


        //////////////////////
        /*
        downloading file check permeation  first
         */
        if (check_stoarge_permtion())
        download_file("downloading file " , "file name test " ,"" +
                "https://dl.genymotion.com/releases/genymotion-2.9.0/genymotion-2.9.0-vbox.exe");
////////////////////////////////
        // download photo in imageView with circle transform
        ImageView profile_img = (ImageView) findViewById(R.id.profile);
        Glide.with(getApplicationContext())
                .load("https://image.freepik.com/free-vector/social-media-icons_23-2147501890.jpg")
                .centerCrop()
                .transform(new CircleTransform(getApplicationContext()))
                .into(profile_img);

    }





    private void download_file(String title , String desc , String url ) {

        Log.e("main_activity", "d file : " +url);

        // parse url to uri
        Uri uri = Uri.parse(url);

        // make download req
        DownloadManager.Request req=new DownloadManager.Request(uri);

        // set downloading options
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                // title here will be downloaded file name
                .setTitle(title)
                // description for downloading progress
                .setDescription(desc)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);
        // get download service
        DownloadManager downloadManager =(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        // add  request to downloading queue
        downloadManager.enqueue(req);
    }

    private boolean check_stoarge_permtion() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STORAGE);
            return false;
        }
        return true ;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "permisson needed", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
