package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.happy_wallet_mobile.Data.Remote.Request.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.RegisterResponse;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}
