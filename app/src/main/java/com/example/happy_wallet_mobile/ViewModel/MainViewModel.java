package com.example.happy_wallet_mobile.ViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.View.Utilities.Event; // Sử dụng Event từ gói Utilities của bạn

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Event<Fragment>> _navigateMainBelow = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateMainBelow = _navigateMainBelow; // LiveData cho điều hướng chính

    private final MutableLiveData<Event<Fragment>> _navigateSubBelow = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateSubBelow = _navigateSubBelow; // LiveData cho điều hướng phụ bên dưới

    private final MutableLiveData<Event<Fragment>> _navigateSubAbove = new MutableLiveData<>();
    public LiveData<Event<Fragment>> navigateSubAbove = _navigateSubAbove; // LiveData cho điều hướng phụ bên trên

    // LiveData mới cho sự kiện điều hướng quay lại
    private final MutableLiveData<Event<Boolean>> _navigateBackEvent = new MutableLiveData<>();
    public LiveData<Event<Boolean>> getNavigateBackEvent() {
        return _navigateBackEvent;
    }

    public MainViewModel() {
    }

    public void navigateMainBelow(Fragment fragment) {
        _navigateMainBelow.setValue(new Event<>(fragment));
    }

    public void navigateSubBelow(Fragment fragment) {
        _navigateSubBelow.setValue(new Event<>(fragment));
    }

    public void navigateSubAbove(Fragment fragment) {
        _navigateSubAbove.setValue(new Event<>(fragment));
    }

    public void navigateBack() {
        _navigateBackEvent.setValue(new Event<>(true));
    }
}