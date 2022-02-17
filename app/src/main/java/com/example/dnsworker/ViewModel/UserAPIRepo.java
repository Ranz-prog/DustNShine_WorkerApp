package com.example.dnsworker.ViewModel;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPIRepo {


    //for task Bookings
    public MutableLiveData<ClientBookingModel> clientBookingRequest(String authToken) {
        final MutableLiveData<ClientBookingModel> clientBookingMLD = new MutableLiveData<>();

        Call<ClientBookingModel> clientBookingModelCall = APIClient.getUserService().getClientBooking("Bearer " + authToken);

        clientBookingModelCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                clientBookingMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body().getMessage());
            }
            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: Logout Failed ==>");
            }
        });

        return clientBookingMLD;
    }

    /*For Signout Request*/
    public MutableLiveData<Map<String, String>> signoutRequest(String retrievedToken) {
        final MutableLiveData<Map<String, String>> signoutMutableData = new MutableLiveData<>();

        Call<Map<String, String>> logoutRequest = APIClient.getUserService().userLogout("Bearer " + retrievedToken);
        logoutRequest.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {

                    signoutMutableData.setValue(response.body());

                    Log.d(TAG, "onResponse: " + response.body().get("message"));

                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                //Toast.makeText(getContext(), "Logout Failed", Toast.LENGTH_LONG);
                Log.d(TAG, "onFailure: Logout Failed ==>");

            }
        });

        return signoutMutableData;
    }


    //Getting User Information
    public MutableLiveData<User> userDataRequest(String authToken) {

        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        Call<User> userResponseCall = APIClient.getUserService().getUserData("Bearer " + authToken);

        userResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userMutableLiveData.setValue(response.body());
                } else {
                    Log.d("TAG", "FAILED: " + "ERROR API CALL");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return userMutableLiveData;

    }

}

