package com.example.dnsworker.Model.User;

import com.example.dnsworker.Model.User.User;

public class MData {
    private User user;
    private String token;


    public MData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}