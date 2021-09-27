package com.example.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    MusicBind musicBind = new MusicBind();
    MediaPlayer mediaPlayer = new MediaPlayer();

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
    public void play(String id){
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), Integer.parseInt(id));
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
        mediaPlayer.release();
    }
    public void setPosition(int i){
        mediaPlayer.seekTo(i);
    }
    public void addTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message message = MainActivity2.handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("current", mediaPlayer.getCurrentPosition());
                bundle.putInt("duration", mediaPlayer.getDuration());
                message.setData(bundle);
                MainActivity2.handler.sendMessage(message);
            }
        };
        timer.schedule(task, 100, 500);
    }
}