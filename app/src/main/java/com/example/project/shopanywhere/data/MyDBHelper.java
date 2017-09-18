package com.example.project.shopanywhere.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopanywhere.db";
    private static final int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_TABLE_TASKS = String.format("CREATE TABLE IF NOT EXISTS %s"
            +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, UNIQUE ( %s ) ON CONFLICT REPLACE)",
            DatabaseContract.TABLE_STORE,
            DatabaseContract.StoreColumns._ID,
            DatabaseContract.StoreColumns.DESCRIPTION,
            DatabaseContract.StoreColumns.LOCATION,
            DatabaseContract.StoreColumns.PHONENO,
            DatabaseContract.StoreColumns.STORENAME,
            DatabaseContract.StoreColumns.PICTURE,
            DatabaseContract.StoreColumns.PICTURE
    );
    private static final String SQL_CREATE_TABLE_ITEM = String.format("CREATE TABLE IF NOT EXISTS %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT,  UNIQUE ( %s ) ON CONFLICT REPLACE)",
            DatabaseContract.TABLE_ITEM,
            DatabaseContract.ItemColumns._ID,
            DatabaseContract.ItemColumns.ITEMNAME,
            DatabaseContract.ItemColumns.ITEMSTORENAME,
            DatabaseContract.ItemColumns.COLOR,
            DatabaseContract.ItemColumns.PRICE,
            DatabaseContract.ItemColumns.ITEMSPICTURE,
            DatabaseContract.ItemColumns.ITEMSPICTURE
    );
    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE IF NOT EXISTS %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT,  UNIQUE ( %s ) ON CONFLICT REPLACE)",
            DatabaseContract.TABLE_USER,
            DatabaseContract.UserColumns._ID,
            DatabaseContract.UserColumns.USERNAME,
            DatabaseContract.UserColumns.USERPASSWORD,
            DatabaseContract.UserColumns.USERID,
            DatabaseContract.UserColumns.USERID
    );


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);
        db.execSQL(SQL_CREATE_TABLE_ITEM);
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_USER);
        onCreate(db);
    }
}
