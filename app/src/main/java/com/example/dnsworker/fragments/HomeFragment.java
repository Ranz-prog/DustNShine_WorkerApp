package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dnsworker.CustomerDetails;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.Task_Adapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Task_Adapter.OnClickTaskListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView taskRecycler;
    private View view;
    //    private ClientBookData[] clientBookDataList;
    private ArrayList<ClientBookData> clientBookDataList;
    private Service[] serviceList;
    private Task_Adapter task_adapter;
    private ClientBookingViewModel clientBookingViewModel;
    private String retrievedToken;
    private SharedPreferences preferences;
    TextView noResult;
    private NotificationManagerCompat notificationManagerCompat;
    private SwipeRefreshLayout swipeRefreshLayout;

    
    Button buttonRefresh;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        notificationManagerCompat = NotificationManagerCompat.from(getContext());


        taskRecycler = view.findViewById(R.id.task_RecyclerView);
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        task_adapter = new Task_Adapter(getContext(), clientBookDataList, this);
        taskRecycler.setAdapter(task_adapter);

        
//        buttonRefresh = view.findViewById(R.id.refreshButton);
//
//        buttonRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onChangedMethod();
//                task_adapter.notifyDataSetChanged();
//                Log.d(TAG, "onClick: ONCHANGEDDDDDD" + clientBookDataList.size());
//            }
//        });


        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        noResult = view.findViewById(R.id.emptyTaskTV);

        //Passed Data from shared Pref
        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);

        clientBookingViewModel = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 3 seconds)
                        //startRefresh();
                        swipeRefreshLayout.setRefreshing(false);
                        onChangedMethod();


//                        swipeRefreshLayout.setRefreshing(false);
//                        clientBookDataList.addAll(clientBookDataList);
//                        getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
//                        Log.d(TAG, "run: Refresh ==>" + clientBookDataList.size());
                    }
                },1000);


            }
        });



//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
//                onChangedMethod();
//
//                Log.d(TAG, "run: REFRESH");
//            }
//        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
        //onChangedMethod();

        onChangedMethod();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");

    }

    private void onChangedMethod() {

        clientBookingViewModel.getClientBookingData(retrievedToken).observe(getActivity(), new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {

                if (clientBookingModel != null) {
                    ArrayList<ClientBookData> clientBookData = clientBookingModel.getData();
                    clientBookDataList = clientBookData;
                    task_adapter.setTaskModelList(clientBookDataList);
                    Log.d("TAG", "REFRESH == > " + clientBookData.size());

                } else {
                    //if No Data retrieved
                    Log.d("TAG", "NO REFRESH");
                    noResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //method to add content to listview while refresh
//    private void startRefresh() {
//        new BackgroundTask().execute();
//    }
//
//    //this method executes after loading contents
//    private void onRefreshComplete(ArrayList<ClientBookData> result) {
//        //clear the existing adapter
//        result.clear();
//        //add new list to adapter
//        swipeRefreshLayout.setRefreshing(false);
//        onChangedMethod();
//
//    }
//
//    //Async task to load data
//    //here a timer is implemented in doInBackground
//    private class BackgroundTask extends AsyncTask {
//        //duration of the swipe feature
//        static final int Duration = 3000;
//
//        @Override
//        protected ArrayList doInBackground(Object[] objects) {
//            try {
//                Thread.sleep(Duration);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return clientBookDataList;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            onRefreshComplete(clientBookDataList);
//        }
//    }



    @Override
    public void onClickTask(int position) {
        Log.d(TAG, "onClickTask: clicked");
        loadData(position);
        Intent intent = new Intent(getContext(), CustomerDetails.class);
        startActivity(intent);
    }

    private void loadData(int position) {

        //Specific Customer Details
        String first_name = clientBookDataList.get(position).getCustomer().getFirstName();
        String last_name = clientBookDataList.get(position).getCustomer().getLastName();
        String mobile_number = clientBookDataList.get(position).getCustomer().getMobileNumber();
        String address = clientBookDataList.get(position).getAddress();
        String email = clientBookDataList.get(position).getCustomer().getEmail();
        String schedule = clientBookDataList.get(position).getSched_datetime();
        long longitude = clientBookDataList.get(position).getLongitude();
        long latitude = clientBookDataList.get(position).getLatitude();
        int status = clientBookDataList.get(position).getStatus();
        int id = (int) clientBookDataList.get(position).getID();
        double totalCost = clientBookDataList.get(position).getTotal();
        String note = clientBookDataList.get(position).getNote();
        serviceList = clientBookDataList.get(position).getServices();

        SharedPreferences preferences = getActivity().getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        preferences.edit().putString("first_name", first_name).apply();
        preferences.edit().putString("last_name", last_name).apply();
        preferences.edit().putString("mobile_number", mobile_number).apply();
        preferences.edit().putString("address", address).apply();
        preferences.edit().putString("total", String.valueOf(totalCost)).apply();
        preferences.edit().putString("email", email).apply();
        preferences.edit().putInt("status", status).apply();
        preferences.edit().putLong("longitude", longitude).apply();
        preferences.edit().putLong("latitude", latitude).apply();
        preferences.edit().putInt("id", id).apply();
        preferences.edit().putString("sched_datetime", schedule).apply();
        preferences.edit().putString("note", note).apply();

        Gson gson = new Gson();

        //Service Details of the customer
        SharedPreferences servicePreference = getActivity().getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = gson.toJson(serviceList);
        servicePreference.edit().putString("SERVICE_LIST", jsonString).commit();

    }


    @Override
    public void onRefresh() {
        onChangedMethod();
    }
}
