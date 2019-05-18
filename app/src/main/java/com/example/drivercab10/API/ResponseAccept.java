package com.example.drivercab10.API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseAccept {

    private boolean success;
    @SerializedName("data")
    private Accept requests;
    private String message;
    private String error;

    public Accept getRequests() {
        return requests;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public class Accept implements Serializable {
        private int request_id;
        private int user_id;
        private int request_type;
        private int status;
        private int provider_status;
        private double s_longitude;
        private double s_latitude;
        private String s_address;
        private double d_latitude;
        private double d_longitude;
        private String d_address;
        private String service_type_name;
        private String user_name;
        private String user_picture;
        private String user_mobile;
        private float user_rating;

        public int getRequest_id() {
            return request_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getRequest_type() {
            return request_type;
        }

        public int getStatus() {
            return status;
        }

        public int getProvider_status() {
            return provider_status;
        }

        public double getS_longitude() {
            return s_longitude;
        }

        public double getS_latitude() {
            return s_latitude;
        }

        public String getS_address() {
            return s_address;
        }

        public double getD_latitude() {
            return d_latitude;
        }

        public double getD_longitude() {
            return d_longitude;
        }

        public String getD_address() {
            return d_address;
        }

        public String getService_type_name() {
            return service_type_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public float getUser_rating() {
            return user_rating;
        }
    }
}