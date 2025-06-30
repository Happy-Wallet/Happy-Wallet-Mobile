package com.example.happy_wallet_mobile.ViewModel.Authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ForgotPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ForgotPasswordResponse;
import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;

public class ForgotPasswordViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();

    private final MediatorLiveData<ForgotPasswordResponse> _forgotPasswordResponse = new MediatorLiveData <>();

    public LiveData<ForgotPasswordResponse> getForgotPasswordResponse() {
        return _forgotPasswordResponse;
    }

    public void forgotPassword(String email){
        if (email == null || email.isEmpty()){
            _forgotPasswordResponse.setValue(null);
            return;
        }

        LiveData<ForgotPasswordResponse> source = authRepository.forgotPassword(new ForgotPasswordRequest(email));

        _forgotPasswordResponse.addSource(source, response -> {
            _forgotPasswordResponse.setValue(response);
            _forgotPasswordResponse.removeSource(source);
        });
    }
}
