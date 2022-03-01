package com.example.dnsworker.API;

import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    @POST("login")
    Call<LogInResponse> userLogin(@Body LogInRequest logInRequest);

    @POST("logout")
    Call<Map<String, String>> userLogout(@Header("Authorization") String logoutRequest);

    @GET("user")
    Call<User> getUserData(@Header("Authorization") String authToken);

    @GET("worker")
    Call<ClientBookingModel> getClientBooking(@Header("Authorization") String bookingRequest);

    //    @POST("work/{id}")
//    Call <ClientBookingModel> postTimeAndDate(@Header("Authorization") String authToken,
//                                              @Path("id") int id,
//                                              @Body String datetimeRequest);
    @FormUrlEncoded
    @PUT("work/{id}")
    Call<ClientBookingModel> postDateAndTime(
            @Header("Authorization") String authToken,
            @Path("id") int id,
            @Field("start_datetime") String dateTimeRequest);
}
