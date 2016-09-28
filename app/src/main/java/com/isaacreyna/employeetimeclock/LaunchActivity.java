package com.isaacreyna.employeetimeclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IsLoggedIn();
        super.onCreate(savedInstanceState);
    }

    void IsLoggedIn()
    {
        SharedPreferences Settings;
        Settings = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        Boolean b = Settings.getBoolean("IsLoggedIn", false);
        if (b) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else
        {
            finish();
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
        }
    }
}
