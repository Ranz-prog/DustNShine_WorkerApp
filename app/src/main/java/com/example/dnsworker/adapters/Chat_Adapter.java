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

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.ChatViewHolder> {

    private List<ChatModel> chatModelList;
    private OnClickMessageListener onClickMessageListener;

    public Chat_Adapter(List<ChatModel> chatModelList, OnClickMessageListener onClickMessageListener) {
        this.chatModelList = chatModelList;
        this.onClickMessageListener = onClickMessageListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_message_row, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view, onClickMessageListener);

        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        holder.chat_clientImageView.setImageResource(chatModelList.get(position).getChat_clientImage());
        holder.chat_clientName.setText(chatModelList.get(position).getChat_clientName());
        holder.chat_clientMessage.setText(chatModelList.get(position).getChat_clientMessage());

    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView chat_clientImageView;
        private TextView chat_clientName, chat_clientMessage, chat_timeReceived;

        OnClickMessageListener onClickMessageListener;

        public ChatViewHolder(@NonNull View itemView, OnClickMessageListener onClickMessageListener) {
            super(itemView);

            chat_clientImageView = itemView.findViewById(R.id.chat_clientImage);
            chat_clientName = itemView.findViewById(R.id.chat_clientName_TV);
            chat_clientMessage = itemView.findViewById(R.id.chat_clientMessage_TV);

            this.onClickMessageListener = onClickMessageListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClickMessageListener.onClickMessage(getAdapterPosition());
        }
    }

    public interface OnClickMessageListener{
        void onClickMessage(int position);
    }
}
