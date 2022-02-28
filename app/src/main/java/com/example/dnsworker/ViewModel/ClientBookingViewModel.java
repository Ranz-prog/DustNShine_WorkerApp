package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Service.BookingService;

import java.util.Map;


public class ClientBookingViewModel extends ViewModel{

    private BookingService bookingService;
    private MutableLiveData<ClientBookingModel> clientBookingMLD;

    public ClientBookingViewModel(){
        bookingService = new BookingService();
    }

    public LiveData<ClientBookingModel> getClientBookingData(String authToken){
        if(clientBookingMLD == null){
            clientBookingMLD = bookingService.clientBookingRequest(authToken);
        }
        return clientBookingMLD;
    }

    public LiveData<ClientBookingModel> postTimeAndDate(String authToken, int id , Map<String, String> datetime){
        if (clientBookingMLD == null){
            clientBookingMLD = bookingService.timedateRequest(authToken, id ,datetime);
        }
        return clientBookingMLD;
    }
}
