package com.example.dnsworker;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.Service;

import com.example.dnsworker.adapter.ServiceListAdapter.ServiceListAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;



public class Feedback extends AppCompatActivity {

    private ImageView arrowback;
    private TextView clientName, clientAddress, clientContact,
            clientTotal, clientSchedule, clientComment, clientRatingValue, clientNote;
    private SharedPreferences historyPreferences, serviceHistoryPreference;
    private String first_name, last_name, mobile_number, location, total, schedule, comment;
    private RecyclerView historyRecycler;
    private RatingBar ratingBar;

    Service[] serviceHistoryList;
    ServiceListAdapter serviceListHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        arrowback = findViewById(R.id.ic_arrowbackFeeback);
        clientName = findViewById(R.id.history_customerName);
        clientAddress = findViewById(R.id.history_clientAddress);
        clientContact = findViewById(R.id.history_clientContactNumber);
        clientTotal = findViewById(R.id.history_totalCost);
        clientSchedule = findViewById(R.id.history_schedule);
        clientComment = findViewById(R.id.history_clientComment);
        clientRatingValue = findViewById(R.id.ratingValue);
        clientNote = findViewById(R.id.history_noteTV);
        ratingBar = findViewById(R.id.ratingBar);
        historyRecycler = findViewById(R.id.history_recyclerView);

        historyRecycler.setHasFixedSize(true);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));

        serviceListHistoryAdapter = new ServiceListAdapter(this, serviceHistoryList);
        historyRecycler.setAdapter(serviceListHistoryAdapter);

        loadFeedbackData();
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFeedbackData(){

        //Shared pref for History Details
        historyPreferences = getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        first_name = historyPreferences.getString("first_name", null);
        last_name = historyPreferences.getString("last_name", null);
        mobile_number = historyPreferences.getString("mobile_number", null);
        location = historyPreferences.getString("address", null);
        total = historyPreferences.getString("total", null);
        schedule = historyPreferences.getString("sched_datetime", null);
        comment = historyPreferences.getString("comment", null);
        String rating = historyPreferences.getString("ratings", null);
        String note = historyPreferences.getString("note", null);
        int id = historyPreferences.getInt("id", 0);

        clientName.setText(first_name + " " + last_name);
        clientAddress.setText(location);
        clientContact.setText(mobile_number);
        clientTotal.setText(total);
        clientSchedule.setText(schedule);
        clientNote.setText(note);

        Log.d(TAG, "loadFeedbackData: CommentIto " + comment);
        Log.d(TAG, "loadFeedbackData: RatingIto " + rating);

        if((comment == null) && (rating == null)){
            clientComment.setText("");
            clientRatingValue.setText(0);
            ratingBar.setRating(0);
        }
        else{
            clientComment.setText(comment);
            clientRatingValue.setText(rating);
            ratingBar.setRating(Float.parseFloat(rating));
        }

        Log.d(TAG, "loadFeedbackData: USER ID==> " + id);


        serviceHistoryPreference = getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = serviceHistoryPreference.getString("SERVICE_LIST", null);

        Gson gson = new Gson();
        Type type = new TypeToken<Service[]>(){}.getType();

        gson.fromJson(jsonString, type);
        serviceHistoryList = gson.fromJson(jsonString, type);
        serviceListHistoryAdapter.setServiceList(serviceHistoryList);


    }

}
