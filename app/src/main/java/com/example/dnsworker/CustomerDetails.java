package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CustomerDetails extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView arrowBack;
    private AppCompatButton startWorkButton, onGoingWorkButton;
    private ImageView button_VideoCall;
    private GoogleMap map;
    private RecyclerView serviceRecyclerView;
    private TextView fullnameTV, mobilenumberTV, addressTV, scheduleTV, noteTV;
    private String first_name, last_name, mobile_number, location, client_email,
            schedule, note, authToken, worker_email, worker_password;
    private Dialog dialog;
    private AppCompatButton btnYes, btnNo;

    Service[] serviceList;
    ClientBookData[] customerList;
    ServiceListAdapter serviceListAdapter;
    SharedPreferences preferences, servicePreference, authPref;
    ClientBookingViewModel clientBookingVM;

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
        scheduleTV = findViewById(R.id.c_details_schedule);
        noteTV = findViewById(R.id.customer_noteTV);
        button_VideoCall = findViewById(R.id.btnVideoCall);

        serviceRecyclerView = findViewById(R.id.service_RecyclerView);
        serviceRecyclerView.setHasFixedSize(true);
        serviceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        serviceListAdapter = new ServiceListAdapter(this, serviceList);
        serviceRecyclerView.setAdapter(serviceListAdapter);

        authPref = getSharedPreferences("AUTH_TOKEN", MODE_PRIVATE);
        authToken = authPref.getString("TOKEN", null);

        dialog = new Dialog(this);

        clientBookingVM = new ClientBookingViewModel();
        loadData();

        //MapView
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);


        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);

        startWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertStartDialog();

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
                Intent intent = new Intent(CustomerDetails.this, MainMenu.class);
                startActivity(intent);
                finish();

            }
        });
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

    private void alertStartDialog(){
        dialog.setContentView(R.layout.dialog_start_work);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnNo = dialog.findViewById(R.id.noStartButton);
        btnYes = dialog.findViewById(R.id.yesStartButton);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStartTimeAndDate();
                Intent intent = new Intent(CustomerDetails.this, ServiceDetails.class);
                startActivity(intent);
                dialog.dismiss();
                finish();

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void loadData() {

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
        Type type = new TypeToken<Service[]>() {
        }.getType();

        gson.fromJson(jsonString, type);
        serviceList = gson.fromJson(jsonString, type);
        serviceListAdapter.setServiceList(serviceList);

        fullnameTV.setText(first_name + " " + last_name);
        addressTV.setText(location);
        mobilenumberTV.setText(mobile_number);
        scheduleTV.setText(schedule);
        noteTV.setText(note);
    }

    private void postStartTimeAndDate() {

        preferences.edit().putInt("status", 2).apply();
        int id = preferences.getInt("id", 0);
        Log.d(TAG, "postTimeAndDate: ID===>" + id);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        String time = format.format(calendar.getTime());

        Log.d(TAG, "postTimeAndDate: TOKEN" + authToken);

        clientBookingVM.postStartDateTime(authToken, id, time).observe(this, new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {
                Log.d(TAG, "onChanged: DATA ===>" + time + "/n" + id);

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        preferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        String clientAdd = preferences.getString("address", null);

        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocationName(clientAdd, 1);
            if (addressList !=null){
                double locLatitude = addressList.get(0).getLatitude();
                double locLongitude = addressList.get(0).getLongitude();

                Log.d(TAG, "onMapReady: longitude " + locLongitude);
                Log.d(TAG, "onMapReady: latitude " + locLatitude );


                map = googleMap;
                LatLng Address = new LatLng(locLatitude,locLongitude);
                map.addMarker(new MarkerOptions().position(Address).title(clientAdd));
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                map.moveCamera(CameraUpdateFactory.newLatLng(Address));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void videoCall(View view) {
        onJitsiMeet();
    }

    private void onJitsiMeet() {

        SharedPreferences jitsiPref = getSharedPreferences("CUSTOMER_DATA", MODE_PRIVATE);
        SharedPreferences emailPref = getSharedPreferences("WORKER_EMAIL", MODE_PRIVATE);
        client_email = jitsiPref.getString("email", null);
        worker_email = emailPref.getString("worker_email", null);
        worker_password = emailPref.getString("worker_password", null);


        Random random = new Random();
        int randomVal = random.nextInt(10000);

        Log.d(TAG, "onJitsiMeet: RANDOM CODE ===>" + randomVal);

        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si ");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();

            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String url = "https://meet.jit.si/";
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(String.valueOf(randomVal))
                .setServerURL(buildURL(url))
                .setWelcomePageEnabled(false)
                .build();

        final String workerEmail = worker_email;
        final String workerPassword = worker_password;

        String messageToSend = "Hello and good day, this is an auto generated message. This is your Room Code:  '" + randomVal + "' or click this Link: ";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "t1");
        props.put("mail.smtp.timeout", "t2");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(workerEmail, workerPassword);
                    }

                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(workerEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(client_email));
            message.setText(messageToSend + " " + url + randomVal);
            //Transport.send(message);
            new SendMail().execute(message);
            Toast.makeText(getApplicationContext(), "Email sent successfully", Toast.LENGTH_LONG).show();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        JitsiMeetActivity.launch(CustomerDetails.this, options);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    private class SendMail extends AsyncTask<Message, String, String> {


        @Override
        protected String doInBackground(Message... messages) {
            try {

                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Success")) {

            }
        }
    }

    private @Nullable
    URL buildURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CustomerDetails.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

