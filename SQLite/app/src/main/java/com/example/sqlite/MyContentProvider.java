package com.example.sqlite;

import static com.example.sqlite.Words.Author;
import static com.example.sqlite.Words.Path;
import static com.example.sqlite.Words.WORD_CODE;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public WordsDBHelper wordsDBHelper;

    static {
        uriMatcher.addURI(Author, Path, WORD_CODE);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        wordsDBHelper = new WordsDBHelper(this.getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = wordsDBHelper.getWritableDatabase();
        int  count = db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = wordsDBHelper.getWritableDatabase();
        long count = db.insert(Words.Word.TABLE_NAME, null, values);
        if(count > 0){
            Uri newUri = ContentUris.withAppendedId(uri, count);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = wordsDBHelper.getReadableDatabase();
        return db.query(Words.Word.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = wordsDBHelper.getWritableDatabase();
        int count = db.update(Words.Word.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}