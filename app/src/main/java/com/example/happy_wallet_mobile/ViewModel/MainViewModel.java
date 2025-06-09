package com.example.happy_wallet_mobile.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.MainDestination;
import com.example.happy_wallet_mobile.Model.SubDestination;
import com.example.happy_wallet_mobile.View.Utilities.Event;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event<MainDestination>> _navigateMainBelow = new MutableLiveData<>();
    public LiveData<Event<MainDestination>> navigateMainBelow = _navigateMainBelow;

    private final MutableLiveData<Event<SubDestination>> _navigateSubBelow = new MutableLiveData<>();
    public LiveData<Event<SubDestination>> navigateSubBelow = _navigateSubBelow;

    private final MutableLiveData<Event<SubDestination>> _navigateSubAbove = new MutableLiveData<>();
    public LiveData<Event<SubDestination>> navigateSubAbove = _navigateSubAbove;

    public void onNavItemClickedMainBelow(MainDestination mainDestination) {
        _navigateMainBelow.setValue(new Event<>(mainDestination));
        Log.d("MainViewModel", "navigate main below " + mainDestination.name());
    }

    public void onNavItemClickedSubBelow(SubDestination subDestination) {
        _navigateSubBelow.setValue(new Event<>(subDestination));
        Log.d("MainViewModel", "navigate sub below " + subDestination.name());
    }

    public void onNavItemClickedSubAbove(SubDestination subDestination) {
        _navigateSubAbove.setValue(new Event<>(subDestination));
        Log.d("MainViewModel", "navigate sub above " + subDestination.name());
    }

}


