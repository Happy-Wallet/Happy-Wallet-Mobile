package com.example.happy_wallet_mobile.View.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.SplashViewModel;

public class SplashActivity extends AppCompatActivity {

    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        splashViewModel.getNavigateNext().observe(this, navigate -> {
            if (navigate) {
                // to SignInActivity
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
            }
        });

        splashViewModel.startTimer();
    }
}
