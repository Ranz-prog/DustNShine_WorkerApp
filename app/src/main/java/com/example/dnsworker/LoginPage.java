package com.example.dnsworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.API.APIClient;
import com.example.LogIn.LogInRequest;
import com.example.LogIn.LogInResponse;

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
                    //Toast.makeText(LoginPage.this, "Username or Password is Required", Toast.LENGTH_SHORT);
                    signin_email.setError("Email is required");

                }
                else if (TextUtils.isEmpty(signin_password.getText().toString())){
                    signin_password.setError("Password is required");
                }
                else {
                    //proceed to log in
                    login();
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_LONG);

                }
            }
        });
    }

    //Method for Log in
    public void login(){
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setEmail(signin_email.getText().toString());
        logInRequest.setPassword(signin_password.getText().toString());

        Call<LogInResponse> logInResponseCall = APIClient.getUserService().userLogin(logInRequest);
        logInResponseCall.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT);

                    LogInResponse logInResponse = response.body();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginPage.this, MainMenu.class));
                            finish();
                        }
                    }, 700);

                }
                else {
                    Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Throwable: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT);

            }
        });
    }
}