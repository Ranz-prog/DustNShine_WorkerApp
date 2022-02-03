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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.API.APIClient;
import com.example.dnsworker.CustomerDetails;
import com.example.dnsworker.LoginPage;
import com.example.dnsworker.Model.TaskModel;
import com.example.dnsworker.R;
import com.example.dnsworker.adapter.Task_Adapter;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements Task_Adapter.OnClickTaskListener {

    private RecyclerView taskRecycler;
    private View view;
    private List<TaskModel> itemListSample;
    private Button btnLogout;

    private  String retrievedToken;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        taskRecycler = view.findViewById(R.id.task_RecyclerView);
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        btnLogout = view.findViewById(R.id.btnLogout);

        //Passed Data from shared Pref
        preferences = getActivity().getSharedPreferences("AUTH_TOKEN", Context.MODE_PRIVATE);
        retrievedToken = preferences.getString("TOKEN", null);


        taskRecycler.setAdapter(new Task_Adapter(taskModels(), this));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    //Method for logout
    private void logout(){

        Call<Map<String, String>> logoutRequest =  APIClient.getUserService().userLogout("Bearer " + retrievedToken);
        logoutRequest.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()){

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("TOKEN").apply();

                    Log.d(TAG, "onResponse: " +  response.body().get("message"));

                    startActivity(new Intent(getContext(), LoginPage.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(getContext(), "Logout Failed", Toast.LENGTH_LONG);

            }
        });


    }

    private List<TaskModel> taskModels(){

        itemListSample = new ArrayList<>();

        itemListSample.add(new TaskModel(R.drawable.user,
                "Juan Dela Cruz", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));
        itemListSample.add(new TaskModel(R.drawable.user,
                "Ivan Dasigan", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));
        itemListSample.add(new TaskModel(R.drawable.user,
                "Michael Jackson", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));


        return itemListSample;



    }

    @Override
    public void onClickTask(int position) {
        Log.d(TAG, "onClickTask: clicked");
        Intent intent = new Intent(getContext(), CustomerDetails.class);
        startActivity(intent);
    }
}
