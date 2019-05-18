package com.example.drivercab10.API;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePaymentMethods {
    private boolean success;
    @SerializedName("payment_modes")
    private List<String> payment_modes;

    public boolean isSuccess() {
        return success;
    }

    public List<String> getPayment_modes() {
        return payment_modes;
    }


}
