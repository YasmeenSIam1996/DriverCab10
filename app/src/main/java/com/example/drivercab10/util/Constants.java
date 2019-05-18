package com.example.drivercab10.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseLogout;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.R;
import com.example.drivercab10.SelectLangActivity;
import com.tapadoo.alerter.Alerter;

import java.util.Locale;


/**
 * Created by Ahmed hani on 11/5/2017.
 */

public class Constants {

    public static final String FONTS_APP = "sf_pro_text_semibold.otf";

    public static final String BASE_URL = "http://159.65.82.19/providerApi/";
    public static final String LOGIN = BASE_URL + "login";
    public static final String REGISTER = BASE_URL + "register";
    public static final String LOGOUT = BASE_URL + "logout";
    public static final String SERVICES_LIST = BASE_URL + "serviceList";

    public static final String SOCIAL_LOGIN = BASE_URL + "social_login";
    public static final String ANDROID = "android";
    public static final String PAYMENT_METHODS = BASE_URL + "getPaymentModes";
    public static final String CANCEL_REQUEST = BASE_URL + "cancelrequest";
    public static final String HISTORY = BASE_URL + "history";
    public static final String REQUEST_DETAILS = BASE_URL + "orderDetails";
    public static final String FILTER_ORDER = BASE_URL + "filterOrders";
    public static final String SERVICES_ACCEPT = BASE_URL + "serviceAccept";
    public static final String SERVICES_REJECT = BASE_URL + "serviceReject";
    public static final String GET_ONLINE_STATUS = BASE_URL + "getOnlineStatus";
    public static final String SET_ONLINE_STATUS = BASE_URL + "changeOnlineStatus";
    public static final String CHECK_STATUS = BASE_URL + "checkStatus";
    public static final String STARTED_REQUEST = BASE_URL + "serviceStarted";
    public static final String STARTED_OROVIDER = BASE_URL + "providerStarted";
    public static final String CONFIRM_OROVIDER = BASE_URL + "arrived";
    public static final String END_REQUEST = BASE_URL + "serviceCompleted";
    public static final String RATE_USER = BASE_URL + "rateUser";

    public static Dialog mProgressDialog;


    public static final String ORIGINS = "origins";
    public static final String DESTINATION = "destinations";
    public static final String SENSOR = "sensor";
    public static final String MODE = "mode";
    public static final String DISTANCE = "distance";
    public static final String TIME = "time";
    public static final Object GOOGLE_API_KEY = "AIzaSyCCxiJeR_AjA5OuYARjHVb-gfnPaydx0ZU";
    public static final String LANGUAGE = "language";
    public static final String GOOGLE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?";


    public static String getLanguage() {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en")) {
            return "en";
        } else {
            return "ar";
        }
    }

    public static void showDialog(Activity context, String message) {
        Alerter.create(context)
                .setText(message)
                .hideIcon()
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }


    public static void showSimpleProgressDialog(Context context, String msg) {
        if (context != null) {
            mProgressDialog = new Dialog(context, R.style.DialogSlideAnim_leftright);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.animation_loading);
            TextView tv_title = mProgressDialog.findViewById(R.id.tv_title);
            tv_title.setText(msg);

            mProgressDialog.show();
        }
    }

    public static void removeProgressDialog() {
        if (null != mProgressDialog)
            mProgressDialog.dismiss();
    }

    public static boolean ValidationEmptyInput(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return false;
        }
        return true;

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showCustomDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);
        TextView textView = dialog.findViewById(R.id.text);
        textView.setText(context.getResources().getString(R.string.logo_out));
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ApplicationController.getInstance().IsUserLoggedIn()) {
                    logout(context, dialog);

                } else {
                    context.startActivity(new Intent(context, SelectLangActivity.class));
                    ((Activity) context).finish();
                }

            }
        });

        dialog.show();

    }

    public static void logout(final Context context, final Dialog dialog) {
        Constants.showSimpleProgressDialog(context, "Loading");

        new UserAPI().Logout(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseLogout result1 = (ResponseLogout) result;
                Log.d("LOGOUT1: ", result1.isStatus() + "");

                if (result1.isStatus()) {
                    Log.d("LOGOUT1: ", "");

                    ApplicationController.getInstance().DeleteUser();
                    context.startActivity(new Intent(context, SelectLangActivity.class));
                    ((Activity) context).finish();
                } else {
                    Log.d("LOGOUT2: ", "");

                    removeProgressDialog();
                    dialog.dismiss();
                    Constants.showDialog((Activity) context, result1.getMessage());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    dialog.dismiss();
                    Constants.showDialog((Activity) context, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                removeProgressDialog();
                dialog.dismiss();

            }

            @Override
            public void OnError(String message) {
                dialog.dismiss();
                Constants.showDialog((Activity) context, message);
            }
        });
    }


}
