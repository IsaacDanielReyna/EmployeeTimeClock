package com.isaacreyna.employeetimeclock.interfaces;

import com.isaacreyna.employeetimeclock.models.Login;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {
    public static final String BASE_URL = "http://www.isaacreyna.com/";

    @FormUrlEncoded @POST("system/login/login.php") Call<Login> postlogin(@Field("username") String u, @Field("password") String p);
    @GET("system/login/login.php") Call<Login> getlogin(@Query("username") String u, @Query("password") String p);

    class Factory{
        public static Service service;
        public static Service getInstance(){
            if (service == null){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                service = retrofit.create(Service.class);
                return service;
            }else{
                return service;
            }
        }
    }

}
