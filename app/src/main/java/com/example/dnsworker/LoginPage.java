package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Application;
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
import com.example.dnsworker.ViewModel.SignInViewModel;
import com.example.dnsworker.ViewModel.UserAPIRepo;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private Button signin_btnSignin;
    private TextInputEditText signin_email, signin_password;

    SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signin_email = findViewById(R.id.signin_Email_ET);
        signin_password = findViewById(R.id.signin_Password_ET);
        signin_btnSignin = findViewById(R.id.signup_buttonSignin);

        Log.d("TAG", "LOGIN PAGE!");
        signInViewModel = new SignInViewModel();


        signin_btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validations for Email and Password
                if (TextUtils.isEmpty(signin_email.getText().toString())) {
                    signin_email.setError("Email is required");

                } else if (TextUtils.isEmpty(signin_password.getText().toString())) {
                    signin_password.setError("Password is required");
                } else {
                    //proceed to log in
                    login(signin_email.getText().toString(), signin_password.getText().toString());
                    //Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_LONG).show();
                }
            }
        });



      signInViewModel.setOnSigninListener(new SignInViewModel.SigninCallback() {
          @Override
          public void signinCallback(Integer statusCode, LogInResponse response) {
              if(statusCode == 200){
                  Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                  String token = response.getData().getToken();
                  SharedPreferences preferences = getSharedPreferences("AUTH_TOKEN", MODE_PRIVATE);
                  preferences.edit().putString("TOKEN", token).apply();
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          startActivity(new Intent(LoginPage.this, MainMenu.class));
                          finish();
                      }
                  }, 700);
                  Log.d(TAG, "signinCallback: StatusCode ====>");
              }
              else if (statusCode == 401){
                  Toast.makeText(getApplicationContext(), "Invalid Credentials, Try Again", Toast.LENGTH_SHORT).show();
              }
              else{
                  Toast.makeText(getApplicationContext(), "Failed, Try again", Toast.LENGTH_SHORT).show();
              }
          }
      });
    }

    void login(String email, String password) {

        signInViewModel.getSignInRes(email, password);
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