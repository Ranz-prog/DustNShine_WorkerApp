package com.example.dnsworker.ViewModel;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.LoginPage;
import com.example.dnsworker.Model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPIRepo {


    /*For Signout Request*/
    public MutableLiveData<Map<String, String>> signoutRequest(String retrievedToken){
        final MutableLiveData<Map<String, String>> signoutMutableData = new MutableLiveData<>();

        Call<Map<String, String>> logoutRequest =  APIClient.getUserService().userLogout("Bearer " + retrievedToken);
        logoutRequest.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){

                    signoutMutableData.setValue(response.body());

                    Log.d(TAG, "onResponse: " +  response.body().get("message"));

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

//    For Login Request
    public MutableLiveData<LogInResponse> signinRequest(String email, String password){

        final MutableLiveData<LogInResponse> mutableLiveData = new MutableLiveData<>();

        // For input data
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(email);
        logInRequest.setPassword(password);

        Call<LogInResponse> logInResponseCall = APIClient.getUserService().userLogin(logInRequest);

        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {


                Log.d(TAG, "onResponse: Status Code ===>" +  response.code());
                //conditional statement for the call
                if (response.isSuccessful()){
                    //Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    mutableLiveData.setValue(response.body());

                }
                else {
                    Log.d("TAG", "FAILED: " + "ERROR API CALL");

                    //Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                //Toast.makeText(LoginPage.this, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return mutableLiveData;
    }


    //Getting User Information
    public MutableLiveData<User> userDataRequest(String authToken){

        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

        Call<User> userResponseCall = APIClient.getUserService().getUserData("Bearer "+ authToken);

        userResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    userMutableLiveData.setValue(response.body());
                }
                else {
                    Log.d("TAG", "FAILED: " + "ERROR API CALL");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        //token


        return userMutableLiveData;

    }
}
