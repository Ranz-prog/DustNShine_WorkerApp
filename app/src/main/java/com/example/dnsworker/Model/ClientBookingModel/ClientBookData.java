package com.example.dnsworker.Model.ClientBookingModel;

public class ClientBookData{
    private long id;
    private long user_id;
    private long company_id;
    private String sched_datetime;
    private String start_datetime;
    private String end_datetime;
    private String address;
    private long latitude;
    private long longitude;
    private int status;
    private long total;
    private String created_at;
    private String updated_at;
    private String note;
    private Service[] services;
    private Review reviews;
    private Customer customer;
    private Customer[] workers;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public long getUserID() { return user_id; }
    public void setUserID(long value) { this.user_id = value; }

    public long getCompanyID() { return company_id; }
    public void setCompanyID(long value) { this.company_id = value; }

    public String getSched_datetime(){return sched_datetime;}
    public void setSched_datetime(String value) {this.sched_datetime = value;}

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

    public long getTotal() { return total; }
    public void setTotal(long value) { this.total = value; }

    public String getCreatedAt() { return created_at; }
    public void setCreatedAt(String value) { this.created_at = value; }

    public String getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(String value) { this.updated_at = value; }

    public String getNote() { return note; }
    public void setNote(String value) { this.note = value; }

    public Service[] getServices() { return services; }
    public void setServices(Service[] value) { this.services = value; }

    public Review getReviews(){ return reviews;}
    public void setReviews(Review value){this.reviews = value; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer value) { this.customer = value; }

    public Customer[] getWorkers() { return workers; }
    public void setWorkers(Customer[] value) { this.workers = value; }

}
