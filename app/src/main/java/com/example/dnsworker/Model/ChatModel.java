package com.example.dnsworker.Model;

public class ChatModel {
    private int chat_clientImage;
    private String chat_clientName, chat_clientMessage, chat_timeReceive;

    public ChatModel(int chat_clientImage, String chat_clientName, String chat_clientMessage, String chat_timeReceive) {
        this.chat_clientImage = chat_clientImage;
        this.chat_clientName = chat_clientName;
        this.chat_clientMessage = chat_clientMessage;
        this.chat_timeReceive = chat_timeReceive;

    }

    public int getChat_clientImage() {
        return chat_clientImage;
    }

    public void setChat_clientImage(int chat_clientImage) {
        this.chat_clientImage = chat_clientImage;
    }

    public String getChat_clientName() {
        return chat_clientName;
    }

    public void setChat_clientName(String chat_clientName) {
        this.chat_clientName = chat_clientName;
    }

    public String getChat_clientMessage() {
        return chat_clientMessage;
    }

    public void setChat_clientMessage(String chat_clientMessage) {
        this.chat_clientMessage = chat_clientMessage;
    }

    public String getChat_timeReceive() {
        return chat_timeReceive;
    }

    public void setChat_timeReceive(String chat_timeReceive) {
        this.chat_timeReceive = chat_timeReceive;
    }
}

