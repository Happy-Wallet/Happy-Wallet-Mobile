package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Model.Destination;
import com.example.happy_wallet_mobile.View.Utilities.Event;

public class MainViewModel {

    private final MutableLiveData<Event<Destination>> _navigate = new MutableLiveData<>();
    public LiveData<Event<Destination>> navigate = _navigate;

    public void onNavItemClicked(Destination destination) {
        _navigate.setValue(new Event<>(destination));
    }
}


