package com.example.dnsworker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Models.ChatModel;
import com.example.dnsworker.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ViewHolder> {

    List<ChatModel> chatModelList;

    public Chat_Adapter(List<ChatModel> chatModelList) {
        this.chatModelList = chatModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_message_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.chat_clientImageView.setImageResource(chatModelList.get(position).getChat_clientImage());
        holder.chat_clientName.setText(chatModelList.get(position).getChat_clientName());
        holder.chat_clientMessage.setText(chatModelList.get(position).getChat_clientMessage());

    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView chat_clientImageView;
        private TextView chat_clientName, chat_clientMessage, chat_timeReceived;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            chat_clientImageView = itemView.findViewById(R.id.chat_clientImage);
            chat_clientName = itemView.findViewById(R.id.chat_clientName_TV);
            chat_clientMessage = itemView.findViewById(R.id.chat_clientMessage_TV);
        }
    }
}
