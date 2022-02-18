package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dnsworker.Model.ClientBookingModel.Service;
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

public class CustomerDetails extends AppCompatActivity implements OnMapReadyCallback {

    private LinearLayout arrowBack;
    private Button startWorkButton;
    private GoogleMap map;
    private RecyclerView serviceRecyclerView;
    private TextView fullname, mobilenumber, address;
    private String first_name, last_name, mobile_number, location;

    Service[] serviceList;
    ServiceListAdapter serviceListAdapter;
    SharedPreferences preferences, servicePreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        arrowBack = findViewById(R.id.ic_arrowbackCustomerDetails);
        startWorkButton = findViewById(R.id.startWorkButton);

        //RecyclerView for Service List
        serviceRecyclerView = findViewById(R.id.service_RecyclerView);
        serviceRecyclerView.setHasFixedSize(true);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        serviceListAdapter = new ServiceListAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(serviceListAdapter);


        fullname = findViewById(R.id.c_details_fullname);
        mobilenumber = findViewById(R.id.c_details_mobileNumber);
        address = findViewById(R.id.c_details_location);

        loadData();

        //MapView
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        startWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, ServiceDetails.class);
                startActivity(intent);
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

        servicePreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = servicePreference.getString("SERVICE_LIST", null);


        Gson gson = new Gson();
        Type type = new TypeToken<Service[]>(){}.getType();

        gson.fromJson(jsonString, type);
        serviceList = gson.fromJson(jsonString, type);
        serviceListAdapter.setServiceList(serviceList);

        fullname.setText(first_name + " " + last_name);
        address.setText(location);
        mobilenumber.setText(mobile_number);

        Log.d(TAG, "onCreate: firstname ======>" + first_name);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng Address = new LatLng(16.0471126,120.3424204);
        map.addMarker(new MarkerOptions().position(Address).title("UPANG"));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.moveCamera(CameraUpdateFactory.newLatLng(Address));
    }

    
}