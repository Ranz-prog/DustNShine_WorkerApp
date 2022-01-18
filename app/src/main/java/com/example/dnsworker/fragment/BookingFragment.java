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

import com.example.dnsworker.Models.BookingModel;
import com.example.dnsworker.R;
import com.example.dnsworker.adapters.BookingAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private RecyclerView bookingRecycler;
    private View view;
    private List<BookingModel> itemListBookingSample;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_booking,container,false);

        bookingRecycler = view.findViewById(R.id.booking_RecyclerView);
        bookingRecycler.setHasFixedSize(true);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //bookingRecycler.setAdapter(new BookingAdapter(bookingModels()));
        bookingRecycler.setAdapter(new BookingAdapter(bookingModels()));

        return view;

    }
    private List<BookingModel> bookingModels(){

        itemListBookingSample = new ArrayList<BookingModel>();

        itemListBookingSample.add(new BookingModel(R.drawable.user,
                "Juan Dela Cruz", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));
        itemListBookingSample.add(new BookingModel(R.drawable.user,
                "Ivan Dasigan", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));
        itemListBookingSample.add(new BookingModel(R.drawable.user,
                "Michael Jackson", "123 St. Brgy, Lorem, Dagupan City", "09123456789"));



        return itemListBookingSample;

    }

}
