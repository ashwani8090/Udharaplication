package com.example.udharaplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashPage extends AppCompatActivity {

    Handler handler;
    ProgressBar progressBar;

    int p=0;

    @SuppressLint("HandlerLeak")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);

        thread t=new thread();
        Thread th=new Thread(t);

        th.start();



    }


    class thread implements  Runnable{
        int i=0;

        @Override
        public void run() {
            progressBar = findViewById(R.id.progressBar);

            for ( ; i < 100; ) {
                progressBar.setProgress(i);
                i=i+3;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



                if(i>98){

                    Intent i=new Intent(SplashPage.this,LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }

            }



        }


    }

}