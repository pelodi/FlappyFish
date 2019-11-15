package com.example.flappyfish;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FishView fishView;
    private Handler handler = new Handler();
    private final static long interval = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fishView = new FishView(this);
        setContentView(fishView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        fishView.invalidate();
                    }
                });
            }
        }, 0, interval);
    }
}
