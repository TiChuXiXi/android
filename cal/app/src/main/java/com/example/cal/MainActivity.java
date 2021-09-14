package com.example.cal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import mystack.useStack;

public class MainActivity extends AppCompatActivity {

    private static final int MOVE = 200;

    Button btn00,btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button add,sub,cheng,chu,deng,dian,clear,cancel,left,right;
    TextView text;

    boolean getResult = false;

    MyGesture myGesture;
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGesture = new MyGesture();
        gestureDetector = new GestureDetector(myGesture);

        btn00 = findViewById(R.id.btn_00);
        btn0 = findViewById(R.id.btn_0);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        add = findViewById(R.id.btn_add);
        sub = findViewById(R.id.btn_sub);
        cheng = findViewById(R.id.btn_cheng);
        chu = findViewById(R.id.btn_chu);
        deng = findViewById(R.id.btn_equal);
        dian = findViewById(R.id.btn_point);
        clear = findViewById(R.id.btn_clear);
        cancel = findViewById(R.id.btn_cancel);
        text = findViewById(R.id.result_text);
        left = findViewById(R.id.btn_left);
        right = findViewById(R.id.btn_right);

        btn00.setOnClickListener(new Click());
        btn0.setOnClickListener(new Click());
        btn1.setOnClickListener(new Click());;
        btn2.setOnClickListener(new Click());
        btn3.setOnClickListener(new Click());
        btn4.setOnClickListener(new Click());
        btn5.setOnClickListener(new Click());
        btn6.setOnClickListener(new Click());
        btn7.setOnClickListener(new Click());
        btn8.setOnClickListener(new Click());
        btn9.setOnClickListener(new Click());
        add.setOnClickListener(new Click());
        sub.setOnClickListener(new Click());
        cheng.setOnClickListener(new Click());
        chu.setOnClickListener(new Click());
        deng.setOnClickListener(new Click());
        dian.setOnClickListener(new Click());
        clear.setOnClickListener(new Click());
        cancel.setOnClickListener(new Click());
        left.setOnClickListener(new Click());
        right.setOnClickListener(new Click());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() < MOVE){
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }else if(e1.getX() - e2.getX() > MOVE){
                startActivity(new Intent(MainActivity.this, MainActivity3.class));
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
            return true;
        }
    }

    class Click implements View.OnClickListener{

        @Override
        public void onClick(View view) {
//            Log.v("test", String.valueOf(text.getWidth()));
            String str = text.getText().toString();
            if(getResult) {
                str = "";
                getResult = false;
            }
            useStack used = new useStack();
            switch (view.getId()){
                case R.id.btn_left:
                    str += "(";
                    text.setText(str);
                    break;
                case R.id.btn_right:
                    str += ")";
                    text.setText(str);
                    break;
                case R.id.btn_0:
                    str += "0";
                    text.setText(str);
                    break;
                case R.id.btn_00:
                    str += "00";
                    text.setText(str);
                    break;
                case R.id.btn_1:
                    str += "1";
                    text.setText(str);
                    break;
                case R.id.btn_2:
                    str += "2";
                    text.setText(str);
                    break;
                case R.id.btn_3:
                    str += "3";
                    text.setText(str);
                    break;
                case R.id.btn_4:
                    str += "4";
                    text.setText(str);
                    break;
                case R.id.btn_5:
                    str += "5";
                    text.setText(str);
                    break;
                case R.id.btn_6:
                    str += "6";
                    text.setText(str);
                    break;
                case R.id.btn_7:
                    str += "7";
                    text.setText(str);
                    break;
                case R.id.btn_8:
                    str += "8";
                    text.setText(str);
                    break;
                case R.id.btn_9:
                    str += "9";
                    text.setText(str);
                    break;
                case R.id.btn_add:
                    str += "+";
                    text.setText(str);
                    break;
                case R.id.btn_sub:
                    str += "-";
                    text.setText(str);
                    break;
                case R.id.btn_cheng:
                    str += "*";
                    text.setText(str);
                    break;
                case R.id.btn_chu:
                    str += "/";
                    text.setText(str);
                    break;
                case R.id.btn_point:
                    str += ".";
                    text.setText(str);
                    break;
                case R.id.btn_equal:
                    if(!str.equals("")) {
                        str = used.getResult(str);
                        getResult = true;
                        text.setText(str);
                    }
                    break;
                case R.id.btn_clear:
                    str = "";
                    text.setText(str);
                    break;
                case R.id.btn_cancel:
                    if(!str.equals("")) {
                        str = str.substring(0, str.length() - 1);
                        text.setText(str);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}