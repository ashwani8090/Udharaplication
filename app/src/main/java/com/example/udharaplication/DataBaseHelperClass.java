package com.example.udharaplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperClass extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Contact.db";
    private static final String TABLE_NAME="CONTACTS";
    private static final String PHONE_NO="PHONE";
    private static final String NAME_OF_CONTACT="NAME";




    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (PHONE PRIMARY KEY,NAME)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(db);

    }




    public boolean insertData(String phone,String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PHONE_NO,phone);
        contentValues.put(NAME_OF_CONTACT,name);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1) return true;
        else return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        return  cursor;
    }


    public Integer deleteData(String row){
        SQLiteDatabase db=this.getWritableDatabase();
          return db.delete(TABLE_NAME, "PHONE=?",new String[]{row});


    }


    public  boolean UpDatePhone(String phone,String newphone){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PHONE_NO,newphone);
        sqLiteDatabase.update(TABLE_NAME,contentValues, "PHONE=?",new String[]{phone});
        return  true;

    }



}
