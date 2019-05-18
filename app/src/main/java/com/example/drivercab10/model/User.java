package com.example.drivercab10.model;

import java.io.Serializable;

public class User implements Serializable {
    private String error_messages;
    private int id;
    private String name;
    private String first_name;
    private String last_name;
    private String mobile;
    private String gender;
    private String email;
    private String is_approved;
    private String picture;
    private String token;
    private int token_expiry;
    private String login_by;
    private String social_unique_id;
    private String payment_mode_status;
    private String currency_code;
    private String country;
    private String timezone;
    private String wallet_bay_key;
    private String error;

    public String getError() {
        return error;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public String getPicture() {
        return picture;
    }

    public String getToken() {
        return token;
    }

    public int getToken_expiry() {
        return token_expiry;
    }

    public String getLogin_by() {
        return login_by;
    }

    public String getSocial_unique_id() {
        return social_unique_id;
    }

    public String getPayment_mode_status() {
        return payment_mode_status;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public String getCountry() {
        return country;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getWallet_bay_key() {
        return wallet_bay_key;
    }

    public String getError_messages() {
        return error_messages;
    }

    public void setError_messages(String error_messages) {
        this.error_messages = error_messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken_expiry(int token_expiry) {
        this.token_expiry = token_expiry;
    }

    public void setLogin_by(String login_by) {
        this.login_by = login_by;
    }

    public void setSocial_unique_id(String social_unique_id) {
        this.social_unique_id = social_unique_id;
    }

    public void setPayment_mode_status(String payment_mode_status) {
        this.payment_mode_status = payment_mode_status;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setWallet_bay_key(String wallet_bay_key) {
        this.wallet_bay_key = wallet_bay_key;
    }
}
