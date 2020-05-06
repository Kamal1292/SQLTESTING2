package com.example.sqltesting2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "FIRST_NAME";
    public static final String COLUMN_NICKNAME = "NICKNAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_STREET = "STREET";
    public static final String COLUMN_SUITE = "SUITE";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_ZIPCODE = "ZIPCODE";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_WEBSITE = "WEBSITE";
    public static final String COLUMN_COMPANY_NAME = "COMPANY_NAME";
    public static final String COLUMN_CATCHPHRASE = "CATCHPHRASE";
    public static final String COLUMN_BS = "BS";

    public DBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table TABLE_NAME ("
                + "_id integer primary key autoincrement,"
                + "ID text,"
                + "FIRST_NAME text,"
                + "NICKNAME text,"
                + "EMAIL text,"
                + "STREET text,"
                + "SUITE text,"
                + "CITY text,"
                + "ZIPCODE text,"
                + "PHONE text,"
                + "WEBSITE text,"
                + "COMPANY_NAME text,"
                + "CATCHPHRASE text,"
                + "BS text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}