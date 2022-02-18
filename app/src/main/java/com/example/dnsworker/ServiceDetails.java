package com.example.dnsworker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.adapter.SLAdapter.SLAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ServiceDetails extends Activity {

    private LinearLayout serviceBackButton;
    private Button doneWorkBtn;
    private TextView customerName, customerMobileNumber, totalCost, customerLocation;
    private Dialog dialog;

    private RecyclerView serviceRecyclerView;
    private SharedPreferences preferences;
    private SharedPreferences servicePreference;
    private SLAdapter slAdapter;
    Service[] serviceList;

    private String first_name, last_name, mobile_number, location, total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        serviceBackButton = findViewById(R.id.service_arrowback);
        doneWorkBtn = findViewById(R.id.doneWorkButton);
        dialog = new Dialog(this);

        customerName = findViewById(R.id.service_customerNameTV);
        customerMobileNumber = findViewById(R.id.service_customerMobileNumberTV);
        customerLocation = findViewById(R.id.service_customerLocationTV);
        totalCost = findViewById(R.id.totalCost);

        serviceRecyclerView = findViewById(R.id.sdRecycler);
        serviceRecyclerView.setHasFixedSize(true);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slAdapter = new SLAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(slAdapter);

        loadData();

        customerName.setText(first_name + " " + last_name);
        customerLocation.setText(location);
        customerMobileNumber.setText(mobile_number);
        totalCost.setText(total);

        serviceBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void loadData(){

        //Shared Customer Data
        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        first_name = preferences.getString("first_name", null);
        last_name = preferences.getString("last_name", null);
        mobile_number = preferences.getString("mobile_number", null);
        location = preferences.getString("address", null);
        total = preferences.getString("total", null);

        //Shared Customer Service List
        servicePreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = servicePreference.getString("SERVICE_LIST", null);

        Gson gson = new Gson();
        Type type = new TypeToken<Service[]>(){}.getType();

        gson.fromJson(jsonString, type);
        serviceList = gson.fromJson(jsonString, type);
        slAdapter.setSLData(serviceList);
    }
    private void openDialog(){
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnDone = (Button) dialog.findViewById(R.id.confirmButton);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
