package com.example.dnsworker.Models;

public class BookingModel {

    private int booking_clientImage;
    private String booking_clientName,booking_clientLocation, boooking_clientNumber;

    public BookingModel(int booking_clientImage, String booking_clientName, String booking_clientLocation, String boooking_clientNumber) {
        this.booking_clientImage = booking_clientImage;
        this.booking_clientName = booking_clientName;
        this.booking_clientLocation = booking_clientLocation;
        this.boooking_clientNumber = boooking_clientNumber;
    }

    public int getBooking_clientImage() {
        return booking_clientImage;
    }

    public void setBooking_clientImage(int booking_clientImage) {
        this.booking_clientImage = booking_clientImage;
    }

    public String getBooking_clientName() {
        return booking_clientName;
    }

    public void setBooking_clientName(String booking_clientName) {
        this.booking_clientName = booking_clientName;
    }

    public String getBooking_clientLocation() {
        return booking_clientLocation;
    }

    public void setBooking_clientLocation(String booking_clientLocation) {
        this.booking_clientLocation = booking_clientLocation;
    }

    public String getBoooking_clientNumber() {
        return boooking_clientNumber;
    }

    public void setBoooking_clientNumber(String boooking_clientNumber) {
        this.boooking_clientNumber = boooking_clientNumber;
    }
}
