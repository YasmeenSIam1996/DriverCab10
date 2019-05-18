package com.example.drivercab10;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListPopupWindow;

import com.example.drivercab10.adapter.LanguageAdapter;
import com.example.drivercab10.model.Language;
import com.example.drivercab10.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectLangActivity extends AppCompatActivity implements View.OnClickListener {

    View layoutChooseLang;
    Button btnLetsGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);

        layoutChooseLang = findViewById(R.id.layoutChooseLang);
        btnLetsGo = findViewById(R.id.btnLetsGo);
        layoutChooseLang.setOnClickListener(this);
        btnLetsGo.setOnClickListener(this);

        Utils.hideStatusBar(this);
    }

    public void showLangMenu(View view) {

        List<Language> languageList = new ArrayList<>();

        languageList.add(new Language(getResources().getString(R.string.ar), R.drawable.flag_egp));
        languageList.add(new Language(getResources().getString(R.string.english), R.drawable.flag_us));

        LanguageAdapter languageAdapter = new LanguageAdapter(this, languageList);

        final ListPopupWindow listPopupWindow = new ListPopupWindow(SelectLangActivity.this);
        listPopupWindow.setAnchorView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            listPopupWindow.setDropDownGravity(Gravity.END);
        }

        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setWidth(600);
        listPopupWindow.setAdapter(languageAdapter);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {


                    listPopupWindow.dismiss();
                    finish();
                    startActivity(getIntent());
                } else {


                    listPopupWindow.dismiss();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        listPopupWindow.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutChooseLang:
                showLangMenu(v);
                break;

            case R.id.btnLetsGo:
                startActivity(new Intent(this , SignInActivity.class));
                break;
        }
    }
}
