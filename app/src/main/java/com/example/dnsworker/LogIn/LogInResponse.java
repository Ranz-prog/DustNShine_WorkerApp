package com.example.dnsworker.LogIn;

import com.example.dnsworker.Model.User.MData;

public class LogInResponse {

    private String message;
    private MData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MData getData() {
        return data;
    }

    public void setData(MData data) {
        this.data = data;
    }

}
