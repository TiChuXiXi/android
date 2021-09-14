package com.example.cal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RightFragment extends Fragment {

    DecimalFormat decimalFormat = new DecimalFormat("0.000");
//    String edit_text1 = "", edit_text2 = "", edit_text3 = "", edit_text4 = "";
    ArrayList<String> mylist = new ArrayList<>();
    boolean handle = true;

    TextView text1, text2, text3, text4;
    EditText edit1, edit2, edit3, edit4;
    Button enter, cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        text1 = view.findViewById(R.id.edit_title1);
        text2 = view.findViewById(R.id.edit_title2);
        text3 = view.findViewById(R.id.edit_title3);
        text4 = view.findViewById(R.id.edit_title4);
        edit1 = view.findViewById(R.id.edit1);
        edit2 = view.findViewById(R.id.edit2);
        edit3 = view.findViewById(R.id.edit3);
        edit4 = view.findViewById(R.id.edit4);
        enter = view.findViewById(R.id.btn_enter);
        cancel = view.findViewById(R.id.btn_cancel);


        getNumber(mylist);

        Bundle bundle = getArguments();
        String item = bundle.getString("item");
        switch (item){
            case "进制转换":
                text1.setText("二进制");
                text2.setText("八进制");
                text3.setText("十进制");
                text4.setText("十六进制");
                break;
            case "长度转换":
                text1.setText("毫米");
                text2.setText("厘米");
                text3.setText("分米");
                text4.setText("米");
                break;
            case "体积转换":
                text1.setText("g/cm^3");
                text2.setText("g/m^3");
                text3.setText("kg/cm^3");
                text4.setText("kg/m^3");
                break;
        }

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = new ArrayList<>();
//                String edit_text11 = "", edit_text22 = "", edit_text33 = "", edit_text44 = "";
                getNumber(list);
                Log.v("RightFragment", handle+"");
                if(handle&&isDigital(item, list)){
                    int index = getIndex(mylist, list);
                    if (index != 0)
                        handle = false;
                Log.v("RightFragment", list.toString());
                    switch (item){
                        case "进制转换":
                            if(index == 1){
                                binary(list.get(0));
                                getNumber(mylist);
                            }
                            if(index == 2){
//                                Log.v("RightFragment", list.get(1).toString());
                                octal(list.get(1));
                                getNumber(mylist);
                            }
                            if(index == 3){
                                decimal(list.get(2));
                                getNumber(mylist);
                            }
                            if(index == 4){
                                hexadecimal(list.get(3));
                                getNumber(mylist);
                            }
                            break;
                        case "长度转换":
                            if(index == 1){
                                millimeter(list.get(0));
                                getNumber(mylist);
                            }
                            if(index == 2){
                                centimeter(list.get(1));
                                getNumber(mylist);
                            }
                            if(index == 3){
                                decimeter(list.get(2));
                                getNumber(mylist);
                            }
                            if(index == 4){
                                meter(list.get(3));
                                getNumber(mylist);
                            }
                            break;
                        case "体积转换":
                            if(index == 1){
                                gOfCentimeter(list.get(0));
                                getNumber(mylist);
                            }
                            if(index == 2){
                                gOfMeter(list.get(1));
                                getNumber(mylist);
                            }
                            if(index == 3){
                                kgOfCentimeter(list.get(2));
                                getNumber(mylist);
                            }
                            if(index == 4){
                                kgOfMeter(list.get(3));
                                getNumber(mylist);
                            }
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle = true;
                edit1.setText("");
                edit2.setText("");
                edit3.setText("");
                edit4.setText("");
            }
        });
        return view;
    }
    public void getNumber(ArrayList<String> list){
        list.add(edit1.getText().toString());
        list.add(edit2.getText().toString());
        list.add(edit3.getText().toString());
        list.add(edit4.getText().toString());
    }
    public int getIndex(ArrayList<String> mylist, ArrayList<String> list){
        for(int i = 0; i < mylist.size(); i++){
            if(!mylist.get(i).equals(list.get(i)))
                return i + 1;
        }
        return 0;
    }
    public boolean isDigital(String item, ArrayList<String> list){
        if(item.equals("进制转换")){
            if(!list.get(0).equals("")){
                for(char c:list.get(0).toCharArray())
                    return c == '0' || c == '1';
            }else if(!list.get(1).equals("")){
                for(char c:list.get(0).toCharArray()){
                    return c >= '0' && c <= '7';
                }
            }
        }
        return true;
    }

    public void binary(String str){
        Integer n = Integer.parseInt(str, 2);
        edit2.setText(Integer.toOctalString(n));
        edit3.setText(n+"");
        edit4.setText(Integer.toHexString(n));
    }
    public void octal(String str){
        Integer n = Integer.parseInt(str, 8);
        edit1.setText(Integer.toBinaryString(n));
        edit3.setText(n+"");
        edit4.setText(Integer.toHexString(n));
    }
    public void decimal(String str){
        Integer n = Integer.parseInt(str);
        edit1.setText(Integer.toBinaryString(n));
        edit2.setText(Integer.toOctalString(n));
        edit4.setText(Integer.toHexString(n));
    }
    public void hexadecimal(String str){
        Integer n = Integer.parseInt(str, 16);
        edit1.setText(Integer.toBinaryString(n));
        edit2.setText(Integer.toOctalString(n));
        edit3.setText(n+"");
    }

    public void millimeter(String str){
        double d = Double.parseDouble(str);
        edit2.setText(10.0*d+"");
        edit3.setText(100.0*d+"");
        edit4.setText(1000.0*d+"");
    }
    public void centimeter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d/10.0+"");
        edit3.setText(10.0*d+"");
        edit4.setText(100.0*d+"");
    }
    public void decimeter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d/100+"");
        edit2.setText(d/10+"");
        edit4.setText(10*d+"");
    }
    public void meter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d/1000+"");
        edit2.setText(d/100+"");
        edit3.setText(d/10+"");
    }

    public void gOfCentimeter(String str){
        double d = Double.parseDouble(str);
        edit2.setText(d*1000000+"");
        edit3.setText(d/1000+"");
        edit4.setText(d*1000+"");
    }
    public void gOfMeter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d/1000000+"");
        edit3.setText(d/1000000000+"");
        edit4.setText(d/1000+"");
    }
    public void kgOfCentimeter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d*1000+"");
        edit2.setText(d*1000000000+"");
        edit4.setText(d*1000000+"");
    }
    public void kgOfMeter(String str){
        double d = Double.parseDouble(str);
        edit1.setText(d/1000+"");
        edit2.setText(d*1000+"");
        edit3.setText(d/1000000+"");
    }
//    private class EditListener implements TextWatcher {
//        private String str1, str2, str3, str4;
//        EditListener(String str1, String str2, String str3, String str4){
//            this.str1 = str1; this.str2 = str2; this.str3 = str3; this.str4 = str4;
//        }
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    }
//
//    public void initListener(String str1, String str2, String str3, String str4){
//        edit1.addTextChangedListener(new EditListener(str1, str2, str3, str4));
//        edit2.addTextChangedListener(new EditListener(str1, str2, str3, str4));
//        edit3.addTextChangedListener(new EditListener(str1, str2, str3, str4));
//        edit4.addTextChangedListener(new EditListener(str1, str2, str3, str4));
//    }
}
