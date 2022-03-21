package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.SLAdapter.SLAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServiceDetails extends AppCompatActivity {

    private ImageView serviceBackButton;
    private Button doneWorkBtn;
    private TextView customerName, customerMobileNumber, totalCost, customerLocation, workStatus, schedule, note;
    private Dialog dialog;
    private RecyclerView serviceRecyclerView;
    private SharedPreferences preferences;
    private SharedPreferences servicePreference;
    private SLAdapter slAdapter;
    private Service[] serviceList;
    private ClientBookingViewModel clientBVM;

    private String first_name, last_name, mobile_number, location, total, status, sched, noteValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        serviceBackButton = findViewById(R.id.service_arrowback);
        doneWorkBtn = findViewById(R.id.doneWorkButton);
        customerName = findViewById(R.id.service_customerNameTV);
        customerMobileNumber = findViewById(R.id.service_customerMobileNumberTV);
        customerLocation = findViewById(R.id.service_customerLocationTV);
        totalCost = findViewById(R.id.totalCost);
        workStatus = findViewById(R.id.statusTV);
        schedule = findViewById(R.id.service_schedule);
        note = findViewById(R.id.service_noteTV);

        clientBVM = new ClientBookingViewModel();

        dialog = new Dialog(this);

        serviceRecyclerView = findViewById(R.id.sdRecycler);
        serviceRecyclerView.setHasFixedSize(true);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slAdapter = new SLAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(slAdapter);

        loadData();

        serviceBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ServiceDetails.this, CustomerDetails.class);
//                startActivity(intent);
                finish();
            }
        });

        doneWorkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEndTimeAndDate();
                confirmationDialog();
            }
        });
    }

    private void loadData() {

        //Shared Customer Data
        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        first_name = preferences.getString("first_name", null);
        last_name = preferences.getString("last_name", null);
        mobile_number = preferences.getString("mobile_number", null);
        location = preferences.getString("address", null);
        total = preferences.getString("total", null);
        sched = preferences.getString("sched_datetime", null);
        noteValue = preferences.getString("note", null);
        int status = preferences.getInt("status", 0);
        //Shared Customer Service List
        servicePreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = servicePreference.getString("SERVICE_LIST", null);
        Log.d("TAG", String.valueOf(status));
        Gson gson = new Gson();
        Type type = new TypeToken<Service[]>() {
        }.getType();

        gson.fromJson(jsonString, type);
        serviceList = gson.fromJson(jsonString, type);
        slAdapter.setSLData(serviceList);

        customerName.setText(first_name + " " + last_name);
        customerLocation.setText(location);
        customerMobileNumber.setText(mobile_number);
        totalCost.setText(total);
        workStatus.setText("On Going");
        schedule.setText(sched);
        note.setText(noteValue);
    }

    private void postEndTimeAndDate(){

        SharedPreferences authPref = getSharedPreferences("AUTH_TOKEN", MODE_PRIVATE);
        String authToken = authPref.getString("TOKEN", null);
        int id = preferences.getInt("id", 0);
        Log.d(TAG, "postTimeAndDate: ID===>" + id);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        String time =  format.format(calendar.getTime());

        Log.d(TAG, "postTimeAndDate: TOKEN" + authToken);

        clientBVM.postEndDateTime(authToken, id, time).observe(this, new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {
                Log.d(TAG, "onChanged: ENDTIME ===>" + time + "/n" + id);
            }
        });
        Log.d(TAG, "getTimeAndDate: =====>" + time);

    }

    private void confirmationDialog() {
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView btnDone = (TextView) dialog.findViewById(R.id.confirmButton);
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
