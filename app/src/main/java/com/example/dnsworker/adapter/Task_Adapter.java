package com.example.dnsworker.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookingModel;
import com.example.dnsworker.Model.ClientBookingModel.Customer;
import com.example.dnsworker.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {

    private Context context;
    private ClientBookData[] customerList;
    private OnClickTaskListener onClickTaskListener;


    public Task_Adapter(Context context, ClientBookData[] customerList, OnClickTaskListener onClickTaskListener) {
        this.context = context;
        this.customerList = customerList;
        this.onClickTaskListener = onClickTaskListener;
    }

    public void setTaskModelList(ClientBookData[] customerList){
        this.customerList = customerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_content_task_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onClickTaskListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.task_clientName.setText(customerList[position].getCustomer().getFirstName() + " " + customerList [position].getCustomer().getLastName());
        holder.task_clientLocation.setText(customerList[position].getAddress());
        holder.task_clientContact.setText(customerList[position].getCustomer().getMobileNumber());
        holder.task_clientSchedule.setText(customerList[position].getSched_datetime());
        Log.d(TAG, "onBindViewHolder: DATA HERE =====> " + customerList[position].getStatus());

/*        String status = String.valueOf(customerList[position].getStatus());
        if (status.equalsIgnoreCase(String.valueOf(1))){
            holder.task_clientName.setText(customerList[position].getCustomer().getFirstName() + " " + customerList [position].getCustomer().getLastName());
            holder.task_clientLocation.setText(customerList[position].getAddress());
            holder.task_clientContact.setText(customerList[position].getCustomer().getMobileNumber());
            holder.task_clientSchedule.setText(customerList[position].getSched_datetime());
            Log.d(TAG, "onBindViewHolder: DATA HERE =====> " + customerList[position].getStatus());

        }
        else{

        }*/



    }




    @Override
    public int getItemCount() {
        if (this.customerList !=null){
            return this.customerList.length;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView task_clientImage;
        private TextView task_clientName, task_clientLocation, task_clientContact, task_clientSchedule;
        OnClickTaskListener onClickTaskListener;

        public ViewHolder(@NonNull View itemView, OnClickTaskListener onClickTaskListener) {
            super(itemView);

            //task_clientImage = itemView.findViewById(R.id.task_clientImageView);
            task_clientName = itemView.findViewById(R.id.task_clientName_TV);
            task_clientLocation = itemView.findViewById(R.id.task_clientLocation_TV);
            task_clientContact = itemView.findViewById(R.id.task_clientContact_TV);
            task_clientSchedule = itemView.findViewById(R.id.task_clientSchedule_TV);

            this.onClickTaskListener = onClickTaskListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClickTaskListener.onClickTask(getAdapterPosition());
        }
    }

    public interface OnClickTaskListener{
        void onClickTask(int position);
    }
}

