package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Feedback;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Review;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.HistoryAdapter;
import com.google.android.gms.common.api.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnClickBookingListener {

    private RecyclerView bookingRecycler;
    private View view;
    private ArrayList<ClientBookData> clientHistoryDataList;
    private Service[] serviceList;
    private Review[] historyReviews;
    private HistoryAdapter historyAdapter;
    private ClientBookingViewModel clientBookingVM;
    private String retrievedToken;
    private SharedPreferences historyPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking, container, false);

        bookingRecycler = view.findViewById(R.id.booking_RecyclerView);
        bookingRecycler.setHasFixedSize(true);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(getContext(), clientHistoryDataList, this);
        bookingRecycler.setAdapter(historyAdapter);

        TextView noResult = view.findViewById(R.id.emptyHistoryTV);



        //Passed Data from shared Pref
        historyPreferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = historyPreferences.getString("TOKEN", null);

        clientBookingVM = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);
        clientBookingVM.getHistoryBookingData(retrievedToken).observe(getActivity(), new Observer<ClientBookingModel>() {
                    @Override
                    public void onChanged(ClientBookingModel clientBookingModel) {

                        if (clientBookingModel != null) {
                            ArrayList<ClientBookData> clientHistoryBookData = clientBookingModel.getData();
                            clientHistoryDataList = clientHistoryBookData;
                            historyAdapter.setHistoryModelList(clientHistoryDataList);
                            Log.d(TAG, "onChanged: DATA HERE ======>" + clientHistoryBookData);
                            Log.d(TAG, "onChanged: Count===>" + clientHistoryBookData.size());
                        } else {
                            //if No Data retrieved
                            noResult.setVisibility(View.VISIBLE);
                        }
                    }
                });


        return view;

    }

    @Override
    public void onClickBooking(int position) {

        loadHistoryData(position);
        Intent intent = new Intent(getActivity(), Feedback.class);
        startActivity(intent);
    }

    private void loadHistoryData(int position){
        //Specific Customer Details
        String first_name = clientHistoryDataList.get(position).getCustomer().getFirstName();
        String last_name = clientHistoryDataList.get(position).getCustomer().getLastName();
        String mobile_number = clientHistoryDataList.get(position).getCustomer().getMobileNumber();
        String address = clientHistoryDataList.get(position).getAddress();
        String schedule = clientHistoryDataList.get(position).getSched_datetime();
        String email = clientHistoryDataList.get(position).getCustomer().getEmail();
        String note = clientHistoryDataList.get(position).getNote();
        int status = clientHistoryDataList.get(position).getStatus();
        int id = (int) clientHistoryDataList.get(position).getID();
        double totalCost = clientHistoryDataList.get(position).getTotal();
        serviceList = clientHistoryDataList.get(position).getServices();

        SharedPreferences preferences = getActivity().getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);

        historyReviews = clientHistoryDataList.get(position).getReviews();

        String commentVal = "";
        double ratingVal = 0.0;

        if (historyReviews.length != 0){
            String comment = historyReviews[0].getComment();
            double rating = historyReviews[0].getRating();
            preferences.edit().putString("comment", comment).apply();
            preferences.edit().putString("ratings", String.valueOf(rating)).apply();

            Log.d(TAG, "loadHistoryData: COMMENT HERE ==>" + comment);
            Log.d(TAG, "loadHistoryData: RATING HERE ==>" + rating);
        }
        else{
            preferences.edit().putString("comment", commentVal).apply();
            preferences.edit().putString("ratings", String.valueOf(ratingVal)).apply();
        }

        preferences.edit().putString("first_name", first_name).apply();
        preferences.edit().putString("last_name", last_name).apply();
        preferences.edit().putString("mobile_number", mobile_number).apply();
        preferences.edit().putString("address", address).apply();
        preferences.edit().putString("sched_datetime", schedule).apply();
        preferences.edit().putString("total", String.valueOf(totalCost)).apply();
        preferences.edit().putString("email", email).apply();
        preferences.edit().putString("status", String.valueOf(status));
        preferences.edit().putInt("id", id).apply();
        preferences.edit().putString("note", note).apply();

        Gson gson = new Gson();

        //Service Details of the customer
        SharedPreferences servicePreference = getActivity().getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = gson.toJson(serviceList);
        servicePreference.edit().putString("SERVICE_LIST", jsonString).commit();
    }

}

