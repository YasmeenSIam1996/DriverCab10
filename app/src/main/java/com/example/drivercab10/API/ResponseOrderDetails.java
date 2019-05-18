package com.example.drivercab10.API;


import java.io.Serializable;

public class ResponseOrderDetails {

    private boolean success;
    private Details details;

    public boolean isSuccess() {
        return success;
    }

    public Details getDetails() {
        return details;
    }

    public class Details implements Serializable {
        private int request_id;
        private String taxi_name;
        private String s_address;
        private String service_picture;
        private String d_address;
        private String date;
        private String provider_name;
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
        private double s_latitude;
        private double s_longitude;
        private double d_latitude;
        private double d_longitude;

        public int getRequest_id() {
            return request_id;
        }

        public String getTaxi_name() {
            return taxi_name;
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

        public String getProvider_name() {
            return provider_name;
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

        public String getService_picture() {
            return service_picture;
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

        @Override
        public String toString() {
            return "Details{" +
                    "request_id=" + request_id +
                    ", taxi_name='" + taxi_name + '\'' +
                    ", s_address='" + s_address + '\'' +
                    ", service_picture='" + service_picture + '\'' +
                    ", d_address='" + d_address + '\'' +
                    ", date='" + date + '\'' +
                    ", provider_name='" + provider_name + '\'' +
                    ", picture='" + picture + '\'' +
                    ", total=" + total +
                    ", map_image='" + map_image + '\'' +
                    ", provider_id=" + provider_id +
                    ", total_time=" + total_time +
                    ", payment_mode='" + payment_mode + '\'' +
                    ", min_price=" + min_price +
                    ", base_price=" + base_price +
                    ", time_price=" + time_price +
                    ", distance_unit='" + distance_unit + '\'' +
                    ", distance_travel=" + distance_travel +
                    ", distance_price=" + distance_price +
                    ", tax_price=" + tax_price +
                    ", booking_fee=" + booking_fee +
                    ", currency='" + currency + '\'' +
                    ", s_latitude=" + s_latitude +
                    ", s_longitude=" + s_longitude +
                    ", d_latitude=" + d_latitude +
                    ", d_longitude=" + d_longitude +
                    '}';
        }
    }
}
