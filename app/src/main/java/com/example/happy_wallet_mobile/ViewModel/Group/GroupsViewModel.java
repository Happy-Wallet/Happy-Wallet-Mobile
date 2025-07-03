package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.GroupService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Group.CreateGroupRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.CreateGroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.GroupResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.MessageResponse;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Group; // Đảm bảo Group class có constructor phù hợp
import com.example.happy_wallet_mobile.Model.GroupMember; // Đảm bảo GroupMember class có constructor phù hợp và @SerializedName
import com.example.happy_wallet_mobile.Model.GroupTransaction;
import com.example.happy_wallet_mobile.Model.User; // User model
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupMemberContribution;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsViewModel extends ViewModel {
    // LiveData cho Category (nếu cần cho nhóm, hiện chưa rõ mục đích)
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> getCategoryList(){
        return categoryList;
    }

    // LiveData cho danh sách các quỹ (nhóm)
    private final MutableLiveData<List<Group>> groupList = new MutableLiveData<>();
    public LiveData<List<Group>> getGroupList(){
        return groupList;
    }

    // LiveData cho danh sách thành viên của quỹ hiện tại (dùng cho chi tiết quỹ)
    private final MutableLiveData<List<GroupMember>> groupMemberList = new MutableLiveData<>();
    public LiveData<List<GroupMember>> getGroupMemberList(){
        return groupMemberList;
    }

    // LiveData cho quỹ được chọn hiện tại
    private final MutableLiveData<Group> currentGroup = new MutableLiveData<>();
    public LiveData<Group> getCurrentGroup() {
        return currentGroup;
    }
    public void setCurrentGroup(Group group){
        currentGroup.setValue(group);
        // Khi setCurrentGroup, cũng có thể cập nhật danh sách thành viên nếu Group object đã có
        if (group != null && group.getMembers() != null) {
            groupMemberList.postValue(group.getMembers());
        } else {
            groupMemberList.postValue(new ArrayList<>());
        }
    }

    // LiveData cho trạng thái tải
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // LiveData cho thông báo lỗi
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // LiveData cho thông báo thành công
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void clearErrorMessage() {
        errorMessage.postValue(null);
    }

    public void clearSuccessMessage() {
        successMessage.postValue(null);
    }

    private final GroupService groupService;
    private final Map<Integer, Integer> groupIconColorMap = new HashMap<>(); // MAP để lưu trữ màu icon theo fundId
    private final Random random = new Random(); // Random instance cho màu

    public GroupsViewModel() {
        groupService = APIClient.getRetrofit().create(GroupService.class);
        categoryList.setValue(new ArrayList<>()); // Khởi tạo nếu cần
        groupMemberList.setValue(new ArrayList<>()); // Khởi tạo
    }

    public void loadAllFunds() {
        isLoading.setValue(true);
        groupService.getAllFunds().enqueue(new Callback<List<GroupResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GroupResponse>> call, @NonNull Response<List<GroupResponse>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    List<GroupResponse> groupResponses = response.body();
                    List<Group> funds = new ArrayList<>();
                    for (GroupResponse gr : groupResponses) {
                        BigDecimal currentAmount = BigDecimal.ZERO;
                        try {
                            // Đảm bảo "currentAmount" từ API là String và có thể chuyển đổi được
                            currentAmount = new BigDecimal(gr.getCurrentAmount());
                        } catch (NumberFormatException e) {
                            e.printStackTrace(); // Log lỗi chuyển đổi số
                        }

                        // Tạo đối tượng Group từ GroupResponse
                        Group group = new Group(
                                gr.getId(),
                                gr.getName(),
                                currentAmount,
                                gr.getHasTarget(), // boolean
                                gr.getTargetAmount() != null ? BigDecimal.valueOf(gr.getTargetAmount()) : null,
                                gr.getDescription(),
                                null, // createdAt, có thể lấy từ gr nếu API trả về
                                null, // updatedAt
                                null, // deletedAt
                                gr.getTargetEndDate(),
                                gr.getCreatorEmail(),
                                gr.getCreatorUsername(),
                                gr.getCreatorAvatarUrl(), // Thêm avatarUrl của người tạo
                                gr.getMembers() // Danh sách thành viên đã được Retrofit ánh xạ tự động
                        );

                        // Gán màu icon từ map hoặc tạo mới nếu chưa có
                        if (!groupIconColorMap.containsKey(group.getId())) {
                            int newColor = android.graphics.Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                            groupIconColorMap.put(group.getId(), newColor);
                        }
                        group.setIconColor(groupIconColorMap.get(group.getId()));

                        funds.add(group);
                    }
                    groupList.setValue(funds);
                    successMessage.postValue("Tải danh sách quỹ thành công!");
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi tải danh sách quỹ: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GroupResponse>> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi tải danh sách quỹ: " + t.getMessage());
            }
        });
    }

    public void loadFundDetail(int fundId) {
        isLoading.setValue(true);
        groupService.getFundDetails(fundId).enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GroupResponse> call, @NonNull Response<GroupResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    GroupResponse gr = response.body();

                    BigDecimal currentAmount = BigDecimal.ZERO;
                    try {
                        currentAmount = new BigDecimal(gr.getCurrentAmount());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    // Tạo đối tượng Group từ GroupResponse
                    Group group = new Group(
                            gr.getId(),
                            gr.getName(),
                            currentAmount,
                            gr.getHasTarget(),
                            gr.getTargetAmount() != null ? BigDecimal.valueOf(gr.getTargetAmount()) : null,
                            gr.getDescription(),
                            null, // createdAt
                            null, // updatedAt
                            null, // deletedAt
                            gr.getTargetEndDate(),
                            gr.getCreatorEmail(),
                            gr.getCreatorUsername(),
                            gr.getCreatorAvatarUrl(), // Thêm avatarUrl của người tạo
                            gr.getMembers() // Danh sách thành viên đã được Retrofit ánh xạ tự động
                    );

                    // Gán màu icon từ map hoặc tạo mới nếu chưa có
                    if (!groupIconColorMap.containsKey(group.getId())) {
                        int newColor = android.graphics.Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                        groupIconColorMap.put(group.getId(), newColor);
                    }
                    group.setIconColor(groupIconColorMap.get(group.getId()));

                    currentGroup.postValue(group);
                    // Cập nhật LiveData cho danh sách thành viên của quỹ hiện tại
                    groupMemberList.postValue(gr.getMembers() != null ? gr.getMembers() : new ArrayList<>());

                    // Xóa dữ liệu các LiveData khác không liên quan đến chi tiết quỹ nếu không dùng
                    // groupTransactionList.postValue(new ArrayList<>());
                    // groupMemberUserList.postValue(new ArrayList<>());
                    // groupMemberContributionList.postValue(new ArrayList<>());


                    successMessage.postValue("Tải chi tiết quỹ thành công!");

                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi tải chi tiết quỹ: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GroupResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi tải chi tiết quỹ: " + t.getMessage());
            }
        });
    }

    public void createFund(CreateGroupRequest request) {
        isLoading.setValue(true);
        groupService.createFund(request).enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateGroupResponse> call, @NonNull Response<CreateGroupResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    successMessage.postValue("Quỹ đã được tạo thành công!");
                    loadAllFunds(); // Tải lại danh sách quỹ để cập nhật UI
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi tạo quỹ: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateGroupResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi tạo quỹ: " + t.getMessage());
            }
        });
    }

    public void updateFund(int fundId, CreateGroupRequest request) {
        isLoading.setValue(true);
        groupService.updateFund(fundId, request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    successMessage.postValue(response.body().getMessage());
                    loadFundDetail(fundId); // Tải lại chi tiết quỹ sau khi cập nhật
                    loadAllFunds(); // Tải lại danh sách quỹ chung
                } else {
                    String errorBody = "";
                    if (response.errorBody() != null) {
                        try {
                            errorBody = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    errorMessage.postValue("Lỗi khi cập nhật quỹ: " + response.code() + " - " + response.message() + " - " + errorBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                errorMessage.postValue("Lỗi kết nối khi cập nhật quỹ: " + t.getMessage());
            }
        });
    }

    public void deleteFund(int fundId) {
        isLoading.setValue(true);
        // Đây là nơi bạn sẽ gọi API xóa quỹ thực sự
        // Ví dụ: groupService.deleteFund(fundId).enqueue(...)
        // Sau khi xóa thành công, đặt isLoading.setValue(false) và tải lại danh sách
        isLoading.setValue(false); // Mô phỏng hoàn thành
        successMessage.postValue("Mô phỏng: Xóa quỹ thành công!");
        loadAllFunds(); // Tải lại danh sách quỹ để cập nhật UI
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Giải phóng tài nguyên nếu cần
    }

    // Các LiveData khác không sử dụng trong file này có thể được loại bỏ hoặc giữ lại
    // tùy thuộc vào nhu cầu thực tế của ứng dụng
    private final MutableLiveData<List<User>> groupMemberUserList = new MutableLiveData<>();
    public LiveData<List<User>> getGroupMemberUserList(){
        return groupMemberUserList;
    }

    private final MutableLiveData<List<GroupTransaction>> groupTransactionList = new MutableLiveData<>();
    public LiveData<List<GroupTransaction>> getGroupTransactionList(){
        return groupTransactionList;
    }

    private final MutableLiveData<List<GroupMemberContribution>> groupMemberContributionList = new MutableLiveData<>();
    public LiveData<List<GroupMemberContribution>> getGroupMemberContributionList() {
        return groupMemberContributionList;
    }
}