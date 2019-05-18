package com.example.drivercab10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UploadFilesActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView tvTitleToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_files);

        findViews();
    }


    private void findViews() {
        imgBack = findViewById(R.id.imgBack);
        tvTitleToolbar = findViewById(R.id.tvTitleToolbar);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvTitleToolbar.setText(getResources().getString(R.string.navUpload));
    }
}
