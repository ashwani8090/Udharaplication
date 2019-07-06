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
    public static final String PK = "PK";


    private Context context;

    public DatabaseItems(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + TABLE_NAME + "( ID  PRIMARY KEY ,DATES,ITEM_NAME,PHONE,AMOUNT INTEGER,DESCRIPTION" + ",PK , FOREIGN KEY(PK) REFERENCES Contacts(PK) )");

    }


    public boolean InsertDates(String Id,String dates, String itemnames,
                                 String phone,Integer amount,  String description,String Pk) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,Id);
        contentValues.put(DATES, dates);
        contentValues.put(ITEM_NAME, itemnames);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PHONE, phone);
        contentValues.put(AMOUNT, amount);
        contentValues.put(PK,Pk);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return true;
        else return true;


    }


    public boolean InsertAfterDeleteItem(String Id,String dates, String itemnames,
                               String phone,Integer amount,  String description,String pk) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, Id);
        contentValues.put(DATES, dates);
        contentValues.put(ITEM_NAME, itemnames);
        contentValues.put(DESCRIPTION, description);
        contentValues.put(PHONE, phone);
        contentValues.put(AMOUNT, amount);
        contentValues.put(PK,pk);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return true;
        else return true;


    }




    public Cursor getAllDataDate(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where DATES=?", new String[]{date});
        return cursor;
    }

    public Cursor getUniqueAll(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where ID=?", new String[]{ID});
        return cursor;
    }

    public Cursor AllDataFirabaseItems() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from " + TABLE_NAME,null);
        return cursor;

    }



    public Integer deleteData(String row) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{row});

    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }


    public boolean upDate(String ID,Integer amount,String des ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AMOUNT,amount);
        contentValues.put(DESCRIPTION,des);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{ID});
        return true;

    }



    public  boolean UpDatePhone(String pk,String newphone){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PHONE,newphone);
        sqLiteDatabase.update(TABLE_NAME,contentValues, "PK=?",new String[]{pk});
        return  true;

    }



    public Cursor GetAllwithPhone(String pk) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where PK=?", new String[]{pk});
        return cursor;
    }


    public Cursor GetItemWithPhone(String Item){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select PHONE from " + TABLE_NAME + " where ITEM_NAME=?",
                new String[]{Item});
        return cursor;
    }
    public Cursor GetDesWithPhone(String des){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select PHONE from " + TABLE_NAME + " where DESCRIPTION=?",
                new String[]{des});
        return cursor;
    }



    public Integer DeleteAllofPhone(String PK){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "PK=?", new String[]{PK});


    }




}
