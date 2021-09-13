package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        ArrayList<Map<String, String>> item = (ArrayList)intent.getSerializableExtra("searchedResult");
        Log.v("SearchActivity", item.toString());
        SimpleAdapter adapter = new SimpleAdapter(this, item, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEAN, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.id123, R.id.name, R.id.mean, R.id.sample});
        ListView listView = findViewById(R.id.mySearchedList);
        listView.setAdapter(adapter);
    }
}