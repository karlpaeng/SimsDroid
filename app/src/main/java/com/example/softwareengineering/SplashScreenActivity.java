package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreenActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(SplashScreenActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Intent intent = databaseHelper.ifUserAlreadyExists() ? new Intent(getApplicationContext(), LoginActivity.class) :
                        new Intent(getApplicationContext(), UserCreationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        };
        handler.postDelayed(r, 1000);


    }
}