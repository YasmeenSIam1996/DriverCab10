package com.example.drivercab10;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseHistoryTrips;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.adapter.TripsAdapter;
import com.example.drivercab10.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class YourTripsActivity extends AppCompatActivity {

    LinearLayout layoutNoTrips;
    RecyclerView rvTrips;
    List<ResponseHistoryTrips.Request> list;

    ImageView imgBack;
    TextView tvTitleToolbar;

    TripsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_trips);

        findViews();

        setAdapter();
        getHistory();



    }

    private void findViews() {
        layoutNoTrips = findViewById(R.id.layoutNoTrips);
        imgBack = findViewById(R.id.imgBack);
        rvTrips = findViewById(R.id.rvTrips);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);

        list = new ArrayList<>();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvTitleToolbar.setText(getResources().getString(R.string.navYourTrips));
    }

    private void setAdapter() {
        adapter = new TripsAdapter(this, list);
        rvTrips.setAdapter(adapter);
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getHistory() {
        Constants.showSimpleProgressDialog(YourTripsActivity.this, "Loading");
        new UserAPI().getHistory(Constants.HISTORY,new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseHistoryTrips result1 = (ResponseHistoryTrips) result;
                if (result1.isSuccess()) {
                    list.clear();
                    list.addAll(result1.getRequests());
                    adapter.notifyDataSetChanged();
                    if (list.size() != 0) {
                        layoutNoTrips.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(YourTripsActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog( YourTripsActivity.this, message);
            }
        });

    }

}
