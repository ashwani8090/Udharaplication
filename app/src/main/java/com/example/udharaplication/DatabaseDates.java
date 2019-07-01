package com.example.udharaplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDates extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Dates.db";
    public static final String TABLE_NAME="DATES";
    public static final String DATE="DATE";
    public static final String PHONE="PHONE";

    public static final String RECIEVED="RECIEVED";
    public static final String LEFTP="LEFTP";
    public static final String PAID="PAID";
    public static final String TOTAL="TOTAL";





    public DatabaseDates(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+"( DATE primary key,PHONE,RECIEVED INTEGER, LEFTP INTEGER,TOTAL INTEGER,PAID ,foreign key(PHONE) references Contacts(PHONE))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);
    }


    public boolean insertDate(String Date,String Phone){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DATE,Date);
        contentValues.put(PHONE,Phone);


        long result=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return  true;
        else
            return  false;



    }


    public Integer DeleteDate(String row){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"DATE=?",new String[]{row});

    }


    public Cursor GetALlDate(String phone){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(" select * from "+TABLE_NAME+" where PHONE=?",new String[]{phone});
        return  cursor;

    }

    public Cursor GetUnique(String date){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(" select * from "+TABLE_NAME+" where DATE=?",new String[]{date});
        return  cursor;

    }





    public  boolean upDateTotal(String date,Integer total){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(TOTAL,total);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"DATE=?",new String[]{date});
        return  true;

    }


    public  boolean upDateRecieve(String date,Integer credit){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(RECIEVED,credit);
        sqLiteDatabase.update(TABLE_NAME,contentValues, "DATE=?",new String[]{date});
        return  true;

    }


    public  boolean UpdateLeft(String date,Integer left){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(LEFTP,left);
        sqLiteDatabase.update(TABLE_NAME,contentValues, "DATE=?",new String[]{date});
        return  true;

    }




    public boolean InsertAfterDelete(String Date,String Phone,Integer Received,Integer Left,Integer Total,String Paid){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(DATE,Date);
        contentValues.put(PHONE,Phone);

        contentValues.put(RECIEVED,Received);
        contentValues.put(LEFTP,Left);
        contentValues.put(TOTAL,Total);
        contentValues.put(PAID,Paid);


        long result=sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return  true;
        else
            return  false;



    }

}
