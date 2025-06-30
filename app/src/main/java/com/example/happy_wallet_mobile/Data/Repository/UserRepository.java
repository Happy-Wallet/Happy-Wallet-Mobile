package com.example.happy_wallet_mobile.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.UserService;
import com.example.happy_wallet_mobile.Data.Remote.Request.User.CreateUserRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.CreateUserResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private final UserService userService;

    public UserRepository() {
        userService = APIClient.getRetrofit().create(UserService.class);
    }

    public LiveData<List<UserResponse>> getAllUsers() {
        MutableLiveData<List<UserResponse>> data = new MutableLiveData<>();
        userService.getAllUsers().enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<CreateUserResponse> createUser(CreateUserRequest request) {
        MutableLiveData<CreateUserResponse> data = new MutableLiveData<>();
        userService.createUser(request).enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
