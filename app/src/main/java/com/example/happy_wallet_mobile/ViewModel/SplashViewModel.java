package com.example.happy_wallet_mobile.ViewModel;

import android.os.Handler;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

    private final MutableLiveData<Boolean> navigateNext = new MutableLiveData<>();

    public LiveData<Boolean> getNavigateNext() {
        return navigateNext;
    }

    public void startTimer() {
        new Handler().postDelayed(() -> navigateNext.setValue(true), 2000);
    }
}
