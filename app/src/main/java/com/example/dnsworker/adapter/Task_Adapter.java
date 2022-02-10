package com.example.dnsworker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.TaskModel;
import com.example.dnsworker.R;

import java.util.List;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {

    private List<TaskModel> taskModelList;
    private OnClickTaskListener onClickTaskListener;

    public Task_Adapter(List<TaskModel> taskModelList, OnClickTaskListener onClickTaskListener){
        this.taskModelList = taskModelList;
        this.onClickTaskListener = onClickTaskListener;
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

        holder.task_clientImage.setImageResource(taskModelList.get(position).getClientImage());
        holder.task_clientName.setText(taskModelList.get(position).getClientName());
        holder.task_clientLocation.setText(taskModelList.get(position).getClientLocation());
        holder.task_clientContact.setText(taskModelList.get(position).getClientNContactNumber());

    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView task_clientImage;
        private TextView task_clientName, task_clientLocation, task_clientContact;
        OnClickTaskListener onClickTaskListener;

        public ViewHolder(@NonNull View itemView, OnClickTaskListener onClickTaskListener) {
            super(itemView);

            task_clientImage = itemView.findViewById(R.id.task_clientImageView);
            task_clientName = itemView.findViewById(R.id.task_clientName_TV);
            task_clientLocation = itemView.findViewById(R.id.task_clientLocation_TV);
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

