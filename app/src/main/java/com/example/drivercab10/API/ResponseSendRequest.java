package com.example.drivercab10.API;


public class ResponseSendRequest {
    private boolean status;
    private String message;
    private boolean success;
    private int request_id;
    private String current_provider;
    private String address;
    private String latitude;
    private String longitude;
    private String d_address;
    private String d_latitude;
    private String d_longitude;
    private String error;
    private String error_messages;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getRequest_id() {
        return request_id;
    }

    public String getCurrent_provider() {
        return current_provider;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getD_address() {
        return d_address;
    }

    public String getD_latitude() {
        return d_latitude;
    }

    public String getD_longitude() {
        return d_longitude;
    }

    public String getError() {
        return error;
    }

    public String getError_messages() {
        return error_messages;
    }
}
