package com.example.happy_wallet_mobile.ViewModel.Community;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.View.Utilities.Event;

public class CommunityViewModel extends ViewModel {
    private final MutableLiveData<Event<Fragment>> _navigateContent = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateContent = _navigateContent;

    public void navigateContent(Fragment fragment) {
        _navigateContent.setValue(new Event<>(fragment));
    }
}
