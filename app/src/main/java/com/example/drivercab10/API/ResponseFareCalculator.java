package com.example.drivercab10.API;


public class ResponseFareCalculator {

    private boolean success;
    private double estimated_fare;
    private double min_fare;
    private double base_price;
    private double tax_price;
    private double booking_fee;
    private String distance_unit;
    private String currency;
    private String difference;

    public boolean isStatus() {
        return success;
    }

    public double getEstimated_fare() {
        return estimated_fare;
    }

    public double getMin_fare() {
        return min_fare;
    }

    public double getBase_price() {
        return base_price;
    }

    public double getTax_price() {
        return tax_price;
    }

    public double getBooking_fee() {
        return booking_fee;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDifference() {
        return difference;
    }
}
