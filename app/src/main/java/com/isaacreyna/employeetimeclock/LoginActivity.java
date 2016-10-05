package com.isaacreyna.employeetimeclock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isaacreyna.employeetimeclock.models.Login;
import com.isaacreyna.employeetimeclock.interfaces.Service;
import com.isaacreyna.employeetimeclock.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private View mProgressView;
    public final String TAG = "APISYSTEM";
    public Login login = new Login();
    private SharedPreferences Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // IDR: Setup listeners
        Button login_button = (Button) findViewById(R.id.sign_in);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startLogin();
            }
        });

        //setup vars
        mProgressView = findViewById(R.id.login_progress);
        Settings = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
    }

    public void startLogin(){
        Boolean error = false;
        EditText username_field = (EditText) findViewById(R.id.username);
        EditText password_field = (EditText) findViewById(R.id.password);

        if(username_field.getText().toString().isEmpty()) {
            username_field.setError("Username Should not be blank");
            error = true;
        }

        if(password_field.getText().toString().isEmpty()) {
            password_field.setError("UserName Should not be blank");
            error = true;
        }

        String username = username_field.getText().toString();
        String password = password_field.getText().toString();
        if (!error) {
            login(username, password);
            showProgress(true);
        }


    }

    public void login(String username, String password){
        Service.Factory.getInstance().postlogin(username, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(!response.isSuccessful())
                    Log.i(TAG, "Unsuccessful Response: " + response.code());
                else {
                    showProgress(false);

                    String message = "";
                    for(String m : response.body().alert.messages)
                    {
                        message += m + " ";
                        Log.i(TAG, "Message: " + m);
                    }

                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                    if (response.body().result) {
                        User user = response.body().user;
                        Toast.makeText(LoginActivity.this, "Success: " + user.username, Toast.LENGTH_LONG).show();
                        /**/
                        SharedPreferences.Editor editor = Settings.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("USER", json);
                        editor.putBoolean("IsLoggedIn", true);
                        editor.commit();
                        StartMainActivity(user);
                        /**/
                    }
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.i(TAG,"onFailure: " + t.getMessage());
            }
        });
    }


    public void StartMainActivity(User u)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            /** // HIDE LOGIN FORM?
             mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
             mLoginFormView.animate().setDuration(shortAnimTime).alpha(
             show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
            });
             /**/

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
