package com.example.sqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RightFragment extends Fragment {

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rightfragment, container, false);
        textView = view.findViewById(R.id.item_sample);
        return view;
    }
    public void getTextView(CallBack callBack){
        callBack.setSample(textView);
//        callBack.getSample(textView.getText().toString());
    }
    public interface CallBack{
        public void setSample(TextView textView);
//        public void getSample(String str);
    }

}
