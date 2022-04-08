package com.example.dnsworker.Model.ClientBookingModel;

public class ServicePivot {
    private long booking_id;
    private long service_id;

    public long getBookingID() { return booking_id; }
    public void setBookingID(long value) { this.booking_id = value; }

    public long getServiceID() { return service_id; }
    public void setServiceID(long value) { this.service_id = value; }
}
