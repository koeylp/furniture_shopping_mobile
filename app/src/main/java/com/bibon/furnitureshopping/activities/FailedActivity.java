package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bibon.furnitureshopping.R;

public class FailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        Button btn_home = (Button) findViewById(R.id.btn_home);
        TextView tv_failed_text = findViewById(R.id.tv_failed_text);

        // Intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        String information = args.getString("Information");
        tv_failed_text.setText(information);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
