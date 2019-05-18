package com.example.drivercab10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseProviderStarted;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.util.Constants;

public class FinishRateActivity extends AppCompatActivity {

    private TextView tvTitleToolbar;
    private ImageView imgBack;
    private RatingBar ratingUser;
    private float rateUser = 0;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_rate);

        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        ratingUser = findViewById(R.id.ratingUser);
        send = findViewById(R.id.send);
        tvTitleToolbar.setText("");
        ratingUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateUser = v;
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateUser != 0) {
                    Log.e("RequestId",getIntent().getIntExtra("RequestId", 0)+"");
                    rateTravel(Constants.RATE_USER, getIntent().getIntExtra("RequestId", 0));
                } else {
                    Constants.showDialog(FinishRateActivity.this, getResources().getString(R.string.Rate));
                }
            }
        });
    }

    public void rateTravel(final String URL, int RequestId) {
        Constants.showSimpleProgressDialog(FinishRateActivity.this, "Loading");
        new UserAPI().rateTravel(String.valueOf(Integer.valueOf((int) rateUser)), "", URL, RequestId + "", new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseProviderStarted result1 = (ResponseProviderStarted) result;
                if (result1.isSuccess()) {
                    startActivity(new Intent(FinishRateActivity.this, HomeActivity.class));
                    finish();
                    Constants.showDialog(FinishRateActivity.this, result1.getMessage());

                } else {
                    Constants.showDialog(FinishRateActivity.this, result1.getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FinishRateActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FinishRateActivity.this, message);
            }
        });

    }

}
