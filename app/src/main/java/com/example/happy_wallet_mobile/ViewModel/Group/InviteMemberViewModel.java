package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.InvitationService; // Import InvitationService
import com.example.happy_wallet_mobile.Data.Remote.Request.Group.InviteMemberRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse;
import com.example.happy_wallet_mobile.Model.Group;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteMemberViewModel extends ViewModel {

    private final MutableLiveData<String> invitationStatus = new MutableLiveData<>();
    public LiveData<String> getInvitationStatus() {
        return invitationStatus;
    }

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private final InvitationService invitationService;

    public InviteMemberViewModel() {
        invitationService = APIClient.getRetrofit().create(InvitationService.class);
    }

    // Mời thành viên thông qua API
    public void inviteMember(String email, Group currentGroup){
        if (currentGroup == null) {
            errorMessage.setValue("Lỗi: Không tìm thấy nhóm để mời thành viên.");
            return;
        }

        isLoading.setValue(true);
        InviteMemberRequest request = new InviteMemberRequest(email);
        invitationService.inviteMember(currentGroup.getId(), request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    invitationStatus.setValue(response.body().getMessage());
                } else {
                    String errorMsg = "Lỗi khi mời thành viên: " + response.message();
                    if (response.errorBody() != null) {
                        try {
                            // Cố gắng đọc thông báo lỗi từ errorBody
                            errorMsg += " - " + response.errorBody().string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.setValue(errorMsg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Lỗi kết nối khi mời thành viên: " + t.getMessage());
            }
        });
    }
}