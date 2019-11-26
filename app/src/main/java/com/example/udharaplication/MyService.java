package com.example.udharaplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyService extends Service {
    BroadcastReceiver broadcastReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

           broadcast();
           return Service.START_NOT_STICKY;

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public void broadcast(){
        try{
            broadcastReceiver=new InternetBroadcast(this);
            IntentFilter intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(broadcastReceiver,intentFilter);
        }
        catch (Exception e){


        }

    }

}
