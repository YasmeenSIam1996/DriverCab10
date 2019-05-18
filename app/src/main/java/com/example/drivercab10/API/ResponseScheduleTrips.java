package com.example.drivercab10.API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseScheduleTrips {

    private boolean success;
    @SerializedName("data")
    private List<Request> requests;

    public boolean isSuccess() {
        return success;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public class Request {
        private int request_id;
        private int later;
        private int request_type;
        private String service_type_name;
        private int provider_id;
        private String provider_picture;
        private int provider_status;
        private double amount;
        private String user_name;
        private String user_picture;
        private int user_id;
        private double s_latitude;
        private double s_longitude;
        private double d_latitude;
        private double d_longitude;
        private String type_picture;
        private int status;
        private String s_address;
        private String d_address;
        private String requested_time;
        private String request_start_time;
        private String provider_name;
        private double distance;
        private double price;
        private String distance_unit;
        private String currency;

        public int getRequest_id() {
            return request_id;
        }

        public int getLater() {
            return later;
        }

        public int getRequest_type() {
            return request_type;
        }

        public String getService_type_name() {
            return service_type_name;
        }

        public int getProvider_id() {
            return provider_id;
        }

        public String getProvider_picture() {
            return provider_picture;
        }

        public int getProvider_status() {
            return provider_status;
        }

        public double getAmount() {
            return amount;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_picture() {
            return user_picture;
        }

        public int getUser_id() {
            return user_id;
        }

        public double getS_latitude() {
            return s_latitude;
        }

        public double getS_longitude() {
            return s_longitude;
        }

        public double getD_latitude() {
            return d_latitude;
        }

        public double getD_longitude() {
            return d_longitude;
        }

        public String getType_picture() {
            return type_picture;
        }

        public int getStatus() {
            return status;
        }

        public String getS_address() {
            return s_address;
        }

        public String getD_address() {
            return d_address;
        }

        public String getRequested_time() {
            return requested_time;
        }

        public String getRequest_start_time() {
            return request_start_time;
        }

        public String getProvider_name() {
            return provider_name;
        }

        public double getDistance() {
            return distance;
        }

        public double getPrice() {
            return price;
        }

        public String getDistance_unit() {
            return distance_unit;
        }

        public String getCurrency() {
            return currency;
        }
    }

}
