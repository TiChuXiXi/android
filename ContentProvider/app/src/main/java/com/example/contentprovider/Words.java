package com.example.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Words {

    public static final String Author = "WORD_APP";
    public static final int WORD_CODE = 1;
    public static final String Path = "word";

    public Words(){}

    public static class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_MEAN = "meaning";
        public static final String COLUMN_NAME_SAMPLE = "sample";

        public static final String uri_path = "content://" + Author + "/" + Path;
        public static final Uri uri = Uri.parse(uri_path);
    }
}
