package com.example.happy_wallet_mobile.API;

import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<UserResponse> createUser(@Body User user);
}
