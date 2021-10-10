package com.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    MusicBind musicBind = new MusicBind();
    MediaPlayer mediaPlayer;
    Timer timer;
    int i = 0;

    public MyService() {
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

    public void getMe(){
        Toast.makeText(getApplicationContext(), "122222222", Toast.LENGTH_SHORT).show();
    }
    public void play(int id){
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), id);

        mediaPlayer.start();
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
    public void playStyle(){
        mediaPlayer.setLooping(true);
    }
    public void setPosition(int i){
        mediaPlayer.seekTo(i);
    }
    public void addTimer(){
//        Log.v("asd", "1");
        if(timer == null){
//            Log.v("asd", "2");
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(mediaPlayer == null) return;
//                    Log.v("asd", "3");
                    Message message = MainActivity2.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("current", mediaPlayer.getCurrentPosition());
                    bundle.putInt("duration", mediaPlayer.getDuration());
                    message.setData(bundle);
                    MainActivity2.handler.sendMessage(message);
//                    Log.v("asd", i++ + "");
                }
            };
            timer.schedule(task, 0, 1000);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
        if(mediaPlayer == null) return;
//        if(mediaPlayer.isPlaying()){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
        Toast.makeText(this, "结束", Toast.LENGTH_SHORT).show();
        mediaPlayer = null;
    }
}