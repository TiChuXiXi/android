package com.example.chat_user;

public class UserMessage {
    public static final int message_of_left = 0;
    public static final int message_of_right = 1;

    private String content;
    private int type;

    public UserMessage(String content, int type){
        this.content = content;
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
    public String getContent(){
        return this.content;
    }
}
