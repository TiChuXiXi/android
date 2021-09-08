package com.example.cal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import mystack.useStack;

public class MainActivity extends AppCompatActivity {

    Button btn00;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button add;
    Button sub;
    Button cheng;
    Button chu;
    Button deng;
    Button dian;
    Button clear;
    Button cancel;
    TextView text;

    double num1, num2;
    double result;
    String operate = "";
    int count = 0;
    String digital = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        cheng = findViewById(R.id.cheng);
        chu = findViewById(R.id.chu);
        deng = findViewById(R.id.deng);
        dian = findViewById(R.id.dian);
        clear = findViewById(R.id.clear);
        cancel = findViewById(R.id.cancel);
        text = findViewById(R.id.str);

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
    }

    class Click implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String str = "";
            useStack used = new useStack();
            switch (view.getId()){
                case R.id.btn_0:
                    str = text.getText().toString();
                    str += "0";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_00:
                    str = text.getText().toString();
                    str += "00";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_1:
                    str = text.getText().toString();
                    str += "1";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_2:
                    str = text.getText().toString();
                    str += "2";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_3:
                    str = text.getText().toString();
                    str += "3";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_4:
                    str = text.getText().toString();
                    str += "4";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_5:
                    str = text.getText().toString();
                    str += "5";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_6:
                    str = text.getText().toString();
                    str += "6";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_7:
                    str = text.getText().toString();
                    str += "7";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_8:
                    str = text.getText().toString();
                    str += "8";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.btn_9:
                    str = text.getText().toString();
                    str += "9";
                    digital += str;
                    text.setText(str);
                    break;
                case R.id.add:
                    str = text.getText().toString();
                    str += "+";
                    text.setText(str);
                    break;
                case R.id.sub:
                    str = text.getText().toString();
                    str += "-";
                    text.setText(str);
                    break;
                case R.id.cheng:
                    str = text.getText().toString();
                    str += "*";
                    text.setText(str);
                    break;
                case R.id.chu:
                    str = text.getText().toString();
                    str += "/";
                    text.setText(str);
                    break;
                case R.id.dian:
                    str = text.getText().toString();
                    str += ".";
                    text.setText(str);
                    break;
                case R.id.deng:
                    str = text.getText().toString();
                    str = used.getResult(str);
                    text.setText(str);
                    break;
                case R.id.clear:
                    str = "";
                    text.setText(str);
                    num1 = 0;
                    num2 = 0;
                    result = 0;
                    break;
                case R.id.cancel:
                    str = text.getText().toString();
                    str = str.substring(0, str.length() - 1);
                    text.setText(str);
                    break;
                default:
                    break;
            }
        }
    }
}