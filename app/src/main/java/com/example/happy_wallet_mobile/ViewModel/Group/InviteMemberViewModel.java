package com.example.happy_wallet_mobile.ViewModel.Group; // Đảm bảo đúng package của bạn

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.InvitationService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Group.InviteMemberRequest; // Giữ nguyên nếu bạn dùng request này
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse; // Dùng MessageResponse từ package Invitation
import com.example.happy_wallet_mobile.Model.Group; // Giữ nguyên nếu bạn dùng Group model cho UI

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
        // Khởi tạo InvitationService bằng APIClient
        invitationService = APIClient.getRetrofit().create(InvitationService.class);
    }

    /**
     * Mời thành viên vào một quỹ (group).
     * @param email Email của người được mời.
     * @param currentGroup Đối tượng Group hiện tại, ID của nó sẽ là fundId.
     */
    public void inviteMember(String email, Group currentGroup){
        if (currentGroup == null) {
            errorMessage.setValue("Lỗi: Không tìm thấy nhóm để mời thành viên.");
            isLoading.setValue(false); // Đảm bảo isLoading được đặt lại
            return;
        }

        isLoading.setValue(true);
        errorMessage.setValue(null); // Xóa lỗi cũ

        // Tạo request body
        // Đảm bảo InviteMemberRequest khớp với yêu cầu POST /invitations/:fundId/invite của backend (chỉ cần email)
        InviteMemberRequest request = new InviteMemberRequest(email);

        // Gọi API inviteMember từ InvitationService
        // Backend endpoint: POST /invitations/:fundId/invite
        invitationService.inviteMember(currentGroup.getId(), request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    invitationStatus.setValue(response.body().getMessage());
                    // Có thể thêm logic thành công khác ở đây nếu cần
                } else {
                    String errorMsg = "Lỗi khi mời thành viên: ";
                    if (response.code() == 404) { // Dựa vào logic backend: "Không tìm thấy người dùng với email này."
                        errorMsg += "Không tìm thấy người dùng với email này. Hãy yêu cầu họ đăng ký trước.";
                    } else if (response.code() == 409) { // Dựa vào logic backend: "Người dùng này đã được mời và lời mời đang chờ xử lý." hoặc "Người dùng này đã là thành viên của quỹ."
                        errorMsg += "Người dùng đã là thành viên hoặc lời mời đang chờ xử lý.";
                    } else {
                        errorMsg += response.message(); // Thông báo lỗi chung từ HTTP
                    }

                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            // Bạn có thể parse errorBodyString nếu backend trả về JSON với một message cụ thể
                            // Ví dụ: {"message": "Tên quỹ là bắt buộc."}
                            // Dựa trên invitationController.js, các lỗi 400, 404, 409 trả về JSON { message: '...' }
                            // Bạn có thể cần một Gson parser để lấy message từ errorBody
                            // Ví dụ đơn giản:
                            // if (errorBodyString.contains("message")) {
                            //    // Cần parse JSON để lấy message
                            //    // ex: new Gson().fromJson(errorBodyString, MessageResponse.class).getMessage();
                            // }
                            errorMsg += " - " + errorBodyString; // Thêm nội dung errorBody vào thông báo lỗi
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
                errorMessage.setValue("Lỗi kết nối khi mời thành viên: " + (t.getMessage() != null ? t.getMessage() : "Unknown error"));
            }
        });
    }
}