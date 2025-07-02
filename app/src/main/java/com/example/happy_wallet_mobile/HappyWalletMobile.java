package com.example.happy_wallet_mobile;

import android.app.Application;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;

public class HappyWalletMobile extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Khởi tạo UserPreferences 1 lần duy nhất
        UserPreferences.init(this);

    }
}
