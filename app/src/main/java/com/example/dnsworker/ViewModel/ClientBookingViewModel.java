package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Service.BookingService;


public class ClientBookingViewModel extends ViewModel{

    public BookingService bookingService;
    private MutableLiveData<ClientBookingModel> clientBookingMLD;
    private MutableLiveData<ClientBookingModel> clientHistoryBookingMLD;

    public ClientBookingViewModel(){
        bookingService = new BookingService();
    }

    public void getClientBookingData(String authToken){
        bookingService.clientBookingRequest(authToken);

    }

    public void getHistoryBookingData(String authToken){
        bookingService.clientHistoryBookingRequest(authToken);
    }

//    public LiveData<ClientBookingModel> getHistoryBookingData(String authToken){
//        if (clientHistoryBookingMLD == null){
//            clientHistoryBookingMLD = bookingService.clientHistoryBookingRequest(authToken);
//        }
//        return clientHistoryBookingMLD;
//    }

    public LiveData<ClientBookingModel> postStartDateTime(String authToken,int id , String start_datetime){
        if (clientBookingMLD == null){
            clientBookingMLD = bookingService.start_datetimeRequest(authToken,id ,start_datetime);
        }
        return clientBookingMLD;
    }

    public LiveData<ClientBookingModel> postEndDateTime(String authToken, int id, String end_datetime){
        if (clientBookingMLD == null){
            clientBookingMLD = bookingService.end_datetimeRequest(authToken, id, end_datetime);
        }
        return clientBookingMLD;
    }
}
