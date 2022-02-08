package com.example.dnsworker.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnsworker.LogIn.LogInResponse;

public class SignInViewModel extends ViewModel {
    private UserAPIRepo signInRepo;
    private MutableLiveData<LogInResponse> mutableLiveData;

    public SignInViewModel(){
        signInRepo = new UserAPIRepo();
    }

    public LiveData<LogInResponse> getSigninResponse(String email, String password){

        if(mutableLiveData==null){
            mutableLiveData = signInRepo.signinRequest(email,password);
        }
        return mutableLiveData;
    }

}
