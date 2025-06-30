package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ForgotPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ResetPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ForgotPasswordResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ResetPasswordResponse;

public interface AuthService {
    @POST("/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest req);

    @POST("/auth/forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest req);

    @POST("/auth/reset-password")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest req);
}
