package com.example.dnsworker.Model.ClientBookingModel;

import android.view.SurfaceControl;

import java.util.Date;

public class ClientBookData implements Comparable<ClientBookData>{
    private long id;
    private long user_id;
    private long company_id;
    private String start_datetime;
    private String end_datetime;
    private String address;
    private long latitude;
    private long longitude;
    private int status;
    private double total;
    private String created_at;
    private String updated_at;
    private Service[] services;
    private Customer customer;
    private Customer[] workers;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public long getUserID() { return user_id; }
    public void setUserID(long value) { this.user_id = value; }

    public long getCompanyID() { return company_id; }
    public void setCompanyID(long value) { this.company_id = value; }

    public String getStartDatetime() { return start_datetime; }
    public void setStartDatetime(String value) { this.start_datetime = value; }

    public String getEnd_datetime() { return end_datetime; }
    public void setEnd_datetime(String value) { this.end_datetime = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public long getLatitude() { return latitude; }
    public void setLatitude(long value) { this.latitude = value; }

    public long getLongitude() { return longitude; }
    public void setLongitude(long value) { this.longitude = value; }

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }

    public double getTotal() { return total; }
    public void setTotal(long value) { this.total = value; }

    public String getCreatedAt() { return created_at; }
    public void setCreatedAt(String value) { this.created_at = value; }

    public String getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(String value) { this.updated_at = value; }

    public Service[] getServices() { return services; }
    public void setServices(Service[] value) { this.services = value; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer value) { this.customer = value; }

    public Customer[] getWorkers() { return workers; }
    public void setWorkers(Customer[] value) { this.workers = value; }

    @Override
    public int compareTo(ClientBookData o) {
        return new Date(this.start_datetime).compareTo(new Date(o.start_datetime));
    }
}
