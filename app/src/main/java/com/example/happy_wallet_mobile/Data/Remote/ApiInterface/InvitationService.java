package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.InviteMemberRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.InvitationResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse; // Để lấy thông tin quỹ trong lời mời

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InvitationService {

    @POST("invitations/{fundId}/invite")
    Call<MessageResponse> inviteMember(@Path("fundId") int fundId, @Body InviteMemberRequest request);

    // Chấp nhận lời mời
    @PUT("invitations/{fundId}/accept")
    Call<MessageResponse> acceptInvitation(@Path("fundId") int fundId);

    @PUT("invitations/{fundId}/reject")
    Call<MessageResponse> rejectInvitation(@Path("fundId") int fundId);

    @GET("invitations/pending")
    Call<List<InvitationResponse>> getPendingInvitations();
}