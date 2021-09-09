package com.example.chat_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private RecyclerView recyclerView;
    List<UserMessage> userMessageList;
    UserMessageAdapter userMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit);
        recyclerView = findViewById(R.id.recycle);
        userMessageList = new ArrayList<>();

        userMessageAdapter = new UserMessageAdapter(userMessageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userMessageAdapter);
    }

    public void onClick(View view){
        String userMessages = editText.getText().toString();
        if(userMessages.trim().length() == 0){
            Toast.makeText(this, "消息不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        sendMessage(userMessages);
        receiveMessage("收到：" + userMessages);
    }
    public void sendMessage(String content){
        UserMessage userMessage = new UserMessage(content, UserMessage.message_of_right);
        userMessageList.add(userMessage);
        //将消息插入到末尾
        userMessageAdapter.notifyItemInserted(userMessageList.size() - 1);
        //让recyclerview自动滚动到最底部
        recyclerView.scrollToPosition(userMessageList.size() - 1);
        editText.setText("");
    }
    public void receiveMessage(String content){
        UserMessage userMessage = new UserMessage(content, UserMessage.message_of_left);
        userMessageList.add(userMessage);
        //将消息插入到末尾
        userMessageAdapter.notifyItemInserted(userMessageList.size() - 1);
        //让recyclerview自动滚动到最底部
        recyclerView.scrollToPosition(userMessageList.size() - 1);
    }
}