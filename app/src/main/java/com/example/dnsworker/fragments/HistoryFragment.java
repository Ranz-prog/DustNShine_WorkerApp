package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dnsworker.Feedback;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Review;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;
import com.example.dnsworker.Service.BookingService;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.HistoryAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

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

    SwipeRefreshLayout history_swipeRefreshLayout;
    ImageView emptyHistoryImage, noInternetHistoryImage;
    TextView noHistoryResult, noIntenetResultHistory, titleHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking, container, false);
        bookingRecycler = view.findViewById(R.id.booking_RecyclerView);
        bookingRecycler.setHasFixedSize(true);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(getContext(), clientHistoryDataList, this);
        bookingRecycler.setAdapter(historyAdapter);

        noHistoryResult = view.findViewById(R.id.emptyHistoryTV);
        emptyHistoryImage = view.findViewById(R.id.noResultLottieHistory);
        history_swipeRefreshLayout = view.findViewById(R.id.history_refresh);
        noIntenetResultHistory = view.findViewById(R.id.noInternetConnectionHistoryTV);
        noInternetHistoryImage = view.findViewById(R.id.noInternetHistoryImage);
        titleHistory = view.findViewById(R.id.titleTaskHist);

        if (!isConnected()){
            noInternetHistoryImage.setVisibility(View.VISIBLE);
            noIntenetResultHistory.setVisibility(View.VISIBLE);
            bookingRecycler.setVisibility(View.GONE);
            emptyHistoryImage.setVisibility(View.GONE);
            noHistoryResult.setVisibility(View.GONE);
            titleHistory.setVisibility(View.GONE);
        }
        else{
            noInternetHistoryImage.setVisibility(View.GONE);
            noIntenetResultHistory.setVisibility(View.GONE);
            bookingRecycler.setVisibility(View.VISIBLE);
            emptyHistoryImage.setVisibility(View.GONE);
            noHistoryResult.setVisibility(View.GONE);
        }

        //Passed Data from shared Pref
        historyPreferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = historyPreferences.getString("TOKEN", null);

        clientBookingVM = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);

        history_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        history_swipeRefreshLayout.setRefreshing(false);
                        onChangeMethodHistory();

                    }
                }, 1000);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");

        onChangeMethodHistory();
    }
    public boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo()!= null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onClickBooking(int position) {
        loadHistoryData(position);
        Intent intent = new Intent(getActivity(), Feedback.class);
        startActivity(intent);
    }

    private void onChangeMethodHistory() {
        clientBookingVM.getHistoryBookingData(retrievedToken);

        clientBookingVM.bookingService.setOnHistoryListener(new BookingService.BookingHistoryCallback() {
            @Override
            public void historyCallback(Integer statusCode, ClientBookingModel clientBookingModel) {

                if (clientBookingModel != null) {
                    ArrayList<ClientBookData> clientHistoryBookData = clientBookingModel.getData();
                    clientHistoryDataList = clientHistoryBookData;
                    historyAdapter.setHistoryModelList(clientHistoryDataList);

                    if (clientHistoryBookData.size() != 0) {
                        bookingRecycler.setVisibility(View.VISIBLE);
                        emptyHistoryImage.setVisibility(View.GONE);
                        noHistoryResult.setVisibility(View.GONE);
                        noInternetHistoryImage.setVisibility(View.GONE);
                        noIntenetResultHistory.setVisibility(View.GONE);

                    } else if(clientHistoryBookData.size() == 0){
                        titleHistory.setVisibility(View.VISIBLE);
                        bookingRecycler.setVisibility(View.GONE);
                        emptyHistoryImage.setVisibility(View.VISIBLE);
                        noHistoryResult.setVisibility(View.VISIBLE);
                        noInternetHistoryImage.setVisibility(View.GONE);
                        noIntenetResultHistory.setVisibility(View.GONE);
                    }

                    else {
                        bookingRecycler.setVisibility(View.GONE);
                        emptyHistoryImage.setVisibility(View.VISIBLE);
                        noHistoryResult.setVisibility(View.VISIBLE);
                        noInternetHistoryImage.setVisibility(View.GONE);
                        noIntenetResultHistory.setVisibility(View.GONE);
                    }

                } else {
                    //if No Data retrieved
                }
            }
        });
    }

    private void loadHistoryData(int position) {
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

        if (historyReviews.length != 0) {
            String comment = historyReviews[0].getComment();
            double rating = historyReviews[0].getRating();
            preferences.edit().putString("comment", comment).apply();
            preferences.edit().putString("ratings", String.valueOf(rating)).apply();

            Log.d(TAG, "loadHistoryData: COMMENT HERE ==>" + comment);
            Log.d(TAG, "loadHistoryData: RATING HERE ==>" + rating);
        } else {
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

