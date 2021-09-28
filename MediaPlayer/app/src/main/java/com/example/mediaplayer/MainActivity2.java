package com.example.mediaplayer;

import static com.example.mediaplayer.Music.MusicId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button service, start, pause, stop;
    static SeekBar seekBar;
    ServiceConnection connection;
    MyService myService;
    boolean isPlaying = false;
    Intent intent;

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            seekBar.setMax(bundle.getInt("duration"));
            seekBar.setProgress(bundle.getInt("current"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent = getIntent();

        service = findViewById(R.id.btn_service);
        start = findViewById(R.id.btn_start);
        pause = findViewById(R.id.btn_pause);
        stop = findViewById(R.id.btn_stop);
        seekBar = findViewById(R.id.seekbar);

        service.setOnClickListener(this);
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_service:
                bindService(new Intent(MainActivity2.this, MyService.class),
                        connection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_start:
                myService.getMe();
                myService.play(intent.getStringExtra(MusicId));
                break;
            case R.id.btn_pause:
                if(pause.getText().equals("暂停")){
                    myService.pause();
                    pause.setText("继续");
                }
                else if(pause.getText().equals("继续")){
                    myService.continuePlay();
                    pause.setText("暂停");
                }
                break;
            case R.id.btn_stop:
                myService.pause();
                unbindService(connection);
                finish();
                break;
        }
    }
}