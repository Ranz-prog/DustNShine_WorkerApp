package com.example.dnsworker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Models.TaskModel;
import com.example.dnsworker.R;
import com.example.dnsworker.adapters.Task_Adapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView taskRecycler;
    private View view;
    private List<TaskModel> itemListSample;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        taskRecycler = view.findViewById(R.id.task_RecyclerView);
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        /*taskModels();*/

        taskRecycler.setAdapter(new Task_Adapter(taskModels()));

        return view;
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
}
