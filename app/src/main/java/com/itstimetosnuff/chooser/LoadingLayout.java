package com.itstimetosnuff.chooser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingLayout extends AppCompatActivity {

    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingLayout.this, MainActivity.class));
                finish();
            }
        };

        new Timer().schedule(timerTask, 3000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        timerTask.cancel();
    }
}