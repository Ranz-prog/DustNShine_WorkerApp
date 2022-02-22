package com.example.dnsworker.Model.ClientBookingModel;

import java.util.Date;

public class ClientBookingModel {
    private ClientBookData[] data;
    private String message;

    public ClientBookData[] getData() { return data; }
    public void setData(ClientBookData[] value) { this.data = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }
}
