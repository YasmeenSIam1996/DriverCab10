package com.example.drivercab10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseSign;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.util.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNewUser;
    Button btnSignIn;
    private EditText EditEmail, EditPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                ApplicationController.getInstance().putDeviceToken(deviceToken);

            }

        });
        findViews();

    }

    private void findViews() {
        tvNewUser = findViewById(R.id.tvNewUser);
        btnSignIn = findViewById(R.id.btnSignIn);
        EditEmail = findViewById(R.id.EditEmail);
        EditPass = findViewById(R.id.EditPass);


        tvNewUser.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNewUser:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.btnSignIn:
                if (validation()) {

                    String email = EditEmail.getText().toString().trim();
                    String pass = EditPass.getText().toString().trim();

                    SignIn(pass, email);
                }
                break;
        }
    }

    private void SignIn(String pass, String email) {
        Constants.showSimpleProgressDialog(SignInActivity.this, "Loading");
        new UserAPI().Login(email, pass, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign.isStatus()) {
                    ApplicationController.getInstance().loginUser(responseSign.getUser());
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Constants.showDialog(SignInActivity.this, responseSign.getUser().getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignInActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignInActivity.this, message);
            }
        });
    }

    private boolean validation() {
        if (!Constants.ValidationEmptyInput(EditEmail)) {
            Constants.showDialog(SignInActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else if (!Constants.isValidEmail(EditEmail.getText().toString())) {
            Constants.showDialog(SignInActivity.this, getResources().getString(R.string.NotVaildEmail));

            return false;

        } else if (!Constants.ValidationEmptyInput(EditPass)) {
            Constants.showDialog(SignInActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else {
            return true;

        }
    }
}
