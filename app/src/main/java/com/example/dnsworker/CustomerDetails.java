package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.ServiceListAdapter.ServiceListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerDetails extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView arrowBack;
    private Button startWorkButton, onGoingWorkButton;
    private GoogleMap map;
    private RecyclerView serviceRecyclerView;
    private TextView fullnameTV, mobilenumberTV, addressTV, scheduleTV, noteTV;
    private ImageView messageIcon;
    private String first_name, last_name, mobile_number, location, email, schedule, note;

    Service[] serviceList;
    ClientBookData[] customerList;
    ServiceListAdapter serviceListAdapter;
    SharedPreferences preferences, servicePreference, authPref;
    ClientBookingViewModel clientBookingVM;

    //Boolean isOnging = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        arrowBack = findViewById(R.id.ic_arrowbackCustomerDetails);
        startWorkButton = findViewById(R.id.startWorkButton);
        onGoingWorkButton = findViewById(R.id.onGoingWorkButton);
        fullnameTV = findViewById(R.id.c_details_fullname);
        mobilenumberTV = findViewById(R.id.c_details_mobileNumber);
        addressTV = findViewById(R.id.c_details_location);
        messageIcon = findViewById(R.id.messageIcon);
        scheduleTV = findViewById(R.id.c_details_schedule);
        noteTV = findViewById(R.id.customer_noteTV);
        //RecyclerView for Service List
        serviceRecyclerView = findViewById(R.id.service_RecyclerView);
        serviceRecyclerView.setHasFixedSize(true);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceListAdapter = new ServiceListAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(serviceListAdapter);

        clientBookingVM = new ClientBookingViewModel();
        loadData();



        //MapView
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);


        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);

        startWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStartTimeAndDate();
                Intent intent = new Intent(CustomerDetails.this, ServiceDetails.class);
                startActivity(intent);
                //getTimeAndDate();
                //onJitsiMeet();

            }
        });

        onGoingWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, ServiceDetails.class);
                startActivity(intent);
                finish();
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData(){

        //Shared pref for Customer Details
        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        first_name = preferences.getString("first_name", null);
        last_name = preferences.getString("last_name", null);
        mobile_number = preferences.getString("mobile_number", null);
        location = preferences.getString("address", null);
        schedule = preferences.getString("sched_datetime", null);
        note = preferences.getString("note", null);

        servicePreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = servicePreference.getString("SERVICE_LIST", null);

        Gson gson = new Gson();
        Type type = new TypeToken<Service[]>(){}.getType();

        gson.fromJson(jsonString, type);
        serviceList = gson.fromJson(jsonString, type);
        serviceListAdapter.setServiceList(serviceList);

        fullnameTV.setText(first_name + " " + last_name);
        addressTV.setText(location);
        mobilenumberTV.setText(mobile_number);
        scheduleTV.setText(schedule);
        noteTV.setText(note);
    }

    private void postStartTimeAndDate(){

        preferences.edit().putInt("status", 2).apply();

        authPref = getSharedPreferences("AUTH_TOKEN", MODE_PRIVATE);
        String authToken = authPref.getString("TOKEN", null);
        int id = preferences.getInt("id", 0);
        Log.d(TAG, "postTimeAndDate: ID===>" + id);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        String time =  format.format(calendar.getTime());

        Log.d(TAG, "postTimeAndDate: TOKEN" + authToken);

        clientBookingVM.postStartDateTime(authToken,id, time).observe(this, new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {
                Log.d(TAG, "onChanged: DATA ===>" + time + "/n" + id);

            }
        });

        Log.d(TAG, "getTimeAndDate: =====>" + time);

    }


    @Override
    protected void onResume() {
        super.onResume();
        int status = preferences.getInt("status", 0);
        Log.d("TAG", String.valueOf(status));
        if (status == 2) {
            startWorkButton.setEnabled(false);
            startWorkButton.setVisibility(View.INVISIBLE);
            onGoingWorkButton.setVisibility(View.VISIBLE);
            onGoingWorkButton.setEnabled(true);
        } else {
            startWorkButton.setEnabled(true);
            startWorkButton.setVisibility(View.VISIBLE);
            onGoingWorkButton.setVisibility(View.INVISIBLE);
            onGoingWorkButton.setEnabled(false);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        //Sharedpref lat and longt
        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        long longitude = preferences.getLong("longitude", 0);
        long latitude = preferences.getLong("latitude", 0);

        String clientAdd = preferences.getString("address", null);

        Log.d(TAG, "onMapReady: LOCATION LATT ==>" + latitude);
        Log.d(TAG, "onMapReady: LOCATION LONGT ==>" + longitude);
        Log.d(TAG, "onMapReady: LOCATION CLIENTADD  ==>" + clientAdd);


        map = googleMap;
        LatLng Address = new LatLng(16.0471126,120.3424204);
        //LatLng Address = new LatLng(latitude,longitude);
        //map.addMarker(new MarkerOptions().position(Address).title(clientAdd));
        map.addMarker(new MarkerOptions().position(Address).title("UPANG"));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.moveCamera(CameraUpdateFactory.newLatLng(Address));

    }



    //    private void onJitsiMeet(){
//
//        SharedPreferences jitsiPref = getSharedPreferences("CUSTOMER_DATA", MODE_PRIVATE);
//        email = jitsiPref.getString("email", null);
//
//        Random random = new Random();
//        int randomVal = random.nextInt(10000);
//
//        Log.d(TAG, "onJitsiMeet: RANDOM CODE ===>" + randomVal);
//
//
//
//    }


}