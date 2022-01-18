package com.example.dnsworker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Models.TaskModel;
import com.example.dnsworker.R;

import java.util.List;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {


    List<TaskModel> taskModelList;

    public Task_Adapter(List<TaskModel> taskModelList){
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_task_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.task_clientImage.setImageResource(taskModelList.get(position).getClientImage());
        holder.task_clientName.setText(taskModelList.get(position).getClientName());
        holder.task_clientLocation.setText(taskModelList.get(position).getClientLocation());
        holder.task_clientContact.setText(taskModelList.get(position).getClientNContactNumber());

    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView task_clientImage;
        private TextView task_clientName, task_clientLocation, task_clientContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            task_clientImage = itemView.findViewById(R.id.clientImageView);
            task_clientName = itemView.findViewById(R.id.task_clientName_TV);
            task_clientLocation = itemView.findViewById(R.id.task_clientLocation_TV);
            task_clientContact = itemView.findViewById(R.id.task_clientContact_TV);
        }
    }
}
