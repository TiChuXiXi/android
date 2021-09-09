package com.example.gestures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private MyGestures myGestures;
    private GestureDetector gestureDetector;
    private static final String TAG = "MyGesture";
    private static final int MIN_MOVE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myGestures = new MyGestures();
        gestureDetector = new GestureDetector(this, myGestures);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class MyGestures extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent m, MotionEvent m1, float v, float v1) {
            if(m.getX() - m1.getX() < MIN_MOVE){
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                Log.d(TAG, "onFling:向左滑动");
            }
            return true;
        }
    }
}