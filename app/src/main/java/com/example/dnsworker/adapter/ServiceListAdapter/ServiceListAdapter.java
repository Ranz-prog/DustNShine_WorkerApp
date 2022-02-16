package com.example.dnsworker.adapter.ServiceListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {


    Context context;
    private Service[] serviceList;

    public ServiceListAdapter(Context context, Service[] serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public void setServiceList(Service[] serviceList){
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_servicelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ViewHolder holder, int position) {
        holder.serviceName.setText(serviceList[position].getName().toString());
        holder.serviceDescription.setText(serviceList[position].getDescription().toString());

    }

    @Override
    public int getItemCount() {
        if (this.serviceList !=null){
            return this.serviceList.length;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView serviceName, serviceDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.service_title);
            serviceDescription = itemView.findViewById(R.id.service_description);

        }
    }
}
