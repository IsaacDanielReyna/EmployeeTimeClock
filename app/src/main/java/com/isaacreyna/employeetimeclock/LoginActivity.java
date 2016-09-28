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
import com.isaacreyna.employeetimeclock.interfaces.SystemAPI;
import com.isaacreyna.employeetimeclock.interfaces.User;
import com.isaacreyna.employeetimeclock.interfaces.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private View mProgressView;
    public final String TAG = "APISYSTEM";
    public User user = new User();
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

        EditText username_field = (EditText) findViewById(R.id.username);
        EditText password_field = (EditText) findViewById(R.id.password);

        String username = username_field.getText().toString();
        String password = password_field.getText().toString();

        showProgress(true);

        //TODO: username and password validation (too short, missing symbols, etc.)
        login(username, password);
    }

    public void login(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI service = retrofit.create(UserAPI.class);
        Call<User> requestUser = service.authenticate(username, password);
        requestUser.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                showProgress(false);
                if (!response.isSuccessful()){
                    Log.i(TAG, "Error: " + response.code());
                }
                else {
                    user = response.body();
                    if (user.isLogin == 1) {
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = Settings.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("USER", json);
                        editor.putBoolean("IsLoggedIn", true);
                        editor.commit();
                        StartMainActivity(user);
                    }
                    else {
                        Log.i(TAG, "result false");
                        Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG,"Error: " + t.getMessage());
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
