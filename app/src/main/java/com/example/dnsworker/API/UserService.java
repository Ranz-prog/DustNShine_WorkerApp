package com.example.dnsworker.API;

import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    Call<LogInResponse> userLogin(@Body LogInRequest logInRequest);

    @POST("logout")
    Call <Map<String, String>> userLogout(@Header("Authorization") String logoutRequest);

    @GET("user")
    Call <User> getUserData(@Header("Authorization") String authToken);

}
