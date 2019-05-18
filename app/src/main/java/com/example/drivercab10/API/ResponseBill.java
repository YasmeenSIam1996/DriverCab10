package com.example.drivercab10.API;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBill {
    private boolean success;
    @SerializedName("requests")
    private List<BillObject> billObject;

    public boolean isSuccess() {
        return success;
    }

    public List<BillObject> getBillObject() {
        return billObject;
    }

    public class BillObject {
        private int id;
        private String taxi_name;
        private int request_type;
        private String s_address;
        private String d_address;
        private String date;
        private String time;
        private String user_name;
        private String picture;
        private double total;
        private String map_image;
        private int provider_id;
        private double total_time;
        private String payment_mode;
        private double min_price;
        private double base_price;
        private double time_price;
        private String distance_unit;
        private double distance_travel;
        private double distance_price;
        private double tax_price;
        private double booking_fee;
        private String currency;

        public int getId() {
            return id;
        }

        public String getTaxi_name() {
            return taxi_name;
        }

        public int getRequest_type() {
            return request_type;
        }

        public String getS_address() {
            return s_address;
        }

        public String getD_address() {
            return d_address;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getPicture() {
            return picture;
        }

        public double getTotal() {
            return total;
        }

        public String getMap_image() {
            return map_image;
        }

        public int getProvider_id() {
            return provider_id;
        }

        public double getTotal_time() {
            return total_time;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public double getMin_price() {
            return min_price;
        }

        public double getBase_price() {
            return base_price;
        }

        public double getTime_price() {
            return time_price;
        }

        public String getDistance_unit() {
            return distance_unit;
        }

        public double getDistance_travel() {
            return distance_travel;
        }

        public double getDistance_price() {
            return distance_price;
        }

        public double getTax_price() {
            return tax_price;
        }

        public double getBooking_fee() {
            return booking_fee;
        }

        public String getCurrency() {
            return currency;
        }
    }

}
