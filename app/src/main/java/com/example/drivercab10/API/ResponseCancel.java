package com.example.drivercab10.API;

public class ResponseCancel {
    private boolean success;
    private String request_id;
    private int status;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getRequest_id() {
        return request_id;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
