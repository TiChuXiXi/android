package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    RightFragment rightFragment;
    ListView listView;
    ArrayList<Map<String, String>> item;
    boolean island;
    Intent intent;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

            setContentView(R.layout.land_main);
            init();
            Toast.makeText(this, "land", Toast.LENGTH_SHORT).show();
        }else{
            setContentView(R.layout.activity_main);
            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        island = intent.getBooleanExtra("layout", false);
        if(island){
            setContentView(R.layout.land_main);
            island = true;
        }
        init();
    }

    public void init(){
        item = (ArrayList)intent.getSerializableExtra("searchedResult");
        setAdapter(item);
        loadRightFragment();
    }

    public ArrayList<Map<String, String>> getItem(){
        Intent intent = getIntent();
        ArrayList<Map<String, String>> item = (ArrayList)intent.getSerializableExtra("searchedResult");
        return item;
    }
    public void setAdapter(ArrayList<Map<String, String>> item){
        SimpleAdapter adapter = new SimpleAdapter(this, item, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEAN, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.id123, R.id.name, R.id.mean, R.id.item_sample});
        listView = findViewById(R.id.mylist);
        listView.setAdapter(adapter);
    }
    public void loadRightFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        rightFragment = new RightFragment();
        transaction.replace(R.id.sample_show, rightFragment).commit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, String> map = item.get(i);

                rightFragment.getTextView(new RightFragment.CallBack() {
                    @Override
                    public void setSample(TextView textView) {
                        textView.setText(map.get(Words.Word.COLUMN_NAME_SAMPLE));
                    }
                });
            }
        });
    }
}