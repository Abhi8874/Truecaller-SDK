package com.maurya.truecallersdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.truecaller.android.sdk.TruecallerSDK;

import java.lang.reflect.TypeVariable;

public class DashboardActivity extends AppCompatActivity {

    TextView textName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textName = findViewById(R.id.tv_name);

        Intent intent = new Intent();
        String name = getIntent().getStringExtra("name");
        textName.setText(name);

    }
}