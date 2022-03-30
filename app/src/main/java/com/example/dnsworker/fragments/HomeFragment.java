package com.example.dnsworker.fragments;

import static android.content.ContentValues.TAG;

import static org.webrtc.ContextUtils.getApplicationContext;

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
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dnsworker.CustomerDetails;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.Model.User.User;
import com.example.dnsworker.R;
import com.example.dnsworker.Service.BookingService;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.ViewModel.UserViewModel;
import com.example.dnsworker.adapter.Task_Adapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Task_Adapter.OnClickTaskListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView taskRecycler;
    private View view;
    private ArrayList<ClientBookData> clientBookDataList;
    private Service[] serviceList;
    private Task_Adapter task_adapter;
    private ClientBookingViewModel clientBookingViewModel;
    private String retrievedToken;
    private SharedPreferences preferences;
    TextView noResult, noInternetResult, worker_name, count_task, hiTV, youhaveTV, newTaskToday;
    private NotificationManagerCompat notificationManagerCompat;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageView emptyImage, noConnectionImage;
    UserViewModel userViewModel;

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

        swipeRefreshLayout = view.findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        noResult = view.findViewById(R.id.emptyTask);
        emptyImage = view.findViewById(R.id.noResultLottie);
        noInternetResult = view.findViewById(R.id.noInternetConnectionTV);
        noConnectionImage = view.findViewById(R.id.noInternetImage);
        worker_name = view.findViewById(R.id.home_worker_name);
        count_task = view.findViewById(R.id.home_count_task);
        hiTV = view.findViewById(R.id.hiTV);
        youhaveTV = view.findViewById(R.id.youHaveTV);
        newTaskToday = view.findViewById(R.id.newTaskTodayTV);

        //Passed Data from shared Pref
        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);

        userViewModel = new UserViewModel();
        getUserInformation();

        if (!isConnected()){
            noConnectionImage.setVisibility(View.VISIBLE);
            noInternetResult.setVisibility(View.VISIBLE);
            taskRecycler.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
            noResult.setVisibility(View.GONE);
            hiTV.setVisibility(View.GONE);
            worker_name.setVisibility(View.GONE);
            youhaveTV.setVisibility(View.GONE);
            count_task.setVisibility(View.GONE);
            newTaskToday.setVisibility(View.GONE);
        }
        else{
            noConnectionImage.setVisibility(View.GONE);
            noInternetResult.setVisibility(View.GONE);
            taskRecycler.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.GONE);
            noResult.setVisibility(View.GONE);
            hiTV.setVisibility(View.VISIBLE);
            worker_name.setVisibility(View.VISIBLE);
            youhaveTV.setVisibility(View.VISIBLE);
            count_task.setVisibility(View.VISIBLE);
            newTaskToday.setVisibility(View.VISIBLE);
        }

        clientBookingViewModel = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        onChangedMethod();

                    }
                },1000);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");

        onChangedMethod();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    private void getUserInformation(){
        userViewModel.getUserDataResponse(retrievedToken).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                worker_name.setText(user.getFirst_name());
            }
        });
    }
    private void onChangedMethod() {

        clientBookingViewModel.getClientBookingData(retrievedToken);

        clientBookingViewModel.bookingService.setOnBookListener(new BookingService.BookingCallback() {
            @Override
            public void bookingCallback(Integer statusCode, ClientBookingModel clientBookingModel) {
                if (clientBookingModel != null) {
                    ArrayList<ClientBookData> clientBookData = clientBookingModel.getData();
                    clientBookDataList = clientBookData;
                    task_adapter.setTaskModelList(clientBookDataList);
                    noResult.setVisibility(View.GONE);

                    if (clientBookData.size() != 0){
                        taskRecycler.setVisibility(View.VISIBLE);
                        hiTV.setVisibility(View.VISIBLE);
                        worker_name.setVisibility(View.VISIBLE);
                        youhaveTV.setVisibility(View.VISIBLE);
                        count_task.setVisibility(View.VISIBLE);
                        newTaskToday.setVisibility(View.VISIBLE);
                        emptyImage.setVisibility(View.GONE);
                        noResult.setVisibility(View.GONE);
                        noConnectionImage.setVisibility(View.GONE);
                        noInternetResult.setVisibility(View.GONE);


                    }
                    else if (clientBookData.size() == 0){
                        hiTV.setVisibility(View.VISIBLE);
                        youhaveTV.setVisibility(View.VISIBLE);
                        count_task.setVisibility(View.VISIBLE);
                        newTaskToday.setVisibility(View.VISIBLE);

                        taskRecycler.setVisibility(View.GONE);
                        emptyImage.setVisibility(View.VISIBLE);
                        noResult.setVisibility(View.VISIBLE);
                        noConnectionImage.setVisibility(View.GONE);
                        noInternetResult.setVisibility(View.GONE);

                    }
                    else{
                        taskRecycler.setVisibility(View.GONE);
                        emptyImage.setVisibility(View.VISIBLE);
                        noResult.setVisibility(View.VISIBLE);
                        noConnectionImage.setVisibility(View.GONE);
                        noInternetResult.setVisibility(View.GONE);

                    }

                    count_task.setText(String.valueOf(clientBookData.size()));


                    Log.d("TAG", "REFRESH == > " + clientBookData.size());

                }

                else {
                    //if No Data retrieved
                    Log.d("TAG", "NO REFRESH");
                }
            }
        });

    }

    public boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo()!= null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }



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
