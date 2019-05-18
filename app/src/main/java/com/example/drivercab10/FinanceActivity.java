package com.example.drivercab10;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseBill;
import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseObject;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.adapter.FinanceAdapter;
import com.example.drivercab10.util.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FinanceActivity extends AppCompatActivity {

    private RecyclerView rvFinance;
    private FinanceAdapter adapter;
    private List<ResponseBill.BillObject> list;
    private TextView tvTitleToolbar, tvCalTo, calFrom;
    private ImageView imgBack;
    private LinearLayout layoutFrom, layoutTo;
    private Calendar calendarCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        findViews();


        setupAdapter();
    }

    //show calender dialog
    private void showDateDialog(final int num) {
        calendarCurrentTime = Calendar.getInstance();
        int DAY_OF_MONTH = calendarCurrentTime.get(Calendar.DAY_OF_MONTH);
        int MONTH = calendarCurrentTime.get(Calendar.MONTH);
        int YEAR = calendarCurrentTime.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month++;
                String monthStr = month + "";
                if (monthStr.length() == 1) {
                    monthStr = "0" + monthStr;
                }
                String dayOfMonthStr = dayOfMonth + "";
                if (dayOfMonthStr.length() == 1) {
                    dayOfMonthStr = "0" + dayOfMonthStr;
                }
                String date = year + "-" + monthStr + "-" + dayOfMonthStr;
                if (num == 1) {
                    calFrom.setText(date);
                } else if (num == 2) {
                    tvCalTo.setText(date);
                }
                sendFilter();
            }
        }, YEAR, MONTH, DAY_OF_MONTH);

        datePickerDialog.show();
    }

    private void findViews() {
        rvFinance = findViewById(R.id.rvFinance);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);
        imgBack = findViewById(R.id.imgBack);
        layoutFrom = findViewById(R.id.layoutFrom);
        layoutTo = findViewById(R.id.layoutTo);
        tvTitleToolbar.setText(getResources().getString(R.string.navFinance));
        tvCalTo = findViewById(R.id.tvCalTo);
        calFrom = findViewById(R.id.calFrom);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layoutFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(1);
            }
        });
        layoutTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(2);

            }
        });
    }

    private void setupAdapter() {
        list = new ArrayList<>();
        adapter = new FinanceAdapter(this, list);
        rvFinance.setAdapter(adapter);
        rvFinance.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendFilter() {
        String fromTxt = calFrom.getText().toString(), toTxt = tvCalTo.getText().toString();
        if (!fromTxt.equals("0000-00-00") && !toTxt.equals("0000-00-00")) {
            filterOrders(fromTxt, toTxt);
        }
    }

    public void filterOrders(String from, String to) {
        Constants.showSimpleProgressDialog(FinanceActivity.this, "Loading");
        new UserAPI().FilterOrder(from, to, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseBill result1 = (ResponseBill) result;
                if (result1.isSuccess()) {
                    if (result1.getBillObject().size() != 0) {
                        list.clear();
                        list.addAll(result1.getBillObject());
                        adapter.notifyDataSetChanged();
                    } else {
                        list.clear();
                        adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(FinanceActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(FinanceActivity.this, message);
            }
        });

    }


}
