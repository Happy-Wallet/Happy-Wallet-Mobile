package com.example.happy_wallet_mobile.ViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.View.Utilities.Event;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event<Fragment>> _navigateMainBelow = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateMainBelow = _navigateMainBelow;

    private final MutableLiveData<Event<Fragment>> _navigateSubBelow = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateSubBelow = _navigateSubBelow;

    private final MutableLiveData<Event<Fragment>> _navigateSubAbove = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateSubAbove = _navigateSubAbove;

    public void navigateMainBelow(Fragment fragment) {
        _navigateMainBelow.setValue(new Event<>(fragment));
    }

    public void navigateSubBelow(Fragment fragment) {
        _navigateSubBelow.setValue(new Event<>(fragment));
    }

    public void navigateSubAbove(Fragment fragment) {
        _navigateSubAbove.setValue(new Event<>(fragment));
    }
}

