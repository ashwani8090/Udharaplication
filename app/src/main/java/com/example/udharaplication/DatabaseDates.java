package com.example.udharaplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDates extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Dates.db";
    private static final String TABLE_NAME = "DATES";
    private static final String DATE = "DATE";
    private static final String PHONE = "PHONE";

    private static final String RECIEVED = "RECIEVED";
    private static final String LEFTP = "LEFTP";
    private static final String PAID = "PAID";
    private static final String TOTAL = "TOTAL";
    private static final String PK="PK";
    private static final String TRANSACTIONS="TRANSACTIONS";



    public DatabaseDates(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "( DATE primary key,PHONE,RECIEVED INTEGER, LEFTP INTEGER,TOTAL INTEGER,PAID ,PK,TRANSACTIONS ,foreign key(PK) references Contacts(PK))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }





    public boolean insertDate(String Date, String Phone,String Pk) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE, Date);
        contentValues.put(PHONE, Phone);
        contentValues.put(PK,Pk);


        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return true;
        else
            return false;


    }


    public Integer DeleteDate(String row) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "DATE=?", new String[]{row});

    }


    public Cursor GetALlDate(String pk) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where PK=?", new String[]{pk});
        return cursor;

    }

    public Cursor GetUnique(String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where DATE=?", new String[]{date});
        return cursor;

    }

    public Cursor AllDataFirabaseDates() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME,null);
        return cursor;

    }

    public Cursor NotifyLeft(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME + " where LEFTP!=0",null);
        return cursor;

    }


    public boolean upDateTotal(String date, Integer total) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOTAL, total);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "DATE=?", new String[]{date});
        return true;

    }


    public boolean upDateRecieve(String date, Integer credit) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECIEVED, credit);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "DATE=?", new String[]{date});
        return true;

    }


    public boolean UpdateLeft(String date, Integer left) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LEFTP, left);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "DATE=?", new String[]{date});
        return true;

    }


    public boolean InsertAfterDelete(String Date, String Phone, Integer Received, Integer Left, Integer Total, String Paid,String pk,String transaction) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DATE, Date);
        contentValues.put(PHONE, Phone);

        contentValues.put(TRANSACTIONS,transaction);
        contentValues.put(RECIEVED, Received);
        contentValues.put(LEFTP, Left);
        contentValues.put(TOTAL, Total);
        contentValues.put(PAID, Paid);
        contentValues.put(PK,pk);


        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return true;
        else
            return false;


    }


    public boolean UpDatePhone(String Pk, String newphone) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONE, newphone);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "PK=?", new String[]{Pk});
        return true;

    }








    public boolean UpDatePaid(String date, String paid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PAID, paid);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "DATE=?", new String[]{date});
        return true;
    }


    public boolean UpdateTransaction(String date, String transaction) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TRANSACTIONS, transaction);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "DATE=?", new String[]{date});
        return true;
    }



    public Integer DeleteAllofPhone(String pk){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "PK=?", new String[]{pk});


    }





}
