package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.InviteMemberRequest; // Import InviteMemberRequest
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.FundDetailsResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.InvitationResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body; // Import Body
import retrofit2.http.GET;
import retrofit2.http.POST; // Import POST
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InvitationService {

    @GET("invitations/pending")
    Call<List<InvitationResponse>> getPendingInvitations();

    @PUT("invitations/{fundId}/accept")
    Call<MessageResponse> acceptInvitation(@Path("fundId") int fundId);

    @PUT("invitations/{fundId}/reject")
    Call<MessageResponse> rejectInvitation(@Path("fundId") int fundId);

    @GET("funds/{fundId}")
    Call<FundDetailsResponse> getFundDetails(@Path("fundId") int fundId);

    // THÊM PHƯƠNG THỨC MỚI ĐỂ MỜI THÀNH VIÊN
    @POST("invitations/{fundId}/invite") // Endpoint từ backend
    Call<MessageResponse> inviteMember(@Path("fundId") int fundId, @Body InviteMemberRequest request);
}