package com.example.drivercab10.API;

import com.example.drivercab10.model.User;
import com.google.gson.annotations.SerializedName;


public class ResponseSign {
    private boolean status;
    private String message;
    @SerializedName("data")
    private User user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
