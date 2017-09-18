package com.example.project.shopanywhere.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.project.shopanywhere.data.DatabaseContract.ItemColumns.COLOR;
import static com.example.project.shopanywhere.data.DatabaseContract.ItemColumns.ITEMNAME;
import static com.example.project.shopanywhere.data.DatabaseContract.ItemColumns.ITEMSPICTURE;
import static com.example.project.shopanywhere.data.DatabaseContract.ItemColumns.ITEMSTORENAME;
import static com.example.project.shopanywhere.data.DatabaseContract.ItemColumns.PRICE;
import static com.example.project.shopanywhere.data.DatabaseContract.StoreColumns.DESCRIPTION;
import static com.example.project.shopanywhere.data.DatabaseContract.StoreColumns.LOCATION;
import static com.example.project.shopanywhere.data.DatabaseContract.StoreColumns.PHONENO;
import static com.example.project.shopanywhere.data.DatabaseContract.StoreColumns.PICTURE;
import static com.example.project.shopanywhere.data.DatabaseContract.StoreColumns.STORENAME;
import static com.example.project.shopanywhere.data.DatabaseContract.UserColumns.USERID;
import static com.example.project.shopanywhere.data.DatabaseContract.UserColumns.USERNAME;
import static com.example.project.shopanywhere.data.DatabaseContract.UserColumns.USERPASSWORD;


public class DatabaseContract {
    public static final String TABLE_STORE = "store";
    public static final String TABLE_ITEM = "item";
    public static final String TABLE_USER = "user";

    public static final class StoreColumns implements BaseColumns {
        public static final String STORENAME = "store";
        public static final String LOCATION = "location";
        public static final String PHONENO = "phoneno";
        public static final String DESCRIPTION = "description";
        public static final String PICTURE = "picture";
    }
    public static final class ItemColumns implements BaseColumns {
        public static final String ITEMSTORENAME = "store";
        public static final String ITEMNAME = "item";
        public static final String PRICE = "price";
        public static final String COLOR = "color";
        public static final String ITEMSPICTURE = "picture";
    }
    public static final class UserColumns implements BaseColumns {
        public static final String USERNAME = "username";
        public static final String USERPASSWORD = "userpassword";
        public static final String USERID = "uerId";
    }


    //Unique authority string for the content provider
    public static final String CONTENT_AUTHORITY = "com.example.project.shopanywhere";



    //Base content Uri for accessing the provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_STORE)
            .build();
    public static final Uri CONTENT_URI_ID(String id) {
        return CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(id))
                .build();
    }
    public static final Uri CONTENT_URI_ITEM = new Uri.Builder().scheme("content")
                .authority(CONTENT_AUTHORITY)
                .appendPath(TABLE_ITEM)
                .build();
    public static final Uri CONTENT_URI__ITEM_ID(String id) {
        return CONTENT_URI_ITEM.buildUpon()
                .appendPath(String.valueOf(id))
                .build();
    }
    public static final Uri CONTENT_URI_USER = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_USER)
            .build();
    public static final Uri CONTENT_URI__USER_ID(String id) {
        return CONTENT_URI_USER.buildUpon()
                .appendPath(String.valueOf(id))
                .build();
    }


    /* Helpers to retrieve column values */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }

    public static String[] storeProjection(){
        return new String[]{STORENAME,DESCRIPTION,LOCATION,PHONENO,PICTURE, StoreColumns._ID};
    }
    public static String[] itemProjection(){
        return new String[]{ITEMSTORENAME,ITEMNAME,PRICE,COLOR,ITEMSPICTURE, ItemColumns._ID};
    }
    public static String[] userProjection(){
       return new String[]{USERNAME,USERPASSWORD,USERID};
    }
}
