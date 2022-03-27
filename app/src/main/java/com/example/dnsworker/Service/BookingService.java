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
                callback.bookingCallback(response.code(), response.body());

                Log.d(TAG, "onResponse: " + response.body());

            }
            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return clientBookingMLD;
    }

    public MutableLiveData<ClientBookingModel> clientHistoryBookingRequest(String authToken) {
        final MutableLiveData<ClientBookingModel> clientHistoryBookingMLD = new MutableLiveData<>();

        Call<ClientBookingModel> clientBookingModelCall = APIClient.getUserService().getHistoryBooking("Bearer " + authToken);

        clientBookingModelCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                clientHistoryBookingMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body());
            }
            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return clientHistoryBookingMLD;
    }

    public MutableLiveData<ClientBookingModel> start_datetimeRequest(String authToken, int id, String start_datetime){
        final MutableLiveData<ClientBookingModel> start_dateTimeMLD = new MutableLiveData<>();

        Call<ClientBookingModel> start_datetimeCall = APIClient.getUserService().postStartDateAndTime("Bearer " + authToken, id , start_datetime);

        start_datetimeCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                start_dateTimeMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return start_dateTimeMLD;
    }

    public MutableLiveData<ClientBookingModel> end_datetimeRequest(String authToken, int id, String end_datetime){
        final MutableLiveData<ClientBookingModel> end_dateTimeMLD = new MutableLiveData<>();

        Call<ClientBookingModel> end_datetimeCall = APIClient.getUserService().postEndDateAndTime("Bearer " + authToken, id , end_datetime);

        end_datetimeCall.enqueue(new Callback<ClientBookingModel>() {
            @Override
            public void onResponse(Call<ClientBookingModel> call, Response<ClientBookingModel> response) {
                end_dateTimeMLD.postValue(response.body());
                Log.d(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<ClientBookingModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ==>" + t.toString());
            }
        });

        return end_dateTimeMLD;
    }

    BookingCallback callback;
    public interface BookingCallback {
        void bookingCallback(Integer statusCode, ClientBookingModel clientBookingModel);
    }
    public void setOnBookListener(BookingCallback bookingCallback){
        callback = bookingCallback;
    }

}
