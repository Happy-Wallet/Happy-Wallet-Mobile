package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Setting.ChangePasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Setting.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SettingService {
    @PUT("/settings/change-password")
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String bearerToken,
            @Body ChangePasswordRequest request
    );

}
