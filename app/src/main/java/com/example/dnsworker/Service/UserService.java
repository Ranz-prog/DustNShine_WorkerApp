package com.example.dnsworker.Service;

import static android.content.ContentValues.TAG;
import android.util.Log;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.lifecycle.MutableLiveData;
import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.User.User;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

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
                Log.d(TAG, "onFailure: " + t);
            }
        });

        return userMutableLiveData;
    }

    public void signinRequest(String email, String password) {
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
                callback.signinErrorCallBack();
                Log.d(MotionEffect.TAG, "onFailure: STATUS CODE ERROR =====>" + t);
            }
        });
    }

    SigninCallback callback;

    public interface SigninCallback {
        void signinCallback(Integer statusCode, LogInResponse response);
        void signinErrorCallBack();
    }

    public void setOnSigninListener(SigninCallback signinCallback){
        callback = signinCallback;
    }

}


