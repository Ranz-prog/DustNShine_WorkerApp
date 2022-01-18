package com.example.dnsworker.Models;

public class TaskModel {

    private int clientImage;
    private String clientName, clientLocation, clientNContactNumber;


    public TaskModel(int clientImage, String clientName, String clientLocation, String clientNContactNumber) {
        this.clientImage = clientImage;
        this.clientName = clientName;
        this.clientLocation = clientLocation;
        this.clientNContactNumber = clientNContactNumber;
    }

    public int getClientImage() {
        return clientImage;
    }

    public void setClientImage(int clientImage) {
        this.clientImage = clientImage;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getClientNContactNumber() {
        return clientNContactNumber;
    }

    public void setClientNContactNumber(String clientNContactNumber) {
        this.clientNContactNumber = clientNContactNumber;
    }
}

