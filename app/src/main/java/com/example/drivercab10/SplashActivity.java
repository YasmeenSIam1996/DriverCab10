package com.example.drivercab10;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.drivercab10.API.ResponseCheckStatus;
import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.util.Constants;

public class SplashActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 3333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hide status bar

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLocationPermission();

            }
        }, 2000);
    }


    public void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setTitle("GPS setting!");
        alertDialog.setMessage("GPS is not enabled, Do you want to go to settings menu? ");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // if from activity
                ActivityCompat.finishAffinity(SplashActivity.this);
            }
        });

        alertDialog.show();
    }


    private void getLocationPermission() {
        if (isLocationEnabled(SplashActivity.this)) {

            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                CheckLogin();
            }

        } else {
            showSettingAlert();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("requestCode", requestCode + "");


        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (isLocationEnabled(SplashActivity.this)) {
                CheckLogin();
            } else {
                showSettingAlert();

            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                CheckLogin();
            }
        }
    }

    private void CheckLogin() {
        if (ApplicationController.getInstance().IsUserLoggedIn()) {
            checkStatus();


        } else {
            startActivity(new Intent(getApplicationContext(), SelectLangActivity.class));
            finish();
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }


    public void checkStatus() {
        new UserAPI().checkStatus(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseCheckStatus result1 = (ResponseCheckStatus) result;
                if (result1.isSuccess()) {
                    int num = result1.getCheckStatuses().getStatus_provider_id();
                    if (num == 1 || num == 2 || num == 3 || num == 4) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("TempOrder", result1.getCheckStatuses());
                        startActivity(intent);
                        finish();
                    } else if (num == 5) {
                        Intent intent = new Intent(getApplicationContext(), FinishRateActivity.class);
                        intent.putExtra("TempOrder", result1.getCheckStatuses());
                        intent.putExtra("RequestId", result1.getCheckStatuses().getRequest_id());


                        startActivity(intent);
                        finish();
                    }

                } else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SplashActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SplashActivity.this, message);
            }
        });

    }

}