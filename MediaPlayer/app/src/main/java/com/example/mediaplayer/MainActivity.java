package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    ListView listView;
    ArrayList<Map<String, String>> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void init(){
        listView = findViewById(R.id.musiclist);
    }

    public void setAdapter(){
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, myList, R.layout.items,
                new String[]{MusicName}, new int[]{R.id.musicName});
    }
}