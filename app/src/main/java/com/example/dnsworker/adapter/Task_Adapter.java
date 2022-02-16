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

import com.example.dnsworker.Model.ClientBookingModel.Customer;
import com.example.dnsworker.R;

import java.util.List;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {

    //private List<TaskModel> taskModelList;
    Context context;
    private List<Customer> customerList;
    private OnClickTaskListener onClickTaskListener;


    public Task_Adapter(Context context, List<Customer> customerList, OnClickTaskListener onClickTaskListener) {
        this.context = context;
        this.customerList = customerList;
        this.onClickTaskListener = onClickTaskListener;
    }


    public void setTaskModelList(List<Customer> customerList){
        this.customerList = customerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_task_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onClickTaskListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.task_clientName.setText(customerList.get(position).getFirstName() + " " + customerList.get(position).getLastName());
        holder.task_clientContact.setText(customerList.get(position).getMobileNumber());

        Log.d(TAG, "onBindViewHolder: client Name ====> " + customerList.get(position).getFirstName());
        Log.d(TAG, "onBindViewHolder: client Name ====> " + customerList.get(position).getMobileNumber());

        //holder.task_clientImage.setImageResource(taskModelList.get(position).getClientImage());
        //holder.task_clientName.setText(taskModelList.get(position).getClientName());
        //holder.task_clientLocation.setText(taskModelList.get(position).getClientLocation());
        //holder.task_clientContact.setText(taskModelList.get(position).getClientNContactNumber());

    }

    @Override
    public int getItemCount() {
        if (this.customerList !=null){
            return this.customerList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView task_clientImage;
        private TextView task_clientName, task_clientLocation, task_clientContact;
        OnClickTaskListener onClickTaskListener;

        public ViewHolder(@NonNull View itemView, OnClickTaskListener onClickTaskListener) {
            super(itemView);

            //task_clientImage = itemView.findViewById(R.id.task_clientImageView);
            task_clientName = itemView.findViewById(R.id.task_clientName_TV);
            //task_clientLocation = itemView.findViewById(R.id.task_clientLocation_TV);
            task_clientContact = itemView.findViewById(R.id.task_clientContact_TV);

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

