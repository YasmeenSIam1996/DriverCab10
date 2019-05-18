package com.example.drivercab10;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.drivercab10.API.ResponseError;
import com.example.drivercab10.API.ResponseSign;
import com.example.drivercab10.API.UserAPI;
import com.example.drivercab10.AppController.ApplicationController;
import com.example.drivercab10.Interfaces.UniversalCallBack;
import com.example.drivercab10.util.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUp;
    private EditText txtSignUp1, txtSignUp2, txtSignUp3, txtSignUp4,
            EditPass, EditFirstName, EditLastName, EditEmail, EditPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        btnSignUp = findViewById(R.id.btnSignUp);
        EditPass = findViewById(R.id.EditPass);
        EditFirstName = findViewById(R.id.EditName);
        EditLastName = findViewById(R.id.EditLastName);
        EditEmail = findViewById(R.id.EditEmail);
        EditPhone = findViewById(R.id.EditPhone);
        btnSignUp.setOnClickListener(this);

    }


    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {


            if (source.equals(" ")) {
                int startSelection = txtSignUp1.getSelectionStart();
                int endSelection = txtSignUp1.getSelectionEnd();
                txtSignUp1.setText(txtSignUp1.getText().toString().trim());
                txtSignUp1.setSelection(startSelection, endSelection);

            }

            return null;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                if (validation()) {
                    String first = EditFirstName.getText().toString().trim();
                    String last = EditLastName.getText().toString().trim();
                    String email = EditEmail.getText().toString().trim();
                    String pass = EditPass.getText().toString().trim();
                    String phone = EditPhone.getText().toString().trim();

                    SignUp(first, last, phone, pass, first + " " + last, email);
                }
                showSignUpDialog();
                break;
        }
    }

    private void showSignUpDialog() {
        Dialog signUpDialog = new Dialog(this);
        signUpDialog.setContentView(R.layout.dialog_complete_signup);
        signUpDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtSignUp1 = signUpDialog.findViewById(R.id.txtSignUp1);
        txtSignUp2 = signUpDialog.findViewById(R.id.txtSignUp2);
        txtSignUp3 = signUpDialog.findViewById(R.id.txtSignUp3);
        txtSignUp4 = signUpDialog.findViewById(R.id.txtSignUp4);

        txtSignUp1.setFilters(new InputFilter[]{filter});


        signUpDialog.show();
    }

    private boolean validation() {
        if (!Constants.ValidationEmptyInput(EditFirstName)) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmptyField));
            return false;
        } else if (!Constants.ValidationEmptyInput(EditLastName)) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else if (!Constants.ValidationEmptyInput(EditEmail)) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else if (!Constants.isValidEmail(EditEmail.getText().toString())) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.NotVaildEmail));

            return false;

        } else if (!Constants.ValidationEmptyInput(EditPhone)) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else if (!Constants.ValidationEmptyInput(EditPass)) {
            Constants.showDialog(SignUpActivity.this, getResources().getString(R.string.EmptyField));

            return false;

        } else {
            return true;

        }
    }

    private void SignUp(String first, String last, String phone, String pass, String name, String email) {
        Constants.showSimpleProgressDialog(SignUpActivity.this, "Loading");
        new UserAPI().Register(first, last, email, pass, phone, name, new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseSign responseSign = (ResponseSign) result;
                if (responseSign.isStatus()) {
                    ApplicationController.getInstance().loginUser(responseSign.getUser());
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Constants.showDialog(SignUpActivity.this, responseSign.getUser().getError());
                }

            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(SignUpActivity.this, responseError.getMessage());
                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(SignUpActivity.this, message);
            }
        });
    }

}
