package com.isaacreyna.employeetimeclock;

import com.isaacreyna.employeetimeclock.models.UdacityCatalog;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by IDR on 5/31/2016.
 */
public interface UdacityService {

    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/"; //    //https://www.udacity.com/public-api/v0/courses
    @GET("courses")
    Call<UdacityCatalog> listCatalog();
}
