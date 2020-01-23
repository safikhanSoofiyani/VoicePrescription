package com.example.voiceprescription;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper  extends SQLiteOpenHelper {
    private static final String dbname = "mydb";
    private static final int version = 1;

    public MyHelper(Context context)
    {
        super(context,dbname,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL = "CREATE TABLE PRESCRIPTION (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PATIENTNAME TEXT, FILENAME TEXT)";
        sqLiteDatabase.execSQL(SQL);

        String SQL2 = "CREATE TABLE PATIENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PATIENTNAME TEXT, PDATE TEXT, PTIME TEXT)";
        sqLiteDatabase.execSQL(SQL2);

    }

    public void insertPrescript(String Pname,String Fname,SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PATIENTNAME",Pname);
        contentValues.put("FILENAME",Fname);
        db.insert("PRESCRIPTION",null,contentValues);
    }

    public void insertPatient(String Pname, String date,String time, SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PATIENTNAME",Pname);
        contentValues.put("PDATE",date);
        contentValues.put("PTIME",time);
        db.insert("PATIENT",null,contentValues);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
