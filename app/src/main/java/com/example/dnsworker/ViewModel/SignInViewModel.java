package com.example.dnsworker.ViewModel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInViewModel extends ViewModel {
    public UserAPIRepo signInRepo;
    private MutableLiveData<LogInResponse> mutableLiveData;


    public SignInViewModel() {
        signInRepo = new UserAPIRepo();
        //callback = null;
    }

    public void getSignInRes(String email, String passwor) {
        signReq(email, passwor);
    }

    public void signReq(String email, String password) {
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(email);
        logInRequest.setPassword(password);

        Call<LogInResponse> logInResponseCall = APIClient.getUserService().userLogin(logInRequest);

        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                // Trigger
                callback.signinCallback(response.code(), response.body());

            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                //Toast.makeText(activity, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: STATUS CODE ERROR =====>" );
            }
        });
    }

    SigninCallback callback;


    public interface SigninCallback {
        void signinCallback(Integer statusCode, LogInResponse response);
    }

    public void setOnSigninListener(SigninCallback signinCallback){
        callback = signinCallback;
    }

}

