package com.example.dnsworker.Service;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingService{
    //for task Bookings
    public MutableLiveData<ClientBookingModel> clientBookingRequest(String authToken) {
        final MutableLiveData<ClientBookingModel> clientBookingMLD = new MutableLiveData<>();

        Call<ClientBookingModel> clientBookingModelCall = APIClient.getUserService().getClientBooking("Bearer " + authToken);

        clientBookingModelCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                clientBookingMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body());
            }
            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return clientBookingMLD;
    }

    public MutableLiveData<ClientBookingModel> timedateRequest(String authToken, int id, String dummyModel){
        final MutableLiveData<ClientBookingModel> timedateMLD = new MutableLiveData<>();

        Call<ClientBookingModel> timedateCall = APIClient.getUserService().postDateAndTime("Bearer " + authToken,id,dummyModel);

        timedateCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                timedateMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return timedateMLD;
    }
}
