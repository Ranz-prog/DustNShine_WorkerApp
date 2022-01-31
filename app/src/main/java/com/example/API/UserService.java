package com.example.API;

import com.example.LogIn.LogInRequest;
import com.example.LogIn.LogInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("login")
    Call<LogInResponse> userLogin(@Body LogInRequest logInRequest);

}
