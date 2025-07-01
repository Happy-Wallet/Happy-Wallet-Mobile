package com.example.happy_wallet_mobile.ViewModel.Authentication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;
import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;

public class SignInViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();

    private final MediatorLiveData<LoginResponse> _loginResponse = new MediatorLiveData<>();

    public LiveData<LoginResponse> getLoginResponse() {
        return _loginResponse;
    }

    public boolean loginMock(String userName, String password) {
        return "".equals(userName) && "".equals(password);
    }

    public void login(String userName, String password, Context context) { // Thêm context
        if (userName == null || password == null || userName.isEmpty() || password.isEmpty()) {
            _loginResponse.setValue(null);
            return;
        }

        LiveData<LoginResponse> source = authRepository.login(new LoginRequest(userName, password), context);
        _loginResponse.addSource(source, response -> {
            _loginResponse.setValue(response);
            _loginResponse.removeSource(source);
        });
    }
}
