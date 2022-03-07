package com.example.dnsworker.Model.ClientBookingModel;

public class Review {
    private int id;
    private int booking_id;
    private String comment;
    private double rating;
    private String created_at;
    private String updated_at;


    public Review(int id, int booking_id, String comment, double rating, String created_at, String updated_at) {
        this.id = id;
        this.booking_id = booking_id;
        this.comment = comment;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

