package com.example.dnsworker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity {

    private LinearLayout arrowback;
    private TextView clientName, clientAddress, clientContact;
    private SharedPreferences historyPreferences;
    private String first_name, last_name, mobile_number, location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        arrowback = findViewById(R.id.ic_arrowbackFeeback);
        clientName = findViewById(R.id.history_customerName);
        clientAddress = findViewById(R.id.history_clientAddress);
        clientContact = findViewById(R.id.history_clientContactNumber);

        loadFeedbackData();
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFeedbackData(){

        //Shared pref for Customer Details
        historyPreferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        first_name = historyPreferences.getString("first_name", null);
        last_name = historyPreferences.getString("last_name", null);
        mobile_number = historyPreferences.getString("mobile_number", null);
        location = historyPreferences.getString("address", null);


//        servicePreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
//        String jsonString = servicePreference.getString("SERVICE_LIST", null);
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<Service[]>(){}.getType();
//
//        gson.fromJson(jsonString, type);
//        serviceList = gson.fromJson(jsonString, type);
//        serviceListAdapter.setServiceList(serviceList);
//
        clientName.setText(first_name + " " + last_name);
        clientAddress.setText(location);
        clientContact.setText(mobile_number);

    }
}
