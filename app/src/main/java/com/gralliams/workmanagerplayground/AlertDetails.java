package com.gralliams.workmanagerplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlertDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);

        String extra = getIntent().getStringExtra("Extra");
        TextView text = findViewById(R.id.textView_alert);
        text.setText(extra);

    }
}