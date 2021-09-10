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

public class MainActivity2 extends AppCompatActivity {

    private static final int MOVE = 200;

    Button btn00,btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    Button point,clear;
    Button reci,pow2,pow3,sqrt,ln,log,factorial,sin,cos,tan;
    TextView text;

    double getResult = 0;

    MyGesture myGesture;
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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
        point = findViewById(R.id.btn_point);
        clear = findViewById(R.id.btn_clear);
        reci = findViewById(R.id.btn_reci);
        pow2 = findViewById(R.id.btn_pow2);
        pow3 = findViewById(R.id.btn_pow3);
        sqrt = findViewById(R.id.btn_sqrt);
        factorial = findViewById(R.id.btn_factorial);
        sin = findViewById(R.id.btn_sin);
        cos = findViewById(R.id.btn_cos);
        tan = findViewById(R.id.btn_tan);
        ln = findViewById(R.id.btn_ln);
        log = findViewById(R.id.btn_log);
        text = findViewById(R.id.result_text);

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
        point.setOnClickListener(new Click());
        clear.setOnClickListener(new Click());
        reci.setOnClickListener(new Click());
        pow2.setOnClickListener(new Click());
        pow3.setOnClickListener(new Click());
        sqrt.setOnClickListener(new Click());
        factorial.setOnClickListener(new Click());
        sin.setOnClickListener(new Click());
        cos.setOnClickListener(new Click());
        tan.setOnClickListener(new Click());
        log.setOnClickListener(new Click());
        ln.setOnClickListener(new Click());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > MOVE){
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
            return true;
        }
    }

    class Click implements View.OnClickListener{

        @Override
        public void onClick(View view) {
//            Log.v("test", String.valueOf(text.getWidth()));
            String str = text.getText().toString();
            getResult = getResult(str);
            switch (view.getId()){
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
                case R.id.btn_point:
                    if(!getPoint(str))
                        str += ".";
                    text.setText(str);
                    break;
                case R.id.btn_clear:
                    str = "";
                    text.setText(str);
                    break;
                case R.id.btn_reci:
                    if(getResult != -1)
                        text.setText(String.valueOf(1/getResult));
                    break;
                case R.id.btn_pow2:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.pow(getResult, 2)));
                    break;
                case R.id.btn_pow3:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.pow(getResult, 3)));
                    break;
                case R.id.btn_sqrt:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.sqrt(getResult)));
                    break;
                case R.id.btn_ln:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.log(getResult)));
                    break;
                case R.id.btn_log:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.log10(getResult)));
                    break;
                case R.id.btn_factorial:
                    if(getResult != -1)
                        if(getResult - (int)getResult == 0)
                            text.setText(getFactorial((int)getResult));
                    break;
                case R.id.btn_sin:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.sin(getResult*180/Math.PI)));
                    break;
                case R.id.btn_cos:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.cos(getResult*180/Math.PI)));
                    break;
                case R.id.btn_tan:
                    if(getResult != -1)
                        text.setText(String.valueOf(Math.tan(getResult*180/Math.PI)));
                    break;
                default:
                    break;
            }
        }
    }

    public boolean getPoint(String str){
        return str.contains(".");
    }

    public double getResult(String str){
        if(!str.equals(""))
            return Double.parseDouble(str);
        return -1;
    }

    public int getFactorial(int content){
        int sum = 1;
        while(content > 1){
            sum *= content;
            content--;
        }
        return sum;
    }
}