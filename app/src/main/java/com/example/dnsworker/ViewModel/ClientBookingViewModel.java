package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;

public class ClientBookingViewModel extends ViewModel{

    private UserAPIRepo userAPIRepo;
    private MutableLiveData<ClientBookingModel> clientBookingMLD;

    public ClientBookingViewModel(){
        userAPIRepo = new UserAPIRepo();
    }

    public LiveData<ClientBookingModel> getClientBookingData(String authToken){
        if(clientBookingMLD==null){
            clientBookingMLD = userAPIRepo.clientBookingRequest(authToken);
        }
        return clientBookingMLD;
    }
}
