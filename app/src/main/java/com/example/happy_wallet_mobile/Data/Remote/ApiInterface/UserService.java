package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.User.CreateUserRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    // GET /users
    @GET("users")
    Call<List<UserResponse>> getAllUsers();

    // POST /users
    @POST("users")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest request);
}
