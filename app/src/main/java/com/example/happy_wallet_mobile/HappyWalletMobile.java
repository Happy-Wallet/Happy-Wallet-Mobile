package com.example.happy_wallet_mobile;

import android.app.Application;
import android.util.Log;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;

public class HappyWalletMobile extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferences.init(this);

        loadAuthToken();
    }

    private void loadAuthToken() {
        String savedToken = UserPreferences.getToken();

        if (savedToken != null && !savedToken.isEmpty()) {
            APIClient.setAuthToken(savedToken);
            Log.d("HappyWalletApp", "JWT Token đã được tải và set cho APIClient.");
        } else {
            Log.d("HappyWalletApp", "Không tìm thấy JWT Token đã lưu. Người dùng có thể cần đăng nhập.");
        }
    }
}