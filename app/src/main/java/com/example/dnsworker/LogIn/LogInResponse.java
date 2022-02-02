package com.example.dnsworker.LogIn;

import com.example.dnsworker.Model.MData;

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

//
//    private int user_id;
//    private String email;
//    private String usertoken;
//
//    public String getUsertoken() {
//        return usertoken;
//    }
//
//    public void setUsertoken(String usertoken) {
//        this.usertoken = usertoken;
//    }
//
//    public int getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(int user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
}
