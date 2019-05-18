package com.example.drivercab10.API;


public class ResponseProviderStarted {

    private boolean success;
    private int status;
    private String message;
    private String error;

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}