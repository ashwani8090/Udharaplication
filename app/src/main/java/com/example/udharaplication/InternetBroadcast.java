package com.example.udharaplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;


public class InternetBroadcast extends BroadcastReceiver {

    private FirebaseAuth firebaseAuth;
    private Context context;
    private DatabaseItems databaseItems;
    private DatabaseDates databaseDates;
    private DataBaseHelperClass dataBaseHelperClass;
    private List<ConstructorItems> itemsList = new ArrayList<>();
    private List<ConstructorDate> datearraylist = new ArrayList<>();
    private List<ContactConstructorlList> arraylist = new ArrayList<>();
    private DatabaseReference firebaseDatabaseContacts;
    private DatabaseReference firebaseDatabaseItems;
    private DatabaseReference firebaseDatabaseDates;

    public InternetBroadcast() {
    }

    public InternetBroadcast(Context context) {
        this.context = context;
        databaseItems = new DatabaseItems(context);
        databaseDates = new DatabaseDates(context);
        dataBaseHelperClass = new DataBaseHelperClass(context);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabaseContacts = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("CONTACTS");
        firebaseDatabaseDates = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("DATES");
        firebaseDatabaseItems = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("ITEMS");


        if (intent.getAction().equals("com.journaldev.broadcastreceiver.SOME_ACTION"))
            Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_LONG).show();

        else {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {

                try {

                    BackUpTheData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {


            }
        }
    }


    public void BackUpTheData() {


        Contacts();
        DatesTable();
        ItemsTable();


        for (ContactConstructorlList constructorlList : arraylist) {

            firebaseDatabaseContacts.child("" + constructorlList.getPk()).setValue(constructorlList);
        }


        for (ConstructorDate constructorDate : datearraylist) {

            firebaseDatabaseDates.child("" + constructorDate.getDATE()).setValue(constructorDate);
        }

        for (ConstructorItems constructorItems : itemsList) {

            firebaseDatabaseItems.child("" + constructorItems.getID()).setValue(constructorItems);

        }


    }


    public void ItemsTable() {

        itemsList.clear();
        Cursor cursor = databaseItems.AllDataFirabaseItems();

        if (cursor.getCount() == 0) {

            return;
        } else {

            while (cursor.moveToNext()) {

                ConstructorItems constructorItems = new ConstructorItems(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6));
                itemsList.add(constructorItems);
            }


        }


    }

    public void DatesTable() {

        datearraylist.clear();
        Cursor cursor1 = databaseDates.AllDataFirabaseDates();
        if (cursor1.getCount() == 0) {
            return;
        } else {

            while (cursor1.moveToNext()) {

                ConstructorDate constructorDate = new ConstructorDate(cursor1.getString(0),
                        cursor1.getString(1),
                        cursor1.getInt(2),
                        cursor1.getInt(3),
                        cursor1.getInt(4),
                        cursor1.getString(5),
                        cursor1.getString(6),
                        cursor1.getString(7));

                datearraylist.add(constructorDate);
            }


        }


    }


    public void Contacts() {

        arraylist.clear();
        Cursor cursor1 = dataBaseHelperClass.getAllData();
        if (cursor1.getCount() == 0) {
            return;
        } else {

            while (cursor1.moveToNext()) {

                ContactConstructorlList constructorDate = new
                        ContactConstructorlList(cursor1.getString(0),
                        cursor1.getString(1),
                        cursor1.getString(2),
                        cursor1.getString(3)
                );

                arraylist.add(constructorDate);
            }


        }


    }


}