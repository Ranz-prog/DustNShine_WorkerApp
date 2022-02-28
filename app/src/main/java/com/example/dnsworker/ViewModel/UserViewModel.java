package com.example.dnsworker.ViewModel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.User;
import com.example.dnsworker.Service.UserService;


import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userMLD;
    private MutableLiveData<Map<String, String>> signoutMLD;
    private MutableLiveData<LogInResponse> signinMLD;

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

