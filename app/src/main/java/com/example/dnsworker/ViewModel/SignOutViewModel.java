package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dnsworker.LogIn.LogInResponse;

import java.util.Map;

public class SignOutViewModel extends ViewModel {
    private UserAPIRepo userapiRepo;
    private MutableLiveData<Map<String, String>> mutableLiveData;

    public SignOutViewModel(){
        userapiRepo = new UserAPIRepo();
    }

    public LiveData<Map<String, String>> getSignoutResponse(String retrievedToken){

        if(mutableLiveData==null){
            mutableLiveData = userapiRepo.signoutRequest(retrievedToken);
        }
        return mutableLiveData;
    }
}
