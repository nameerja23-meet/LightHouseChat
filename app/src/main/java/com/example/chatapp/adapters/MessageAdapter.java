package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.models.AIMessage;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<AIMessage> messageList;
    public MessageAdapter(List<AIMessage> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_ai_message,null);
        MyViewHolder myViewHolder = new MyViewHolder(chatview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AIMessage message = messageList.get(position);
        if (message.getSentBy().equals(AIMessage.SENT_BY_ME)){
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(message.getMessage());
        }else{
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftTextView.setText(message.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftChatView,rightChatView;
        TextView leftTextView,rightTextView;

        public MyViewHolder(@NonNull View itemView) {


            super(itemView);
            leftChatView = itemView.findViewById(R.id.left_chat_view);
            rightChatView = itemView.findViewById(R.id.right_chat_view);
            leftTextView = itemView.findViewById(R.id.left_chat_textview);
            rightTextView = itemView.findViewById(R.id.right_chat_textview);

        }
    }
}
