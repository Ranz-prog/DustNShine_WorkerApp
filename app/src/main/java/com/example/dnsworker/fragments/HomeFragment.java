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
import android.widget.Button;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.CustomerDetails;
import com.example.dnsworker.LoginPage;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Customer;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.Model.TaskModel;
import com.example.dnsworker.R;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.Task_Adapter;
import com.google.gson.Gson;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements Task_Adapter.OnClickTaskListener {

    private RecyclerView taskRecycler;
    private View view;
    List<Customer> customerList;
    ClientBookData[] clientBookDataList;
    Service[] serviceList;
    Task_Adapter task_adapter;
    private ClientBookingViewModel clientBookingViewModel;

    private  String retrievedToken;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        taskRecycler = view.findViewById(R.id.task_RecyclerView);
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        task_adapter = new Task_Adapter(getContext(), customerList, this);
        taskRecycler.setAdapter(task_adapter);

        //Passed Data from shared Pref
        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);


        customerList = new ArrayList<>();

        clientBookingViewModel = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);
        clientBookingViewModel.getClientBookingData(retrievedToken).observe(getActivity(), new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {
                ClientBookData[] clientBookData  = clientBookingModel.getData();
                customerList.add(clientBookData[0].getCustomer());
                clientBookDataList = clientBookingModel.getData();
                task_adapter.setTaskModelList(customerList);
                serviceList = clientBookData[0].getServices();
            }
        });
        return view;
    }

    @Override
    public void onClickTask(int position) {
        Log.d(TAG, "onClickTask: clicked");

        loadData(position);
        Intent intent = new Intent(getContext(), CustomerDetails.class);
        startActivity(intent);
    }

    private void loadData(int position){

        //Customer Details
        String first_name = customerList.get(position).getFirstName();
        String last_name = customerList.get(position).getLastName();
        String mobile_number = customerList.get(position).getMobileNumber();
        double totalCost = clientBookDataList[position].getTotal();


        Log.d(TAG, "onClickTask: C_DATA ========> " + customerList.get(position));
        Log.d(TAG, "onClickTask: TOTALCOST =======>" + totalCost);

        //Customer service data
        SharedPreferences preferences = getActivity().getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        preferences.edit().putString("first_name", first_name).apply();
        preferences.edit().putString("last_name", last_name).apply();
        preferences.edit().putString("mobile_number", mobile_number).apply();
        preferences.edit().putString("total", String.valueOf(totalCost)).apply();


        Gson gson = new Gson();

        //Service Details
        SharedPreferences servicePreference = getActivity().getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = gson.toJson(serviceList);
        servicePreference.edit().putString("SERVICE_LIST", jsonString).apply();
    }
}
