package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnsworker.LogIn.LogInResponse;
import com.example.dnsworker.Service.UserService;
import com.example.dnsworker.ViewModel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class LoginPage extends AppCompatActivity {

    private Button signin_btnSignin;
    private TextInputEditText signin_email, signin_password;
    //private TextInputLayout signinpassword;
    UserViewModel userViewModel;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        signin_email = findViewById(R.id.signin_Email_ET);
        signin_password = findViewById(R.id.signin_Password_ET);
        signin_btnSignin = findViewById(R.id.signup_buttonSignin);

        dialog = new Dialog(this);
        Log.d("TAG", "LOGIN PAGE!");
        userViewModel = new UserViewModel();

        //Proceed to Login
        signin_btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validations for Email and Password
                if (TextUtils.isEmpty(signin_email.getText().toString())) {
//                    signin_email.setError("Email is required");
                    warningDialogEmpty();

                } else if (TextUtils.isEmpty(signin_password.getText().toString())) {
                    warningDialogEmpty();

                } else {
                    //proceed to log in
                    login(signin_email.getText().toString(), signin_password.getText().toString());
                    String workerEmail = signin_email.getText().toString();
                    String workerPass = signin_password.getText().toString();
                    SharedPreferences emailPref = getSharedPreferences("WORKER_EMAIL", MODE_PRIVATE);
                    emailPref.edit().putString("worker_email", workerEmail).apply();
                    emailPref.edit().putString("worker_password", workerPass).apply();
                }
            }
        });

        //Checking the Status Code with Toast Warning
        userViewModel.userService.setOnSigninListener(new UserService.SigninCallback() {
            @Override
            public void signinCallback(Integer statusCode, LogInResponse response) {
                if (statusCode == 200) {

                    if (response.getData().getUser().getRoles()[0].getName().equals("worker")){
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
                    }
                    else{
                        warningDialog();
                        Log.d(TAG, "signinCallback: Not Worker");
                    }
                } else if (statusCode == 401) {
                    warningDialog();
                    Log.d(TAG, "signinCallback: Invalid Credentials, Try again");
                } else {
                    warningDialog();
                    Log.d(TAG, "signinCallback: Login Failed");

                }
            }

            @Override
            public void signinErrorCallBack() {
                warningDialog();
                Log.d(TAG, "signinErrorCallBack: signinErrorCallback ====> ");
            }
        });
    }

    private void warningDialogEmpty(){
        dialog.setContentView(R.layout.dialog_error_email);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnDone = (TextView) dialog.findViewById(R.id.dismissButton);
        TextView warningMessage = (TextView) dialog.findViewById(R.id.warningMessage);
        warningMessage.setText("Email or Password is empty. Please try again");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void warningDialog(){

        dialog.setContentView(R.layout.dialog_error_email);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnDone = (TextView) dialog.findViewById(R.id.dismissButton);
        TextView warningMessage = (TextView) dialog.findViewById(R.id.warningMessage);
        warningMessage.setText("Invalid Credentials, Try again");
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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