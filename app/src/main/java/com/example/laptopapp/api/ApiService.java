package com.example.laptopapp.api;


import com.example.laptopapp.Model.Bill;
import com.example.laptopapp.Model.BillDetail;
import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.Model.Event;
import com.example.laptopapp.Model.Id_token;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.Model.Login;
import com.example.laptopapp.Model.Order;
import com.example.laptopapp.Model.UpdateUser;
import com.example.laptopapp.Model.User;
import com.example.laptopapp.Model.Voucher;
import com.example.laptopapp.Model.listProduct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/product")
    Call<ArrayList<Laptop>> laptop();
    @GET("api/newproduct")
    Call<ArrayList<Laptop>> newlaptop();
    @GET("api/product/{id}")
    Call<Laptop> detail(@Path("id") String id );
    @GET("api/event")
    Call<ArrayList<Event>> event();
    @GET("api/voucher")
    Call<ArrayList<Voucher>> voucher();
    @GET("api/bill/{id}")
    Call<ArrayList<Bill>> bill(@Path("id") String id );
    @POST("api/login")
    Call<Login> login(@Body() Id_token token_id);
    @POST("api/order")
    Call<Array> order(@Body Order order);
    @PATCH("api/user/{id}")
    Call<User> Updateuser(@Path("id") String id, @Body UpdateUser updateUser);
    @GET("api/user/{id}")
     Call<User> Takeinformuser(@Path("id") String id);
    @GET("/api/billDetail/{id}")
    Call<ArrayList<BillDetail>> takebilldetali(@Path("id") String id);
}
