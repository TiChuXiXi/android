package com.example.intentservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_ACTION1 = "android.intent.action.MyBroadcast1";
    public static final String INTENT_ACTION2 = "android.intent.action.MyBroadcast2";

    EditText edit1, edit2;
    Button btn1, btn2;

//    MyReceiver receiver;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MainActivity.INTENT_ACTION1)){
                String edit1 = intent.getStringExtra("broadcast1");
                if(edit1.equals("yes"))
                    Toast.makeText(context, "run1:是素数", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "run1:不是素数", Toast.LENGTH_SHORT).show();
            }else {
                String edit2 = intent.getStringExtra("broadcast2");
                if(edit2.equals("yes"))
                    Toast.makeText(context, "run2:是素数", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "run2:不是素数", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        btn1 = findViewById(R.id.run1);
        btn2 = findViewById(R.id.run2);

        IntentFilter filter = new IntentFilter();
        filter.addAction(MainActivity.INTENT_ACTION1);
        filter.addAction(MainActivity.INTENT_ACTION2);
//        registerReceiver(receiver, filter);
        registerReceiver(broadcastReceiver, filter);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService.startActionFoo(MainActivity.this, edit1.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyIntentService.startActionBaz(MainActivity.this, edit2.getText().toString());
            }
        });

    }


}