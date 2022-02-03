package com.example.dnsworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.LogIn.LogInRequest;
import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Model.MData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private Button signin_btnSignin;
    private EditText signin_email, signin_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signin_email = findViewById(R.id.signin_Email_ET);
        signin_password = findViewById(R.id.signin_Password_ET);
        signin_btnSignin = findViewById(R.id.signup_buttonSignin);

        signin_btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validations for Email and Password
                if (TextUtils.isEmpty(signin_email.getText().toString())){
                    signin_email.setError("Email is required");

                }
                else if (TextUtils.isEmpty(signin_password.getText().toString())){
                    signin_password.setError("Password is required");
                }
                else {
                    //proceed to log in
                    login();
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    //Method for Log in and passing the token for authentication
    public void login(){

        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(signin_email.getText().toString());
        logInRequest.setPassword(signin_password.getText().toString());

        Call<LogInResponse> logInResponseCall = APIClient.getUserService().userLogin(logInRequest);

        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {

                //conditional statement for the call
                if (response.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "RESULT " + response.body());

                    LogInResponse logInResponse = response.body();
                    MData data =  logInResponse.getData();

                    //Save token temporarily on shared pref
                    String token = data.getToken();
                    SharedPreferences preferences = getSharedPreferences("AUTH_TOKEN", MODE_PRIVATE);
                    preferences.edit().putString("TOKEN", token).apply();

                    Log.d("TAG", "TOKEN: " + data.getToken());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginPage.this, MainMenu.class));
                            finish();
                        }
                    }, 700);

                }
                else {
                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    //OnBack press to Exit the Application
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}