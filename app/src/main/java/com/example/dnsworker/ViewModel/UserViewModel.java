package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.User.User;
import com.example.dnsworker.Service.UserService;


import java.util.Map;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userMLD;
    private MutableLiveData<Map<String, String>> signoutMLD;
    public UserService userService;


    public UserViewModel() {
        userService = new UserService();
    }

    public LiveData<User> getUserDataResponse(String authToken) {

        if (userMLD == null) {
            userMLD = userService.userDataRequest(authToken);
        }
        return userMLD;
    }

    public LiveData<Map<String, String>> getSignoutResponse(String retrievedToken) {

        if (signoutMLD == null) {
            signoutMLD = userService.signoutRequest(retrievedToken);
        }
        return signoutMLD;

    }

    public void getSignInRes(String email, String password) {
        userService.signinRequest(email, password);
    }
}

