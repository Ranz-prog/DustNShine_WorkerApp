package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.widget.Toast;

import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Service.UserService;
import com.example.dnsworker.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {

    private Button signin_btnSignin;
    private TextInputEditText signin_email, signin_password;

    //SignInViewModel signInViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signin_email = findViewById(R.id.signin_Email_ET);
        signin_password = findViewById(R.id.signin_Password_ET);
        signin_btnSignin = findViewById(R.id.signup_buttonSignin);

        Log.d("TAG", "LOGIN PAGE!");
        userViewModel = new UserViewModel();

        //Proceed to Login
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
                }
            }
        });

        //Checking the Status Code with Toast Warning
        userViewModel.userService.setOnSigninListener(new UserService.SigninCallback() {
            @Override
            public void signinCallback(Integer statusCode, LogInResponse response) {
                if (statusCode == 200) {
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
                } else if (statusCode == 401) {
                    Toast.makeText(getApplicationContext(), "Invalid Credentials, Try Again", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //passing the email and password into the user view model
    void login(String email, String password) {
        userViewModel.getSignInRes(email, password);
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
        Toast.makeText(this, "Please tap BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}