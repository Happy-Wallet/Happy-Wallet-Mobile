package com.example.happy_wallet_mobile.Data.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.AuthService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ForgotPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ResetPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ForgotPasswordResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthService authService;

    public AuthRepository() {
        authService = APIClient.getRetrofit().create(AuthService.class);
    }

    // login
    public LiveData<LoginResponse> login(LoginRequest request) {
        MutableLiveData<LoginResponse> result = new MutableLiveData<>();
        authService.login(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("AuthRepository", "Login failed: " + response.message());
                    result.setValue(null);  // Hoặc tạo một error state
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("AuthRepository", "Login error: ", t);
                result.setValue(null);
            }
        });
        return result;
    }

    // register
    public LiveData<RegisterResponse> register(RegisterRequest request) {
        MutableLiveData<RegisterResponse> result = new MutableLiveData<>();
        authService.register(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("AuthRepository", "Register failed: " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("AuthRepository", "Register error: ", t);
                result.setValue(null);
            }
        });
        return result;
    }

    // forgot password
    public LiveData<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest request) {
        MutableLiveData<ForgotPasswordResponse> result = new MutableLiveData<>();
        authService.forgotPassword(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("AuthRepository", "Forgot password failed: " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Log.e("AuthRepository", "Forgot password error: ", t);
                result.setValue(null);
            }
        });
        return result;
    }

    // reset password
    public LiveData<ResetPasswordResponse> resetPassword(ResetPasswordRequest request) {
        MutableLiveData<ResetPasswordResponse> result = new MutableLiveData<>();
        authService.resetPassword(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("AuthRepository", "Reset password failed: " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                Log.e("AuthRepository", "Reset password error: ", t);
                result.setValue(null);
            }
        });
        return result;
    }
}
