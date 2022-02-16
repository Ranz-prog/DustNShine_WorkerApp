package com.example.dnsworker.adapter.SLAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.Service;
import com.example.dnsworker.R;
import com.example.dnsworker.adapter.ServiceListAdapter.ServiceListAdapter;

public class SLAdapter extends RecyclerView.Adapter<SLAdapter.ViewHolder> {

    private Context context;
    private Service[] serviceList;

    public SLAdapter(Context context, Service[] serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    public void setSLData(Service[] serviceList){
        this.serviceList = serviceList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SLAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_servicelist_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SLAdapter.ViewHolder holder, int position) {
        holder.titleService.setText(serviceList[position].getName().toString());
        holder.titleDesc.setText(serviceList[position].getDescription().toString());
    }

    @Override
    public int getItemCount() {
        if (this.serviceList !=null){
            return this.serviceList.length;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleService, titleDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleService = itemView.findViewById(R.id.service_title);
            titleDesc = itemView.findViewById(R.id.service_description);
        }
    }
}
