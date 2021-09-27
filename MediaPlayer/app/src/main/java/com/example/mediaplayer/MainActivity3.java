package com.example.mediaplayer;

import static com.example.mediaplayer.Music.MusicId;
import static com.example.mediaplayer.Music.MusicName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    ListView listView;
    ArrayList<Map<String, String>> myList;
    ArrayList<Music> allMusic, showMusic, playMusic;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = findViewById(R.id.allmusiclist);
        intent = getIntent();
        playMusic = (ArrayList<Music>) intent.getSerializableExtra("playList");
        allMusic = getAll();
        showMusic = isNotPlay(allMusic, playMusic);
        Toast.makeText(this, ""+playMusic.size()+":"+allMusic.size()+":"+showMusic.size(), Toast.LENGTH_SHORT).show();
        myList = getUnListMusic(showMusic);
        setAdapter(myList, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music = showMusic.get(i);
                intent.putExtra(MusicName, music.getMusicName());
                intent.putExtra(MusicId, music.getId());
                setResult(1, intent);
                finish();
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

    public ArrayList<Music> getAll(){
        String[] NAME = {"123", "456", "789"};
        int[] ID = {R.raw.music1, R.raw.music2, R.raw.music3};
        ArrayList<Music> list = new ArrayList<>();
        for(int i = 0; i < NAME.length; i++){
            Music music = new Music(NAME[i], ID[i], false);
            list.add(music);
        }
        return list;
    }

    public ArrayList<Music> isNotPlay(ArrayList<Music> musiclist, ArrayList<Music> playMusic){
        ArrayList<Music> list = new ArrayList<>();
        boolean isExist = false;
        if(playMusic.size() == 0)
            return musiclist;
        for(int i = 0; i < musiclist.size(); i++){
            for(int j = 0; j < playMusic.size(); j++){
                if(musiclist.get(i).getId() == playMusic.get(j).getId()){
                    isExist = true;
                    break;
                }
                isExist = false;
            }
            if(!isExist)
                list.add(musiclist.get(i));
        }
        return list;
    }
}