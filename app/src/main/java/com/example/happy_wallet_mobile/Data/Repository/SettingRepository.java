package com.example.happy_wallet_mobile.Data.Repository;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.SettingService;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.TransactionService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Setting.ChangePasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Setting.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.Response;

public class SettingRepository {
    private final SettingService settingService;

    public SettingRepository() {
        settingService = APIClient.getRetrofit().create(SettingService.class);
    }


    public void changePassword(String token, String currentPassword, String newPassword,
                               ChangePasswordCallback callback) {
        Call<ChangePasswordResponse> call = settingService.changePassword(
                "Bearer " + token,
                new ChangePasswordRequest(currentPassword, newPassword)
        );

        call.enqueue(new retrofit2.Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    String errMsg = "Đổi mật khẩu thất bại";
                    try {
                        if (response.errorBody() != null) {
                            errMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onFailure(errMsg);
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                callback.onFailure("Lỗi mạng: " + t.getMessage());
            }
        });
    }

    public interface ChangePasswordCallback {
        void onSuccess(String message);
        void onFailure(String errorMessage);
    }

}
