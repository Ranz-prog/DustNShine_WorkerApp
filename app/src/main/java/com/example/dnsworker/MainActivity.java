package com.example.dnsworker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("AUTH_TOKEN",MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", null);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(token == null){
                    Intent intent = new Intent(MainActivity.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                    finish();
                }
            }
        }, 3000);

    }
}
