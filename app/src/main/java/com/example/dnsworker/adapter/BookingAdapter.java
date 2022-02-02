package com.example.dnsworker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnsworker.Model.BookingModel;
import com.example.dnsworker.R;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder>{

    private List<BookingModel> bookingModelsList;
    private OnClickBookingListener onClickBookingListener;

    public BookingAdapter(List<BookingModel> bookingModelsList, OnClickBookingListener onClickBookingListener){
        this.bookingModelsList = bookingModelsList;
        this.onClickBookingListener = onClickBookingListener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_booking_row, parent, false);
        BookingViewHolder viewHolder = new BookingViewHolder(view, onClickBookingListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.booking_Image.setImageResource(bookingModelsList.get(position).getBooking_clientImage());
        holder.booking_clientName.setText(bookingModelsList.get(position).getBooking_clientName());
        holder.booking_clientLocation.setText(bookingModelsList.get(position).getBooking_clientLocation());
        holder.booking_contactNumber.setText(bookingModelsList.get(position).getBoooking_clientNumber());

    }

    @Override
    public int getItemCount() {
        return bookingModelsList.size();
    }



    public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView booking_Image;
        private TextView booking_clientName, booking_clientLocation, booking_contactNumber;

        OnClickBookingListener onClickBookingListener;
        public BookingViewHolder(@NonNull View itemView, OnClickBookingListener onClickBookingListener) {
            super(itemView);

            /*Use from task_container_row.xml as sample*/

            booking_Image = itemView.findViewById(R.id.booking_clientImageView);
            booking_clientName = itemView.findViewById(R.id.booking_clientName_TV);
            booking_clientLocation = itemView.findViewById(R.id.booking_clientLocation_TV);
            booking_contactNumber = itemView.findViewById(R.id.booking_clientContact_TV);

            this.onClickBookingListener = onClickBookingListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickBookingListener.onClickBooking(getAdapterPosition());
        }
    }
    public interface OnClickBookingListener{
        void onClickBooking(int position);
    }
}
