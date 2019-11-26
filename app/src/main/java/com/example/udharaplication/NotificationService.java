package com.example.udharaplication;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

public class NotificationService extends Service {

    private static final String CHANNEL_ID = "1";
    private StringBuffer details = new StringBuffer();
    private Context context;
    private HashSet<String> arrayList = new HashSet<>();
    private HashMap<String,String> arrayList2 = new HashMap<>();



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        context = getApplicationContext();
        GetLeftContact();
        GetAllName();




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Udhar";

            String description = "Track your udhar account";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance

            // or other notification behaviors after this

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);


        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)

                .setSmallIcon(R.drawable.udhar)


                .setContentTitle("Udhar")

                .setContentText("Track your udhar account" )

                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setColor(Color.YELLOW)

                .setVibrate(new long[]{100, 100}).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();


        inboxStyle.setBigContentTitle("Udhar");
        for (String c : arrayList2.values()) {

            inboxStyle.addLine(c);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        builder.setStyle(inboxStyle);

        // notificationId is a unique int for each notification that you must define

        notificationManager.notify(1, builder.build());



        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {


        return super.stopService(name);
    }

    public void GetLeftContact() {
        arrayList.clear();
        DatabaseDates databaseDates = new DatabaseDates(context);
        Cursor cursor = databaseDates.NotifyLeft();
        if (cursor.getCount() == 0) {
            return;
        } else {

            while (cursor.moveToNext()) {
                ConstructorDate constructorDate = new ConstructorDate(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7));
                arrayList.add(constructorDate.getPHONE());

            }


        }


    }


    private void GetAllName() {
        arrayList2.clear();
        Cursor cursor1;
        DataBaseHelperClass dataBaseHelperClass = new DataBaseHelperClass(context);
           cursor1 = dataBaseHelperClass.getAllData();
            if (cursor1.getCount() == 0) {
                return;
            } else {

                while (cursor1.moveToNext()) {


                    ContactConstructorlList constructorlList = new
                            ContactConstructorlList(cursor1.getString(0),
                            cursor1.getString(1),
                            cursor1.getString(2),
                            cursor1.getString(3)
                            );


                    if(arrayList.contains(constructorlList.getPhone())) {
                        arrayList2.put(constructorlList.getPhone(), constructorlList.getName());
                    }

                }


            }

        }








}
