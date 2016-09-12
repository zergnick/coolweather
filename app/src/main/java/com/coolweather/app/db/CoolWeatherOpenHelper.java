package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chai on 2016/9/12.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {
    /**
     * Province Table
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement,"
            + "province_name text,"
            + "province_key text)";

    /**
     * City Table
     */
    public static final String CREATE_CITY = "create table City ("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_key text,"
            + "province_id integer)";

    /**
     * Country Table
     */
    public static final String CREATE_COUNTRY = "create table Country ("
            + "id integer primary key autoincrement,"
            + "country_name text,"
            + "country_key text,"
            + "city_id integer)";

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
