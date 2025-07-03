package com.example.happy_wallet_mobile.Data.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.InvitationService;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.FundDetailsResponse; // Import FundDetailsResponse
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.InvitationResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationRepository {
    private final InvitationService invitationService;

    public InvitationRepository() {
        invitationService = APIClient.getRetrofit().create(InvitationService.class);
    }

    public LiveData<List<InvitationResponse>> getPendingInvitations() {
        MutableLiveData<List<InvitationResponse>> result = new MutableLiveData<>();
        invitationService.getPendingInvitations().enqueue(new Callback<List<InvitationResponse>>() {
            @Override
            public void onResponse(Call<List<InvitationResponse>> call, Response<List<InvitationResponse>> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("InvitationRepository", "Failed to get pending invitations: " + response.message());
                    result.setValue(null); // Hoặc một danh sách rỗng để biểu thị lỗi
                }
            }

            @Override
            public void onFailure(Call<List<InvitationResponse>> call, Throwable t) {
                Log.e("InvitationRepository", "Error getting pending invitations: ", t);
                result.setValue(null); // Hoặc một danh sách rỗng
            }
        });
        return result;
    }

    public LiveData<MessageResponse> acceptInvitation(int fundId) {
        MutableLiveData<MessageResponse> result = new MutableLiveData<>();
        invitationService.acceptInvitation(fundId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("InvitationRepository", "Failed to accept invitation for fund " + fundId + ": " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e("InvitationRepository", "Error accepting invitation for fund " + fundId + ": ", t);
                result.setValue(null);
            }
        });
        return result;
    }


    public LiveData<MessageResponse> rejectInvitation(int fundId) {
        MutableLiveData<MessageResponse> result = new MutableLiveData<>();
        invitationService.rejectInvitation(fundId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("InvitationRepository", "Failed to reject invitation for fund " + fundId + ": " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e("InvitationRepository", "Error rejecting invitation for fund " + fundId + ": ", t);
                result.setValue(null);
            }
        });
        return result;
    }
    public LiveData<FundDetailsResponse> getFundDetails(int fundId) {
        MutableLiveData<FundDetailsResponse> result = new MutableLiveData<>();
        invitationService.getFundDetails(fundId).enqueue(new Callback<FundDetailsResponse>() {
            @Override
            public void onResponse(Call<FundDetailsResponse> call, Response<FundDetailsResponse> response) {
                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    Log.e("InvitationRepository", "Failed to get fund details for fund " + fundId + ": " + response.message());
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<FundDetailsResponse> call, Throwable t) {
                Log.e("InvitationRepository", "Error getting fund details for fund " + fundId + ": ", t);
                result.setValue(null);
            }
        });
        return result;
    }
}