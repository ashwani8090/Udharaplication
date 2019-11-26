package com.example.udharaplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Date;

public class SplashPage extends AppCompatActivity {


    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);


        sharedpref();


        thread t = new thread();
        Thread th = new Thread(t);
        th.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            startService(new Intent(this, MyService.class));
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void sharedpref(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("FirstTime", false)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,8);
            calendar.set(Calendar.MINUTE,0);
            if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
            Intent intent = new Intent(getApplicationContext(),NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("FirstTime", true);
            editor.apply();
        }
    }

    class thread implements Runnable {
        int i = 0;

        @Override
        public void run() {

            for (; i < 100; ) {

                i = i + 3;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i > 98) {

                    Intent i = new Intent(SplashPage.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            }


        }


    }


}