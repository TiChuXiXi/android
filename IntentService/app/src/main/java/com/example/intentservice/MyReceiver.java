package com.example.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.v("123", intent.getAction());
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
}