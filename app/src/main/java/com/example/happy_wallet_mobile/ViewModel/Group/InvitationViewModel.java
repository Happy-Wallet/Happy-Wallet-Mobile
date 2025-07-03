package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Response.Group.FundDetailsResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.InvitationResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse; // Đảm bảo import đúng MessageResponse
import com.example.happy_wallet_mobile.Data.Repository.InvitationRepository;

import java.util.List;

public class InvitationViewModel extends ViewModel {

    private InvitationRepository invitationRepository;

    private MutableLiveData<List<InvitationResponse>> _pendingInvitations = new MutableLiveData<>();
    public LiveData<List<InvitationResponse>> pendingInvitations = _pendingInvitations;

    // QUAN TRỌNG: Đảm bảo các LiveData này được trả về từ các phương thức tương ứng
    private MutableLiveData<MessageResponse> _acceptInvitationResult = new MutableLiveData<>();
    public LiveData<MessageResponse> acceptInvitationResult = _acceptInvitationResult; // Đây là LiveData mà Activity quan sát

    private MutableLiveData<MessageResponse> _rejectInvitationResult = new MutableLiveData<>();
    public LiveData<MessageResponse> rejectInvitationResult = _rejectInvitationResult; // Đây là LiveData mà Activity quan sát

    private MutableLiveData<FundDetailsResponse> _fundDetails = new MutableLiveData<>();
    public LiveData<FundDetailsResponse> fundDetails = _fundDetails;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;


    public InvitationViewModel() {
        invitationRepository = new InvitationRepository();
    }

    public void fetchPendingInvitations() {
        _isLoading.setValue(true);
        _errorMessage.setValue(null);

        // Lưu ý: observeForever có thể gây rò rỉ bộ nhớ nếu không được removeObserver()
        // Thông thường, bạn sẽ dùng observe() với LifecycleOwner trong Fragment/Activity
        // Nhưng trong ViewModel, observeForever là chấp nhận được nếu bạn quản lý cẩn thận
        invitationRepository.getPendingInvitations().observeForever(invitations -> {
            _pendingInvitations.setValue(invitations);
            _isLoading.setValue(false);

            if (invitations == null) {
                _errorMessage.setValue("Không thể tải lời mời đang chờ xử lý.");
            }
        });
    }

    // --- SỬA CÁC PHƯƠNG THỨC NÀY ---
    // Các phương thức này NÊN trả về LiveData để Activity/Fragment có thể observe trực tiếp kết quả của action cụ thể
    public LiveData<MessageResponse> acceptInvitation(int fundId) {
        // Tạo một MutableLiveData cục bộ cho mỗi lần gọi hàm, để trả về một LiveData riêng biệt cho lần gọi này.
        MutableLiveData<MessageResponse> resultForThisCall = new MutableLiveData<>();

        _isLoading.setValue(true);
        _errorMessage.setValue(null);

        invitationRepository.acceptInvitation(fundId).observeForever(response -> {
            resultForThisCall.setValue(response); // Cập nhật LiveData cục bộ
            _isLoading.setValue(false);

            if (response == null || response.getMessage() == null) {
                _errorMessage.setValue("Lỗi khi chấp nhận lời mời.");
            } else {
                fetchPendingInvitations(); // Làm mới danh sách chung
            }
            // QUAN TRỌNG: Sau khi nhận được kết quả, bạn nên gỡ bỏ observer này
            // Tuy nhiên, việc gỡ bỏ observer bên trong observeForever() hơi phức tạp
            // Cách tốt hơn là Repository trả về LiveData trực tiếp, và ViewModel chỉ truyền nó qua
        });
        return resultForThisCall; // Trả về LiveData cục bộ này
    }


    public LiveData<MessageResponse> rejectInvitation(int fundId) {
        MutableLiveData<MessageResponse> resultForThisCall = new MutableLiveData<>();

        _isLoading.setValue(true);
        _errorMessage.setValue(null);

        invitationRepository.rejectInvitation(fundId).observeForever(response -> {
            resultForThisCall.setValue(response);
            _isLoading.setValue(false);

            if (response == null || response.getMessage() == null) {
                _errorMessage.setValue("Lỗi khi từ chối lời mời.");
            } else {
                fetchPendingInvitations();
            }
        });
        return resultForThisCall;
    }
    // --- KẾT THÚC SỬA ---


    public void fetchFundDetails(int fundId) {
        _isLoading.setValue(true);
        _errorMessage.setValue(null);

        invitationRepository.getFundDetails(fundId).observeForever(details -> {
            _fundDetails.setValue(details);
            _isLoading.setValue(false);

            if (details == null) {
                _errorMessage.setValue("Không thể tải chi tiết quỹ.");
            }
        });
    }
}