package com.example.drivercab10.model;

import java.io.Serializable;

public class OrderNotification implements Serializable {
    private String status_id;
    private String s_longitude;
    private String mobile;
    private String d_longitude;
    private String s_address;
    private String rate;
    private String request_id;
    private String user_name;
    private String d_latitude;
    private String d_address;
    private String waiting_time;
    private String s_latitude;



    public String getStatus_id() {
        return status_id;
    }

    public String getS_longitude() {
        return s_longitude;
    }

    public String getMobile() {
        return mobile;
    }

    public String getD_longitude() {
        return d_longitude;
    }

    public String getS_address() {
        return s_address;
    }

    public String getRate() {
        return rate;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getD_latitude() {
        return d_latitude;
    }

    public String getD_address() {
        return d_address;
    }

    public String getWaiting_time() {
        return waiting_time;
    }

    public String getS_latitude() {
        return s_latitude;
    }
}
