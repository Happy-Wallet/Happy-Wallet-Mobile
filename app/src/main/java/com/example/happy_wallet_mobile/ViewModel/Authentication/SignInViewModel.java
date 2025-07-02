package com.example.happy_wallet_mobile.ViewModel.Authentication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.LoginRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;
import com.example.happy_wallet_mobile.Data.Repository.UserRepository;
import com.example.happy_wallet_mobile.Model.User;

public class SignInViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();
    private final UserRepository userRepository = new UserRepository();

    private final MediatorLiveData<LoginResponse> _loginResponse = new MediatorLiveData<>();

    public LiveData<LoginResponse> getLoginResponse() {
        return _loginResponse;
    }


    private final MediatorLiveData<User> _userProfile = new MediatorLiveData<>();
    public LiveData<User> getUserProfile() {
        return _userProfile;
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


    public void fetchUserProfileAndSave(String token) {
        LiveData<UserResponse> source = userRepository.getUserProfile("Bearer " + token);
        _userProfile.addSource(source, userResponse -> {
            if (userResponse != null) {
                // Convert UserResponse to User model
                User user = new User();
                user.setId(userResponse.getId());
                user.setEmail(userResponse.getEmail());
                user.setUserName(userResponse.getUsername());
                user.setAvatarUrl(userResponse.getAvatarUrl());
                user.setDateOfBirth(userResponse.getDateOfBirth());

                // Save to preferences
                UserPreferences.saveUser(user, token);

                _userProfile.setValue(user);
            } else {
                _userProfile.setValue(null);
            }
            _userProfile.removeSource(source);
        });
    }
}
