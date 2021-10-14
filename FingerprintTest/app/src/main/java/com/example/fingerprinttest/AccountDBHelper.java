package com.example.fingerprinttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AccountDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydata";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_DATABASE = "Create table " + Account.TABLE_NAME +
            "(" + Account._ID + " Integer primary key autoincrement," +
            Account.DESCRIPTION + " Text," +
            Account.NAME + " Text," +
            Account.PASSWORD + " Text)";
    private static final String SQL_DELETE_DATABASE = "Drop table if exists " + Account.TABLE_NAME;

    public AccountDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }
}
