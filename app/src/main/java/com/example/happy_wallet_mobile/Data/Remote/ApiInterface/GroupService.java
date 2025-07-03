package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.CreateGroupRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.CreateGroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface GroupService {
    @GET("funds")
    Call<List<GroupResponse>> getAllFunds();

    @GET("funds/{fundId}")
    Call<GroupResponse> getFundDetails(@Path("fundId") int fundId);

    @POST("funds")
    Call<CreateGroupResponse> createFund(@Body CreateGroupRequest request);

    @PUT("funds/{fundId}")
    Call<MessageResponse> updateFund(@Path("fundId") int fundId, @Body CreateGroupRequest request);

    @DELETE("funds/{fundId}/members/{memberId}")
    Call<MessageResponse> removeMember(@Path("fundId") int fundId, @Path("memberId") int memberId);

    @FormUrlEncoded
    @PUT("funds/{fundId}/members/{memberId}/role")
    Call<MessageResponse> updateMemberRole(@Path("fundId") int fundId, @Path("memberId") int memberId, @Field("role") String newRole);
}
