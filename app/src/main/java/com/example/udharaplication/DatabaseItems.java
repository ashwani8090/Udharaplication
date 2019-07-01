package com.example.udharaplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseItems extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DatesItem.db";
    public static final String TABLE_NAME = "ITEMS";
    public static final String ID = "ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String DATES = "DATES";
    public static final String AMOUNT = "AMOUNT";
    public static final String PHONE = "PHONE";
    public static final String DESCRIPTION = "DESCRIPTION";


    private Context context;

    public DatabaseItems(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT,DATES,ITEM_NAME,PHONE,AMOUNT INTEGER,DESCRIPTION, FOREIGN KEY(PHONE) REFERENCES Contacts(Phone) )");

    }


    public boolean InsertDates(String dates, String itemnames,
                                 String phone,Integer amount,  String description) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATES, dates);
        contentValues.put(ITEM_NAME, itemnames);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PHONE, phone);
        contentValues.put(AMOUNT, amount);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return true;
        else return true;


    }


    public boolean InsertAfterDeleteItem(Integer Id,String dates, String itemnames,
                               String phone,Integer amount,  String description) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, Id);
        contentValues.put(DATES, dates);
        contentValues.put(ITEM_NAME, itemnames);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PHONE, phone);
        contentValues.put(AMOUNT, amount);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return true;
        else return true;


    }




    public Cursor getAllDataDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where DATES=?", new String[]{date});
        return cursor;
    }

    public Cursor getUniqueAll(Integer ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where ID=?", new String[]{String.valueOf(ID)});
        return cursor;
    }




    public Integer deleteData(Integer row) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(row)});

    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }


    public boolean upDate(Integer ID,Integer amount ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AMOUNT,amount);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{String.valueOf(ID)});
        return true;

    }

}