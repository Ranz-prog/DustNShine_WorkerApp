package com.example.dnsworker.Model.ClientBookingModel;

import java.util.Date;

public class ClientBookData {
    private long id;
    private long user_id;
    private long company_id;
    private Date startDatetime;
    private String address;
    private Object latitude;
    private Object longitude;
    private long status;
    private long total;
    private Date createdAt;
    private Date updatedAt;
    private Service[] services;
    private Customer customer;
    private Customer[] workers;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public long getUserID() { return user_id; }
    public void setUserID(long value) { this.user_id = value; }

    public long getCompanyID() { return company_id; }
    public void setCompanyID(long value) { this.company_id = value; }

    public Date getStartDatetime() { return startDatetime; }
    public void setStartDatetime(Date value) { this.startDatetime = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public Object getLatitude() { return latitude; }
    public void setLatitude(Object value) { this.latitude = value; }

    public Object getLongitude() { return longitude; }
    public void setLongitude(Object value) { this.longitude = value; }

    public long getStatus() { return status; }
    public void setStatus(long value) { this.status = value; }

    public long getTotal() { return total; }
    public void setTotal(long value) { this.total = value; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date value) { this.createdAt = value; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date value) { this.updatedAt = value; }

    public Service[] getServices() { return services; }
    public void setServices(Service[] value) { this.services = value; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer value) { this.customer = value; }

    public Customer[] getWorkers() { return workers; }
    public void setWorkers(Customer[] value) { this.workers = value; }
}
