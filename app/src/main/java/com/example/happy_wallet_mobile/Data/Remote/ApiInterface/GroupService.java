package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.CreateGroupRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.CreateGroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GroupService {
    @GET("funds")
    Call<List<GroupResponse>> getAllFunds();

    @POST("funds")
    Call<CreateGroupResponse> createFund(@Body CreateGroupRequest request);
}
