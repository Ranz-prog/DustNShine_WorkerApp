package com.example.dnsworker.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.R;
import java.util.ArrayList;

public class Task_Adapter extends RecyclerView.Adapter<Task_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<ClientBookData> customerList;
    private OnClickTaskListener onClickTaskListener;


    public Task_Adapter(Context context, ArrayList<ClientBookData> customerList, OnClickTaskListener onClickTaskListener) {
        this.context = context;
        this.customerList = customerList;
        this.onClickTaskListener = onClickTaskListener;
    }

    public void setTaskModelList(ArrayList<ClientBookData> customer_List) {
        this.customerList = customer_List;
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

        holder.task_firstLetter.setText(customerList.get(position).getCustomer().getFirstName().substring(0,1));
        holder.task_clientName.setText(customerList.get(position).getCustomer().getFirstName() + " " + customerList.get(position).getCustomer().getLastName());
        holder.task_clientLocation.setText(customerList.get(position).getAddress());
        holder.task_clientContact.setText(customerList.get(position).getCustomer().getMobileNumber());
        holder.task_clientSchedule.setText(customerList.get(position).getSched_datetime());

    }

    @Override
    public int getItemCount() {
        if (this.customerList != null) {
            return this.customerList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView task_clientName, task_clientLocation, task_clientContact,
                task_clientSchedule, task_firstLetter;
        OnClickTaskListener onClickTaskListener;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, OnClickTaskListener onClickTaskListener) {
            super(itemView);

            task_clientName = itemView.findViewById(R.id.task_clientName_TV);
            task_clientLocation = itemView.findViewById(R.id.task_clientLocation_TV);
            task_clientContact = itemView.findViewById(R.id.task_clientContact_TV);
            task_clientSchedule = itemView.findViewById(R.id.task_clientSchedule_TV);
            task_firstLetter = itemView.findViewById(R.id.firstLetterTV);

            this.onClickTaskListener = onClickTaskListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onClickTaskListener.onClickTask(getAdapterPosition());
        }
    }

    public interface OnClickTaskListener {
        void onClickTask(int position);
    }
}

