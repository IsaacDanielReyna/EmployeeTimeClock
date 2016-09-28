package com.isaacreyna.employeetimeclock.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by IDR on 5/31/2016.
 */
public interface UserAPI {
    public static final String BASE_URL = "http://www.isaacreyna.com/seniorproject/";
    @GET("api")
    Call<User> authenticate(@Query("username") String u, @Query("password") String p); //Call<User> authenticate(@Path("email") String email, @Path("password") String password);
}
