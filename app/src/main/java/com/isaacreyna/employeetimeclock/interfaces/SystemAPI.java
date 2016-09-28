package com.isaacreyna.employeetimeclock.interfaces;

import android.app.Activity;
import android.util.Log;

import com.isaacreyna.employeetimeclock.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SystemAPI {
    public final String TAG = "APISYSTEM";
    public User user = new User();


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
                if (!response.isSuccessful()){
                    Log.i(TAG, "Error: " + response.code());
                }
                else {
                    // Request returned with success.
                    user = response.body();
                    //TODO: WHY THE FUCK ISN'T THIS BOOLEAN WORKING? ok wtf its working????
                    //Log.i(TAG, "Result: " + user.username + "(" + user.isLogin + "), " + user.email);
                    if (user.isLogin == 1)
                        Log.i(TAG, "inside systemapi.java result true");
                    else
                        Log.i(TAG, "inside systemapi.java result false");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i(TAG,"Error: " + t.getMessage());
            }
        });
    }
}
