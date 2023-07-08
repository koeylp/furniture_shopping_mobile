package com.bibon.furnitureshopping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibon.furnitureshopping.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView imageView = (ImageView) findViewById(R.id.ivSplash);
        imageView.animate().translationX(1000).setDuration(1000).setStartDelay(3000);

        TextView textView = (TextView) findViewById(R.id.tvSplash);
        textView.animate().translationX(1000).setDuration(1000).setStartDelay(3000);


        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(SplashActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
                finish();
            }


        });
        thread.start();
    }
}