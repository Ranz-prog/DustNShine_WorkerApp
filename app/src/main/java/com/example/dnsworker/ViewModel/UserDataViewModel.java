package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dnsworker.Model.User;

public class UserDataViewModel {
    private UserAPIRepo userAPIRepo;
    private MutableLiveData<User> userMutableLiveData;

    public UserDataViewModel(){
        userAPIRepo = new UserAPIRepo();
    }

    public LiveData<User> getUserDataResponse(String authToken){

        if(userMutableLiveData==null){
            userMutableLiveData = userAPIRepo.userDataRequest(authToken);
        }
        return userMutableLiveData;
    }
}
