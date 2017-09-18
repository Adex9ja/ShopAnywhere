package com.example.project.shopanywhere.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MyProvider extends ContentProvider {

    private static final int STORE = 100;
    private static final int STORE_ID = 101;
    private static final int ITEM = 200;
    private static final int ITEM_ID = 201;
    private static final int USER = 300;
    private static final int USER_ID = 301;

    private MyDBHelper mDbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_STORE,
                STORE);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_STORE + "/*",
                STORE_ID);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_ITEM,
                ITEM);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_ITEM + "/*",
                ITEM_ID);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_USER,
                USER);
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_USER + "/*",
                USER_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MyDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null; /* Not used */
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String selectedId;
        long id;
        switch (sUriMatcher.match(uri)) {
            case STORE:
                cursor = db.query(DatabaseContract.TABLE_STORE,
                        DatabaseContract.storeProjection(),
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STORE_ID:
                selectedId = uri.getLastPathSegment();
                cursor = db.query(DatabaseContract.TABLE_STORE,
                        DatabaseContract.storeProjection(),
                        DatabaseContract.StoreColumns.STORENAME + " = ? ",
                        new String[]{selectedId},
                        null,
                        null,
                        sortOrder);
                break;
            case ITEM:
                cursor = db.query(DatabaseContract.TABLE_ITEM,
                        DatabaseContract.itemProjection(),
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case ITEM_ID:
                selectedId = uri.getLastPathSegment();
                cursor = db.query(DatabaseContract.TABLE_ITEM,
                        DatabaseContract.itemProjection(),
                        DatabaseContract.ItemColumns.ITEMSPICTURE + " = ? ",
                        new String[]{selectedId},
                        null,
                        null,
                        sortOrder);
                break;
            case USER:
                cursor = db.query(DatabaseContract.TABLE_USER,
                        DatabaseContract.userProjection(),
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case USER_ID:
                selectedId = uri.getLastPathSegment();
                cursor = db.query(DatabaseContract.TABLE_USER,
                        DatabaseContract.userProjection(),
                        DatabaseContract.UserColumns.USERNAME + " = ? ",
                        new String[]{selectedId},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int matchid = sUriMatcher.match(uri);
        Uri uriTorReturn;
        long ret;
        switch (matchid){
            case STORE:
                ret = db.insert(DatabaseContract.TABLE_STORE,null,values);
                if(ret > 0)
                    uriTorReturn = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI,ret);
                else
                    uriTorReturn = null;
                break;
            case ITEM:
                ret = db.insert(DatabaseContract.TABLE_ITEM,null,values);
                if(ret > 0)
                    uriTorReturn = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI,ret);
                else
                    uriTorReturn = null;
                break;
            case USER:
                ret = db.insert(DatabaseContract.TABLE_USER,null,values);
                if(ret > 0)
                    uriTorReturn = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI,ret);
                else
                    uriTorReturn = null;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return uriTorReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int matchid = sUriMatcher.match(uri);
        long ret;
        String id;
        switch (matchid){
            case STORE_ID:
                 id = uri.getLastPathSegment();
                 ret = db.update(DatabaseContract.TABLE_STORE,
                        values,
                        DatabaseContract.StoreColumns.PICTURE + " = ?",
                        new String[]{String.valueOf(id)});
                return  Integer.parseInt(String.valueOf(ret));
            case ITEM_ID:
                id = uri.getLastPathSegment();
                ret = db.update(DatabaseContract.TABLE_ITEM,
                        values,
                        DatabaseContract.ItemColumns.ITEMSPICTURE + " = ?",
                        new String[]{String.valueOf(id)});
                return  Integer.parseInt(String.valueOf(ret));
            case USER_ID:
                id = uri.getLastPathSegment();
                ret = db.update(DatabaseContract.TABLE_USER,
                        values,
                        DatabaseContract.UserColumns.USERID + " = ?",
                        new String[]{String.valueOf(id)});
                return  Integer.parseInt(String.valueOf(ret));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int count ;
        String id;
        switch (sUriMatcher.match(uri)) {
            case STORE:
                count = db.delete(DatabaseContract.TABLE_STORE, null, null);
                break;
            case ITEM:
                count = db.delete(DatabaseContract.TABLE_STORE, null, null);
                break;
            case USER:
                count = db.delete(DatabaseContract.TABLE_USER, null, null);
                break;
            case STORE_ID:
                id = uri.getLastPathSegment();
                selection = String.format("%s = ?", DatabaseContract.StoreColumns.PICTURE);
                selectionArgs = new String[]{String.valueOf(id)};
                count = db.delete(DatabaseContract.TABLE_STORE, selection, selectionArgs);
                break;
            case ITEM_ID:
                id = uri.getLastPathSegment();
                selection = String.format("%s = ?", DatabaseContract.StoreColumns.PICTURE);
                selectionArgs = new String[]{String.valueOf(id)};
                count = db.delete(DatabaseContract.TABLE_ITEM, selection, selectionArgs);
                break;
            case USER_ID:
                id = uri.getLastPathSegment();
                selection = String.format("%s = ?", DatabaseContract.UserColumns.USERID);
                selectionArgs = new String[]{String.valueOf(id)};
                count = db.delete(DatabaseContract.TABLE_USER, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        if (count > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

}
