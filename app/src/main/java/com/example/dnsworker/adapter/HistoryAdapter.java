package com.example.dnsworker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dnsworker.Model.ClientBookingModel.ClientBookData;
import com.example.dnsworker.R;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.BookingViewHolder> {

    private OnClickBookingListener onClickBookingListener;
    private ArrayList<ClientBookData> customerDataList;
    private Context context;


    public HistoryAdapter(Context context, ArrayList<ClientBookData> customerList, OnClickBookingListener onClickBookingListener) {
        this.context = context;
        this.customerDataList = customerList;
        this.onClickBookingListener = onClickBookingListener;
    }

    public void setHistoryModelList(ArrayList<ClientBookData> customerList) {
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
        holder.booking_firstLetter.setText(customerDataList.get(position).getCustomer().getFirstName().substring(0,1));
        holder.booking_clientName.setText(customerDataList.get(position).getCustomer().getFirstName() + " " + customerDataList.get(position).getCustomer().getLastName());
        holder.booking_clientLocation.setText(customerDataList.get(position).getAddress());
        holder.booking_contactNumber.setText(customerDataList.get(position).getCustomer().getMobileNumber());
        holder.booking_schedule.setText(customerDataList.get(position).getSched_datetime());

    }

    @Override
    public int getItemCount() {
        if (this.customerDataList != null) {
            return this.customerDataList.size();
        }
        return 0;
    }


    public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView booking_clientName, booking_clientLocation,
                booking_contactNumber, booking_schedule, booking_firstLetter;

        OnClickBookingListener onClickBookingListener;

        public BookingViewHolder(@NonNull View itemView, OnClickBookingListener onClickBookingListener) {
            super(itemView);
            booking_clientName = itemView.findViewById(R.id.booking_clientName_TV);
            booking_clientLocation = itemView.findViewById(R.id.booking_clientLocation_TV);
            booking_contactNumber = itemView.findViewById(R.id.booking_clientContact_TV);
            booking_schedule = itemView.findViewById(R.id.booking_clientSchedule_TV);
            booking_firstLetter = itemView.findViewById(R.id.history_firstLetterTV);

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
