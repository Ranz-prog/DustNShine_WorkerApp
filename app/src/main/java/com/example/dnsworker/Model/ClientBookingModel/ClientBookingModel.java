package com.example.dnsworker.Model.ClientBookingModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ClientBookingModel {
    private ArrayList<ClientBookData> data;
    private String message;


    public ArrayList<ClientBookData> getData() { return data; }
    public void setData(ArrayList<ClientBookData> value) {
        this.data = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }
}
