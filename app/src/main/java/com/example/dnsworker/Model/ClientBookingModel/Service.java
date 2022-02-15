package com.example.dnsworker.Model.ClientBookingModel;

import java.util.Date;

public class Service {
    private long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private ServicePivot pivot;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date value) { this.createdAt = value; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date value) { this.updatedAt = value; }

    public ServicePivot getPivot() { return pivot; }
    public void setPivot(ServicePivot value) { this.pivot = value; }
}