package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WordsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydata";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_DATABASE = "Create table " + Words.Word.TABLE_NAME +
            "(" + Words.Word._ID + " Integer primary key autoincrement," +
            Words.Word.COLUMN_NAME_WORD + " Text," +
            Words.Word.COLUMN_NAME_MEAN + " Text," +
            Words.Word.COLUMN_NAME_SAMPLE + " Text)";
    private static final String SQL_DELETE_DATABASE = "Drop table if exists " + Words.Word.TABLE_NAME;

    public WordsDBHelper(@Nullable Context context) {
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
