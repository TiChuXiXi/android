package com.example.chat_user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.UserMessageViewHolder>{
    private final List<UserMessage> UserMessageList;

    public UserMessageAdapter(List<UserMessage> UserMessageList){
        this.UserMessageList = UserMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        UserMessage msg = UserMessageList.get(position);
        return msg.getType();
    }

    @NonNull
    @Override
    public UserMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == UserMessage.message_of_right){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_message, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_message, parent, false);
        }
        return new UserMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMessageViewHolder holder, int position) {
        UserMessage userMessage = UserMessageList.get(position);
        if(userMessage.getType() == UserMessage.message_of_right){
            holder.textRight.setText(userMessage.getContent());
        }else{
            holder.textLeft.setText(userMessage.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return UserMessageList.size();
    }

    class UserMessageViewHolder extends RecyclerView.ViewHolder{
        TextView textLeft, textRight;
        public UserMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textLeft = itemView.findViewById(R.id.left);
            textRight = itemView.findViewById(R.id.right);
        }
    }
}
