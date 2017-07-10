package com.sinergia.registroepisodiosdemigrana.Activities.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sinergia.registroepisodiosdemigrana.Activities.Dto.URLDto;

import java.util.HashMap;

/**
 * Created by juan on 8/07/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EpisodioMigrana.db";
    public static final String URL_TABLE_NAME = "URL";
    public static final String URL_COLUMN_ID = "Id";
    public static final String URL_COLUMN_URL = "URL";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table URL (Id int primary key, URL text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS URL");
        onCreate(sqLiteDatabase);
    }

    public Boolean InsertContract(int id, String URL){
        SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Id", id);
        contentValues.put("URL", URL);

        sQLiteDatabase.insert("URL", null, contentValues);
        return true;
    }

    public URLDto GetLastURL(){
        String selectQuery = "SELECT Id, URL FROM URL where Id in (" +
                "               select max(Id) from URL)";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        URLDto result = new URLDto();

        result.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        result.setURL(cursor.getString(cursor.getColumnIndex("URL")));

        return result;
    }

    public int GetURLCount(){
        String selectQuery = "SELECT count(1) as 'Cantidad' FROM URL";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getInt(cursor.getColumnIndex("Cantidad"));
    }
}
