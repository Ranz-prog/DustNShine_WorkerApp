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
import com.example.dnsworker.CustomerDetails;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;
import com.example.dnsworker.ViewModel.ClientBookingViewModel;
import com.example.dnsworker.adapter.Task_Adapter;
import com.google.gson.Gson;

public class HomeFragment extends Fragment implements Task_Adapter.OnClickTaskListener {

    private RecyclerView taskRecycler;
    private View view;
    ClientBookData[] clientBookDataList;
    ClientBookData[] customerList;
    Service[] serviceList;
    Task_Adapter task_adapter;
    private ClientBookingViewModel clientBookingViewModel;

    private String retrievedToken;
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

        TextView noResult = view.findViewById(R.id.emptyTaskTV);

        //Passed Data from shared Pref
        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);


        clientBookingViewModel = ViewModelProviders.of(getActivity()).get(ClientBookingViewModel.class);
        clientBookingViewModel.getClientBookingData(retrievedToken).observe(getActivity(), new Observer<ClientBookingModel>() {
            @Override
            public void onChanged(ClientBookingModel clientBookingModel) {

                if (clientBookingModel != null){

                    ClientBookData[] clientBookData  = clientBookingModel.getData();
                    customerList = clientBookData;
                    clientBookDataList = clientBookingModel.getData();
                    task_adapter.setTaskModelList(customerList);
                    serviceList = clientBookData[0].getServices();
                } else{
                    //if No Data retrieved
                    noResult.setVisibility(View.VISIBLE);
                }
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
        String first_name = customerList [position].getCustomer().getFirstName();
        String last_name = customerList[position].getCustomer().getLastName();
        String mobile_number = customerList [position].getCustomer().getMobileNumber();
        String address = customerList [position].getAddress();
        String email = customerList[position].getCustomer().getEmail();
        double totalCost = clientBookDataList[position].getTotal();

        //Customer service data
        SharedPreferences preferences = getActivity().getSharedPreferences("CUSTOMER_DATA", Context.MODE_PRIVATE);
        preferences.edit().putString("first_name", first_name).apply();
        preferences.edit().putString("last_name", last_name).apply();
        preferences.edit().putString("mobile_number", mobile_number).apply();
        preferences.edit().putString("address", address).apply();
        preferences.edit().putString("total", String.valueOf(totalCost)).apply();
        preferences.edit().putString("email", email).apply();

        Gson gson = new Gson();

        //Service Details
        SharedPreferences servicePreference = getActivity().getSharedPreferences("CUSTOMER_SERVICE", Context.MODE_PRIVATE);
        String jsonString = gson.toJson(serviceList);
        servicePreference.edit().putString("SERVICE_LIST", jsonString).apply();
    }
}
