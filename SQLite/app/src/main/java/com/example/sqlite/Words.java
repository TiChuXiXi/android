package com.example.sqlite;

import android.provider.BaseColumns;

public class Words {
    public Words(){}

    public static class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_MEAN = "meaning";
        public static final String COLUMN_NAME_SAMPLE = "sample";
    }
}
