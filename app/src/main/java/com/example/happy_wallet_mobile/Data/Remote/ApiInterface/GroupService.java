package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.CreateGroupRequest; // Sử dụng CreateGroupRequest làm body cho PUT
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.CreateGroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse; // Import MessageResponse cho phản hồi PUT/DELETE

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT; // Thêm import này
import retrofit2.http.Path;

public interface GroupService {
    @GET("funds")
    Call<List<GroupResponse>> getAllFunds();

    @GET("funds/{fundId}")
    Call<GroupResponse> getFundDetails(@Path("fundId") int fundId);

    @POST("funds")
    Call<CreateGroupResponse> createFund(@Body CreateGroupRequest request);

    @PUT("funds/{fundId}")
    Call<MessageResponse> updateFund(@Path("fundId") int fundId, @Body CreateGroupRequest request);
}