package com.example.dnsworker.Model.ClientBookingModel;

import java.util.Date;

public class Customer {
    private long id;
    private String first_name;
    private String last_name;
    private String mobile_number;
    private String email;
    private Object emailVerifiedAt;
    private Date createdAt;
    private Date updatedAt;
    private CustomerPivot pivot;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getFirstName() { return first_name; }
    public void setFirstName(String value) { this.first_name = value; }

    public String getLastName() { return last_name; }
    public void setLastName(String value) { this.last_name = value; }

    public String getMobileNumber() { return mobile_number; }
    public void setMobileNumber(String value) { this.mobile_number = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public Object getEmailVerifiedAt() { return emailVerifiedAt; }
    public void setEmailVerifiedAt(Object value) { this.emailVerifiedAt = value; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date value) { this.createdAt = value; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date value) { this.updatedAt = value; }

    public CustomerPivot getPivot() { return pivot; }
    public void setPivot(CustomerPivot value) { this.pivot = value; }
}