package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Destination;
import com.example.happy_wallet_mobile.View.Utilities.Event;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event<Destination>> _navigateBelow = new MutableLiveData<>();
    public LiveData<Event<Destination>> navigateBelow = _navigateBelow;

    private final MutableLiveData<Event<Destination>> _navigateAbove = new MutableLiveData<>();
    public LiveData<Event<Destination>> navigateAbove = _navigateAbove;

    public void onNavItemClickedBelow(Destination destination) {
        _navigateBelow.setValue(new Event<>(destination));
    }

    public void onNavItemClickedAbove(Destination destination) {
        _navigateAbove.setValue(new Event<>(destination));
    }

}


