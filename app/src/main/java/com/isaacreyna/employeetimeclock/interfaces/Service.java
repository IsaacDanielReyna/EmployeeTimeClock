package com.isaacreyna.employeetimeclock.interfaces;

import com.isaacreyna.employeetimeclock.models.Company.Companies;
import com.isaacreyna.employeetimeclock.models.Company.Result;
import com.isaacreyna.employeetimeclock.models.Invite;
import com.isaacreyna.employeetimeclock.models.Login;
import com.isaacreyna.employeetimeclock.models.Register;
import com.isaacreyna.employeetimeclock.models.TimeClockSelect.TimeClockSelect;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    public static final String BASE_URL = "http://www.isaacreyna.com/";
    @FormUrlEncoded @POST("system/login/login.php") Call<Login> postlogin(@Field("username") String u, @Field("password") String p);
    @FormUrlEncoded @POST("system/api/start.php") Call<Companies> companies(@Field("token") String token, @Field("option") String option, @Field("task") String task);
    @FormUrlEncoded @POST("system/api/") Call<Result> update_user(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<Result> post(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<Register> register(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<Companies> ListCompanies(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<Login> login(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<Invite> inviteEmployee(@FieldMap HashMap<String, String> fields);
    @FormUrlEncoded @POST("system/api/") Call<TimeClockSelect> TimeClockSelect(@FieldMap HashMap<String, String> fields);


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
