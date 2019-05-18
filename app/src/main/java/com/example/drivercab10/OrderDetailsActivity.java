package com.example.drivercab10;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseOrderDetails;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.util.Constants;
import com.squareup.picasso.Picasso;

public class OrderDetailsActivity extends AppCompatActivity {


        private TextView tvOrderTime, tvOrderCarType, tvPrice, currencyType, tvFirstPoint,
                tvEndPoint, tvDistance, txtCarStyle, txtDriverName, tvDriveDuration, tvCrossedDistance,
                tvMinWage, tvMainPrice, tvBookingFee, tvTaxiService, tvTotalPrice, PriceUnit;

        private ImageView imgCar, imgDriver, MapImage;
        Intent intent;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order_details);
            intViews();
            Log.e("RequestId",getIntent().getIntExtra("RequestId",-1)+"");
            orderDetails(getIntent().getIntExtra("RequestId",-1)+"");
        }

        private void intViews() {
            tvOrderTime = findViewById(R.id.tvOrderTime);
            tvOrderCarType = findViewById(R.id.tvOrderCarType);
            tvPrice = findViewById(R.id.tvPrice);
            currencyType = findViewById(R.id.currencyType);
            tvFirstPoint = findViewById(R.id.tvFirstPoint);
            tvEndPoint = findViewById(R.id.tvEndPoint);
            tvDistance = findViewById(R.id.tvDistance);
            txtCarStyle = findViewById(R.id.txtCarStyle);
            txtDriverName = findViewById(R.id.txtDriverName);
            tvDriveDuration = findViewById(R.id.tvDriveDuration);
            tvCrossedDistance = findViewById(R.id.tvCrossedDistance);
            tvMinWage = findViewById(R.id.tvMinWage);
            tvMainPrice = findViewById(R.id.tvMainPrice);
            tvBookingFee = findViewById(R.id.tvBookingFee);
            tvTaxiService = findViewById(R.id.tvTaxiService);
            tvTotalPrice = findViewById(R.id.tvTotalPrice);
            PriceUnit = findViewById(R.id.PriceUnit);
            imgCar = findViewById(R.id.imgCar);
            imgDriver = findViewById(R.id.imgDriver);
            MapImage = findViewById(R.id.MapImage);
            intent=new Intent(OrderDetailsActivity.this,MapsActivity.class);
            MapImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }

        public void orderDetails(String request_id) {
            Constants.showSimpleProgressDialog(OrderDetailsActivity.this, "Loading");
            new UserAPI().RequestDetails(request_id, new UniversalCallBack() {
                @Override
                public void onResponse(Object result) {
                    ResponseOrderDetails result1 = (ResponseOrderDetails) result;
                    if (result1.isSuccess()) {
                        ResponseOrderDetails.Details details = result1.getDetails();
                        intent.putExtra("Details",details);
                        tvOrderTime.setText(details.getDate());
                        tvOrderCarType.setText(details.getTaxi_name());
                        tvPrice.setText(details.getTotal() + "");
                        currencyType.setText(details.getCurrency());
                        tvFirstPoint.setText(details.getS_address());
                        tvEndPoint.setText(details.getD_address());
                        tvDistance.setText(details.getDistance_travel() + details.getDistance_unit());
                        txtCarStyle.setText(details.getTaxi_name());
                        txtDriverName.setText(details.getProvider_name());
                        tvDriveDuration.setText(details.getTotal_time() + "");
                        tvCrossedDistance.setText(details.getDistance_travel() + "");
                        tvMinWage.setText(details.getMin_price() + "");
                        tvMainPrice.setText(details.getBase_price() + "");
                        tvBookingFee.setText(details.getBooking_fee() + "");
                        tvTaxiService.setText(details.getBooking_fee() + "");
                        tvTotalPrice.setText(details.getTotal() + "");
                        PriceUnit.setText(details.getCurrency());

                        Picasso.with(OrderDetailsActivity.this).load(details.getMap_image()).fit()
                                .into(MapImage);


                        Picasso.with(OrderDetailsActivity.this).load(ApplicationController.getInstance().getUser().getPicture()).fit()
                                .into(imgDriver);
                        Picasso.with(OrderDetailsActivity.this).load(details.getService_picture()).fit()
                                .into(imgCar);
                    }

                }

                @Override
                public void onFailure(Object result) {
                    if (result != null) {
                        ResponseError responseError = (ResponseError) result;
                        Constants.showDialog(OrderDetailsActivity.this, responseError.getMessage());
                    }
                }

                @Override
                public void onFinish() {
                    Constants.removeProgressDialog();
                }

                @Override
                public void OnError(String message) {
                    Constants.showDialog((Activity) OrderDetailsActivity.this, message);
                }
            });

        }

    }
