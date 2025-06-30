package com.example.happy_wallet_mobile.ViewModel.Authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ResetPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ResetPasswordResponse;
import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;

public class ResetPasswordViewModel extends ViewModel {
    private final AuthRepository repo = new AuthRepository();
    private final MediatorLiveData<ResetPasswordResponse> _response = new MediatorLiveData<>();

    public LiveData<ResetPasswordResponse> getResetPasswordResponse() {
        return _response;
    }

    public void resetPassword(String email, String otp, String newPassword) {
        LiveData<ResetPasswordResponse> source = repo.resetPassword(new ResetPasswordRequest(email, otp, newPassword));
        _response.addSource(source, r -> {
            _response.setValue(r);
            _response.removeSource(source);
        });
    }
}
