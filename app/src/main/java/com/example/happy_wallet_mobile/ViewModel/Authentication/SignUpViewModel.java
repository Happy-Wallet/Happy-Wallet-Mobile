package com.example.happy_wallet_mobile.ViewModel.Authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.ForgotPasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.ForgotPasswordResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;
import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;

public class SignUpViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();

    private final MediatorLiveData <RegisterResponse> _registerResponse = new MediatorLiveData<>();

    public LiveData<RegisterResponse> getRegisterResponse() {
        return _registerResponse;
    }

    public void register(String email, String userName, String password){
        if (email == null || email.isEmpty()
        || userName == null || userName.isEmpty()
        || password == null || password.isEmpty()){
            _registerResponse.setValue(null);
            return;
        }

        LiveData<RegisterResponse> source = authRepository.register(new RegisterRequest(email, userName, password));
        _registerResponse.addSource(source, response -> {
            _registerResponse.setValue(response);
            _registerResponse.removeSource(source);
        });
    }
}
