package com.example.cal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    private static final int MOVE = 200;

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    GestureDetector gestureDetector;
    MyGesture myGesture;

    Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        help = findViewById(R.id.btn_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity3.this, "左右滑动发现新功能！", Toast.LENGTH_SHORT).show();
            }
        });

        myGesture = new MyGesture();
        gestureDetector = new GestureDetector(myGesture);

        MyListFragment myListFragment = new MyListFragment();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.left_fragment, myListFragment).commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() - e1.getX() > MOVE){
                startActivity(new Intent(MainActivity3.this, MainActivity.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }else if(e1.getX() - e2.getX() > MOVE){
                startActivity(new Intent(MainActivity3.this, MainActivity2.class));
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
            return true;
        }
    }
}