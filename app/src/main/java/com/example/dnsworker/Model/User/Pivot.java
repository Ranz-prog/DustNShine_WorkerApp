package com.example.dnsworker.Model.User;

public class Pivot {
    private int model_id;
    private int role_id;
    private String model_type;

    public Pivot(int model_id, int role_id, String model_type) {
        this.model_id = model_id;
        this.role_id = role_id;
        this.model_type = model_type;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }
}
