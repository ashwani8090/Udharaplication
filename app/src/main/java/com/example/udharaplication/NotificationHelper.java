package com.example.udharaplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

class NotificationHelper {

    private Context mContext;
    private static final String NOTIFICATION_CHANNEL_ID = "10001";

    NotificationHelper(Context context) {
        mContext = context;
    }

    void createNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Udhar";

            String description = "Track your udhar account";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);

            channel.setDescription(description);


            // Register the channel with the system; you can't change the importance

            // or other notification behaviors after this

            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);


        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)

                .setSmallIcon(R.drawable.udhar)


                .setContentTitle("Udhar")

                .setContentText("Track your udhar account")

                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setColor(Color.YELLOW)

                .setVibrate(new long[]{100, 100}).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);



        Intent intent = new Intent(mContext, SplashPage.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        builder.setContentIntent(pendingIntent);



        // notificationId is a unique int for each notification that you must define

        notificationManager.notify(1, builder.build());

    }

    }
