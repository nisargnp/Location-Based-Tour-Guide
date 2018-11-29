package edu.umd.cs.cmsc436.location_basedtourguide.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import edu.umd.cs.cmsc436.location_basedtourguide.Main.MainActivity;
import edu.umd.cs.cmsc436.location_basedtourguide.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.determinateBar);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);

        new Thread(() -> {

            int interval = SPLASH_DELAY / 100;
            for (int i = 0; i < 101; i++) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int progress = i;
                runOnUiThread(() -> progressBar.setProgress(progress));
            }

            runOnUiThread(() -> {
                finish();
                startActivity(intent);
            });

        }).start();

    }

}
