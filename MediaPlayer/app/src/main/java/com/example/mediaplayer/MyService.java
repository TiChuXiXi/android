package com.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    MusicBind musicBind = new MusicBind();
    MediaPlayer mediaPlayer;
    Timer timer;
    int nowIndex, duration;
    ArrayList<Music> list;
    boolean isRandom = false, isLoop = false;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }
    public class MusicBind extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    public void init(){
        mediaPlayer = new MediaPlayer();
    }
    public void getMusicList(ArrayList<Music> list){
        this.list = list;
    }
    public int sendIndex(){
        return this.nowIndex;
    }
    public void setRandom(boolean isRandom){
        this.isRandom = isRandom;
    }
    public void play(int index, TextView musicName, TextView singerName){
        init();
        nowIndex = index;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), list.get(index).getId());
        duration = mediaPlayer.getDuration();
        mediaPlayer.start();
        mediaPlayer.setLooping(isLoop);
        musicName.setText(list.get(index).getMusicName());
        singerName.setText(list.get(index).getSingerName());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(isRandom){
                    while(nowIndex == index)
                        nowIndex = (int)(Math.random() * list.size());
                }else{
                    nowIndex++;
                    if(nowIndex == list.size())
                        nowIndex = 0;
                }
                clear();
                play(nowIndex, musicName, singerName);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                clear();
                play(nowIndex, musicName, singerName);
                return false;
            }
        });
        addTimer();
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public void continuePlay(){
        mediaPlayer.start();
    }
    public void stop(){
        mediaPlayer.stop();
    }
    public void setLoop(boolean isLoop){
        this.isLoop = isLoop;
        mediaPlayer.setLooping(isLoop);
    }
    public void setPosition(int i){
        mediaPlayer.seekTo(i);
    }
    public void clear(){
        mediaPlayer.reset();
        mediaPlayer = null;
    }
    public void addTimer(){
        if(timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(mediaPlayer == null) return;
                    Message message = MainActivity2.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("current", mediaPlayer.getCurrentPosition());
                    bundle.putInt("duration", duration);
                    message.setData(bundle);
                    MainActivity2.handler.sendMessage(message);
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
        if(mediaPlayer == null) return;
        mediaPlayer = null;
    }
}