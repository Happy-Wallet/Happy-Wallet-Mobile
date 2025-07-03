package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.GroupService;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.Model.GroupMember;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageMembersViewModel extends ViewModel {

    private final MutableLiveData<List<GroupMember>> membersList = new MutableLiveData<>();
    public LiveData<List<GroupMember>> getMembersList() {
        return membersList;
    }

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    private final MutableLiveData<Group> currentManagedGroup = new MutableLiveData<>();
    public LiveData<Group> getCurrentManagedGroup() {
        return currentManagedGroup;
    }

    public void setCurrentManagedGroup(Group group) {
        currentManagedGroup.setValue(group);
        if (group != null && group.getMembers() != null) {
            membersList.postValue(group.getMembers());
        } else {
            membersList.postValue(new ArrayList<>());
        }
    }

    public void clearMessages() {
        errorMessage.postValue(null);
        successMessage.postValue(null);
    }

    private final GroupService groupService;

    public ManageMembersViewModel() {
        groupService = APIClient.getRetrofit().create(GroupService.class);
        membersList.setValue(new ArrayList<>());
    }

    // Tải lại danh sách thành viên cho quỹ hiện tại
    public void loadMembers(int fundId) {
        isLoading.setValue(true);
        groupService.getFundDetails(fundId).enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupResponse> call, @NonNull Response<GroupResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    GroupResponse groupResponse = response.body();
                    List<GroupMember> members = groupResponse.getMembers();
                    membersList.postValue(members);

                    Group currentGroupValue = currentManagedGroup.getValue();
                    if (currentGroupValue != null) {
                        currentGroupValue.setMembers(members);
                        currentManagedGroup.postValue(currentGroupValue);
                    }
                    successMessage.postValue("Tải danh sách thành viên thành công!");
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi tải thành viên: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi tải thành viên: " + t.getMessage());
            }
        });
    }

    // Gọi API xóa thành viên
    public void removeMember(int fundId, int memberId) {
        isLoading.setValue(true);
        groupService.removeMember(fundId, memberId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    successMessage.postValue(response.body().getMessage());
                    loadMembers(fundId); // Tải lại danh sách sau khi xóa
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi xóa thành viên: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi xóa thành viên: " + t.getMessage());
            }
        });
    }

    public void updateMemberRole(int fundId, int memberId, String newRole) {
        isLoading.setValue(true);
        groupService.updateMemberRole(fundId, memberId, newRole).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    successMessage.postValue(response.body().getMessage());
                    loadMembers(fundId);
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi cập nhật vai trò: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi cập nhật vai trò: " + t.getMessage());
            }
        });
    }
}