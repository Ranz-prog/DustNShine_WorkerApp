package com.example.dnsworker.Model;


public class ClientBookingModel {
    private Worker[] worker;
    private Detail[] details;
    private Service[] services;
    private String message;

    public Worker[] getWorker() { return worker; }
    public void setWorker(Worker[] value) { this.worker = value; }

    public Detail[] getDetails() { return details; }
    public void setDetails(Detail[] value) { this.details = value; }

    public Service[] getServices() { return services; }
    public void setServices(Service[] value) { this.services = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }
}

// Detail.java

class Detail {
    private long id;
    private long userID;
    private long companyID;
    private String startDatetime;
    private String address;
    private double latitude;
    private double longitude;
    private long status;
    private long total;
    private String createdAt;
    private String updatedAt;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public long getUserID() { return userID; }
    public void setUserID(long value) { this.userID = value; }

    public long getCompanyID() { return companyID; }
    public void setCompanyID(long value) { this.companyID = value; }

    public String getStartDatetime() { return startDatetime; }
    public void setStartDatetime(String value) { this.startDatetime = value; }

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double value) { this.latitude = value; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double value) { this.longitude = value; }

    public long getStatus() { return status; }
    public void setStatus(long value) { this.status = value; }

    public long getTotal() { return total; }
    public void setTotal(long value) { this.total = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String value) { this.updatedAt = value; }
}

// Service.java

class Service {
    private long bookingsID;
    private long servicesID;

    public long getBookingsID() { return bookingsID; }
    public void setBookingsID(long value) { this.bookingsID = value; }

    public long getServicesID() { return servicesID; }
    public void setServicesID(long value) { this.servicesID = value; }
}

// Worker.java


class Worker {
    private long bookingID;
    private long userID;

    public long getBookingID() { return bookingID; }
    public void setBookingID(long value) { this.bookingID = value; }

    public long getUserID() { return userID; }
    public void setUserID(long value) { this.userID = value; }
}


