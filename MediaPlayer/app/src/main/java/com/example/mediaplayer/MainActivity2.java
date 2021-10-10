package com.example.mediaplayer;

import static com.example.mediaplayer.Music.MusicId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button last, pause, next, style;
    static TextView start, end;
    static SeekBar seekBar;
    ServiceConnection connection;
    MyService myService;
    boolean isClose = true;
    boolean isStart = false;
    Intent intent;
    ArrayList<Music> musiclist;
    int index, id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent = getIntent();
        musiclist = (ArrayList<Music>) intent.getSerializableExtra("MusicList");
        index = intent.getIntExtra("index", -1);
        id = musiclist.get(index).getId();
        Log.v("asd", id+"");

        last = findViewById(R.id.btn_up);
        pause = findViewById(R.id.btn_pause);
        next = findViewById(R.id.btn_down);
        style = findViewById(R.id.btn_style);
        seekBar = findViewById(R.id.seekbar);
        start = findViewById(R.id.text_start);
        end = findViewById(R.id.text_end);

        last.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        style.setOnClickListener(this);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                myService = ((MyService.MusicBind)iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myService.setPosition(seekBar.getProgress());
            }
        });
        connectService();
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int max = bundle.getInt("duration");
            int position = bundle.getInt("current");
            seekBar.setMax(max);
            seekBar.setProgress(position);
            start.setText(timeFormat(position));
            end.setText(timeFormat(max));
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_up:
                index--;
                if(index < 0)
                    index = musiclist.size() - 1;
                myService.play(musiclist.get(index).getId());
                pause.setText("正在播放");
                break;
            case R.id.btn_pause:
                if(isStart){
                    if(pause.getText().equals("正在播放")){
                        myService.pause();
                        pause.setText("已暂停");
                    }
                    else if(pause.getText().equals("已暂停")){
                        myService.continuePlay();
                        pause.setText("正在播放");
                    }
                }else{
                    myService.play(musiclist.get(index).getId());
                    pause.setText("正在播放");
                    isStart = true;
                }
                break;
            case R.id.btn_down:
                index++;
                if(index == musiclist.size())
                    index = 0;
                myService.play(musiclist.get(index).getId());
                pause.setText("正在播放");
                break;
            case R.id.btn_style:

                break;
        }
    }

    public void connectService(){
        bindService(new Intent(MainActivity2.this, MyService.class),
                connection, Service.BIND_AUTO_CREATE);
        isClose = false;
    }
    public void closeService(){
        if(!isClose){
            unbindService(connection);
            isClose = true;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeService();
    }

    public static String timeFormat(int time){
        String seconds, minutes;
        int second, minute;
        second = time / 1000;
        minute = second / 60;
        second -= minute * 60;
        if(second < 10)
            seconds = "0"+second;
        else
            seconds = ""+second;
        if(minute < 10)
            minutes = "0"+minute;
        else
            minutes = ""+minute;
        return minutes+":"+seconds;
    }
}