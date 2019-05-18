package com.example.drivercab10;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseAccept;
import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.model.OrderNotification;
import com.example.drivercab10.util.Constants;
import com.skyfishjy.library.RippleBackground;

public class OrderCounterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView tvTitleToolbar, counter, txtUserName, txtLocation;

    private RippleBackground pulse;
    private Button btnAcceptOrder, btnRejectOrder;
    private int RequestID;
    private RatingBar rating;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_counter);
        intiViews();

        final OrderNotification orderNotification = (OrderNotification) getIntent().getSerializableExtra("OrderNotification");
        if (orderNotification != null) {
            counter.setText(orderNotification.getWaiting_time() + "");
            RequestID = Integer.valueOf(orderNotification.getRequest_id());
            txtUserName.setText(orderNotification.getUser_name());
            txtLocation.setText(orderNotification.getS_address());
            rating.setRating(Float.valueOf(orderNotification.getRate()));
            t = new Thread() {
                public void run() {
                    for (int i = Integer.valueOf(orderNotification.getWaiting_time()); i >= 0; i--) {
                        try {
                            Log.e("currentThread", Thread.currentThread().isInterrupted() + "");
                            Log.e("currentThread", i + "");

                            if (Thread.currentThread().isInterrupted() == false) {

                                final int a = i;
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        counter.setText("" + (a - 1));
                                    }
                                });
                                if (a == 0) {
                                    startActivity(new Intent(OrderCounterActivity.this, HomeActivity.class));
                                    finish();
                                }
                                sleep(1000);
                                if (a == Integer.valueOf(orderNotification.getWaiting_time()) - 1) {
                                    //Remove Same Notification
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.cancel(2);
                                    //
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
        }

    }

    private void intiViews() {
        btnAcceptOrder = findViewById(R.id.btnAcceptOrder);
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        btnRejectOrder = findViewById(R.id.btnRejectOrder);
        counter = findViewById(R.id.counter);
        txtUserName = findViewById(R.id.txtUserName);
        txtLocation = findViewById(R.id.txtLocation);
        rating = findViewById(R.id.rating);
        imgBack.setVisibility(View.GONE);
        tvTitleToolbar.setText(getResources().getString(R.string.newOrder));
        pulse = findViewById(R.id.pulse);
        pulse.startRippleAnimation();

        btnAcceptOrder.setOnClickListener(this);
        btnRejectOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAcceptOrder:
                sendRequest(Constants.SERVICES_ACCEPT, RequestID);

                break;

            case R.id.btnRejectOrder:
                sendRequest(Constants.SERVICES_REJECT, RequestID);

                break;
        }
    }


    public void sendRequest(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(OrderCounterActivity.this, "Loading");
        new UserAPI().serviceAccept(URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseAccept result1 = (ResponseAccept) result;
                if (result1.isSuccess()) {
                    if (URL.equals(Constants.SERVICES_ACCEPT)) {
                        Intent intent = new Intent(OrderCounterActivity.this, HomeActivity.class);
                        intent.putExtra("OrderAccept", result1.getRequests());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(OrderCounterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Constants.showDialog(OrderCounterActivity.this, result1.getMessage());
                } else {
                    Constants.showDialog(OrderCounterActivity.this, result1.getError());

                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(OrderCounterActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(OrderCounterActivity.this, message);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.interrupt();

    }
}
