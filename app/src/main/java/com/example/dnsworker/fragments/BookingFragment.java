package com.example.dnsworker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Feedback;
import com.example.dnsworker.Model.BookingModel;
import com.example.dnsworker.R;
import com.example.dnsworker.adapter.BookingAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment implements BookingAdapter.OnClickBookingListener {

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
        //bookingRecycler.setAdapter(new BookingAdapter(bookingModels(), this));

        return view;

    }
//    private List<BookingModel> bookingModels(){
//
//        itemListBookingSample = new ArrayList<BookingModel>();
//
//        itemListBookingSample.add(new BookingModel(R.drawable.user,
//                "Juan Dela Cruz", "123 Arellano, Dagupan City", "09123456789"));
//        itemListBookingSample.add(new BookingModel(R.drawable.user,
//                "Ivan Dasigan", "123 Arellano, Dagupan City", "09123456789"));
//        itemListBookingSample.add(new BookingModel(R.drawable.user,
//                "Michael Jackson", "123 Arellano, Dagupan City", "09123456789"));
//        return itemListBookingSample;
//
//    }


    @Override
    public void onClickBooking(int position) {
        Intent intent = new Intent(getActivity(), Feedback.class);
        startActivity(intent);
    }
}

