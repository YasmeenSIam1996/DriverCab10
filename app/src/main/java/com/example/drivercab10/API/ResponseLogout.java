package com.example.drivercab10.API;


public class ResponseLogout {

    private boolean success;
    private String error;

    public boolean isStatus() {
        return success;
    }

    public void setStatus(boolean status) {
        this.success = status;
    }

    public String getMessage() {
        return error;
    }

    public void setMessage(String message) {
        this.error = message;
    }
}
