package com.example.dnsworker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.BookingViewHolder> {

    private OnClickBookingListener onClickBookingListener;
    private ClientBookData[] customerDataList;
    private Context context;


    public HistoryAdapter(Context context, ClientBookData[] customerList, OnClickBookingListener onClickBookingListener) {
        this.context = context;
        this.customerDataList = customerList;
        this.onClickBookingListener = onClickBookingListener;
    }

    public void setHistoryModelList(ClientBookData[] customerList) {
        this.customerDataList = customerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_content_booking_row, parent, false);
        BookingViewHolder viewHolder = new BookingViewHolder(view, onClickBookingListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        //holder.booking_Image.setImageResource(bookingModelsList.get(position).getBooking_clientImage());
        holder.booking_clientName.setText(customerDataList[position].getCustomer().getFirstName() + " " + customerDataList[position].getCustomer().getLastName());
        holder.booking_clientLocation.setText(customerDataList[position].getAddress());
        holder.booking_contactNumber.setText(customerDataList[position].getCustomer().getMobileNumber());
        holder.booking_schedule.setText(customerDataList[position].getSched_datetime());

    }

    @Override
    public int getItemCount() {
        if (this.customerDataList != null) {
            return this.customerDataList.length;
        }
        return 0;
    }


    public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView booking_Image;
        private TextView booking_clientName, booking_clientLocation,
                booking_contactNumber, booking_schedule;

        OnClickBookingListener onClickBookingListener;

        public BookingViewHolder(@NonNull View itemView, OnClickBookingListener onClickBookingListener) {
            super(itemView);

            /*Use from task_container_row.xml as sample*/

            //booking_Image = itemView.findViewById(R.id.booking_clientImageView);
            booking_clientName = itemView.findViewById(R.id.booking_clientName_TV);
            booking_clientLocation = itemView.findViewById(R.id.booking_clientLocation_TV);
            booking_contactNumber = itemView.findViewById(R.id.booking_clientContact_TV);
            booking_schedule = itemView.findViewById(R.id.booking_clientSchedule_TV);

            this.onClickBookingListener = onClickBookingListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickBookingListener.onClickBooking(getAdapterPosition());
        }
    }

    public interface OnClickBookingListener {
        void onClickBooking(int position);
    }
}
