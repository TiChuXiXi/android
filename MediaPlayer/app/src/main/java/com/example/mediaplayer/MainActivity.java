package com.example.mediaplayer;

import static com.example.mediaplayer.Music.MusicId;
import static com.example.mediaplayer.Music.MusicName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{



    ListView listView;
    ArrayList<Map<String, String>> myList;
    ArrayList<Music> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        listView = findViewById(R.id.musiclist);
        myList = getUnListMusic(allList);
        setAdapter(myList, listView);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra(MusicId, myList.get(i).get(MusicId));
                startActivity(intent);
            }
        });
    }

    public void setAdapter(ArrayList<Map<String, String>> myList, ListView listView){
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, myList, R.layout.items,
                new String[]{MusicName, MusicId}, new int[]{R.id.musicName, R.id.musicId});
        listView.setAdapter(simpleAdapter);
    }

    public ArrayList<Map<String, String>> getUnListMusic(ArrayList<Music> AllList){
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for(int i = 0; i < AllList.size(); i++){
            Map<String, String> map = new HashMap<>();
            map.put(MusicName, AllList.get(i).getMusicName());
            map.put(MusicId, AllList.get(i).getId()+"");
            list.add(map);
        }
        return list;
    }

//    public ArrayList<Music> getAll(){
//        String[] NAME = {"123", "123", "123"};
//        int[] ID = {R.raw.music1, R.raw.music2, R.raw.music3};
//        ArrayList<Music> list = new ArrayList<>();
//        for(int i = 0; i < NAME.length; i++){
//            Music music = new Music(NAME[i], ID[i], false);
//            list.add(music);
//        }
//        return list;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add_music:
                addMusic();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        Map<String, String> map = getUnListMusic(allList).get(index);
            int id = item.getItemId();
            switch (id) {
                case R.id.delete_music:
                    deleteMusic(map.get(MusicId));
                    break;
            }
            return super.onContextItemSelected(item);
    }

    public void addMusic(){
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        intent.putExtra("playList", allList);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == 1){
                Music music = new Music(data.getStringExtra(MusicName), data.getIntExtra(MusicId, -1), false);
                allList.add(music);
                myList = getUnListMusic(allList);
                setAdapter(myList, listView);
            }
        }
    }

    public void deleteMusic(String musicId){
        new AlertDialog.Builder(this).setTitle("删除歌曲").setMessage("是否要删除歌曲？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(musicId);
                        setAdapter(myList, listView);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    public void delete(String musicId){
        for(int i = 0; i < myList.size(); i++){
            if(myList.get(i).get(MusicId).equals(musicId)){
                myList.remove(i);
                return;
            }
        }
    }
}