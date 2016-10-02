package com.isaacreyna.employeetimeclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialActivity extends AppCompatActivity {
    private static final String TAG = "Isaac";
    private TextView text;
    private SharedPreferences Settings;
    public static final String BASE_URL = "http://www.isaacreyna.com/seniorproject/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        IsLoggedIn();
        text = (TextView) findViewById(R.id.InitialText);
        Button register_button = (Button) findViewById(R.id.button_register);
        Button login_button = (Button) findViewById(R.id.button_login);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {StartRegistrationActivity();
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartLoginActivity();
            }
        });
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
    }

    private void StartRegistrationActivity(){
        Intent intent = new Intent (this, RegistrationActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void StartLoginActivity(){
        Intent intent = new Intent (this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
