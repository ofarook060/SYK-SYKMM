/*
 * Copyright (C) 2019  All rights reserved for FaraSource (ABBAS GHASEMI)
 * https://farasource.com
 */
package com.syk.sykmm.ui;

import static com.syk.sykmm.BuildApp.showSplash;
import static com.syk.sykmm.BuildApp.splashTime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.syk.sykmm.R;
import com.syk.sykmm.api.CheckNetworkStatus;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle superSave) {
        super.onCreate(superSave);

        if (!showSplash) {
            main();
            return;
        }

        setContentView(R.layout.activity_splash);
        if (!CheckNetworkStatus.isOnline())
            Toast.makeText(this, "شبکه در دسترس نیست!", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                main();
            }
        }, splashTime * 1000L);
    }

    private void main() {
        if (isFinishing()) {
            return;
        }
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}