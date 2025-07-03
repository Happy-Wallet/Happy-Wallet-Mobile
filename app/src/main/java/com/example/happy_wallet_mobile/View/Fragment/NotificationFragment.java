package com.example.happy_wallet_mobile.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log; // Import Log

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.View.Adapter.NotificationListViewAdapter;
import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.NotificationViewModel;
import com.example.happy_wallet_mobile.View.Activity.InvitationDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements NotificationListViewAdapter.OnNotificationClickListener {

    private NotificationViewModel notificationViewModel;
    private ListView lvNotification;
    private NotificationListViewAdapter adapter;
    private List<Notification> notificationList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        Button btnBack = view.findViewById(R.id.btnBack);
        lvNotification = view.findViewById(R.id.lvNotification);

        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        adapter = new NotificationListViewAdapter(getContext(), notificationList, this);
        lvNotification.setAdapter(adapter);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        // Quan sát danh sách thông báo
        notificationViewModel.notifications.observe(getViewLifecycleOwner(), notifications -> {
            if (notifications != null) {
                Log.d("NotificationFrag", "Observer triggered: Notifications received. Count: " + notifications.size());
                adapter.setNotifications(notifications);
                if (notifications.isEmpty()) {
                    Toast.makeText(getContext(), "Không có thông báo nào.", Toast.LENGTH_SHORT).show();
                    Log.d("NotificationFrag", "No notifications to display.");
                }
            } else {
                Log.e("NotificationFrag", "Observer triggered: Notifications LiveData is null. Displaying error toast.");
                Toast.makeText(getContext(), "Không thể tải thông báo.", Toast.LENGTH_SHORT).show();
            }
        });

        // Quan sát kết quả đánh dấu đã đọc
        notificationViewModel.markAsReadResult.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Log.d("NotificationFrag", "Mark as read result: " + response.getMessage());
            } else {
                Log.e("NotificationFrag", "Mark as read result is null.");
                Toast.makeText(getContext(), "Lỗi khi đánh dấu thông báo đã đọc.", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("NotificationFrag", "Fetching notifications on onCreateView.");
        notificationViewModel.fetchNotifications();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("NotificationFrag", "Fetching notifications on onResume.");
        // Luôn làm mới danh sách khi fragment được resume
        notificationViewModel.fetchNotifications();
    }

    @Override
    public void onNotificationClick(Notification notification) {
        Log.d("NotificationFrag", "Notification clicked: " + notification.getTitle() + " Type: " + notification.getType());
        if (notification.isInvitation()) {
            Intent intent = new Intent(getContext(), InvitationDetailActivity.class);
            intent.putExtra("fundId", notification.getFundId());
            // Bạn có thể truyền thêm fundName và fundDescription từ notification.getTitle() và notification.getDescription()
            intent.putExtra("fundName", notification.getTitle()); // Giả định title chứa tên quỹ
            intent.putExtra("fundDescription", notification.getDescription()); // Giả định description chứa mô tả
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Bạn đã click thông báo: " + notification.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMarkAsReadClick(int notificationId) {
        Log.d("NotificationFrag", "Marking notification as read: " + notificationId);
        notificationViewModel.markNotificationAsRead(notificationId);
    }

    @Override
    public void onAcceptInvitationClick(int fundId, int notificationId) {
        Log.d("NotificationFrag", "Accept invitation click for fundId: " + fundId + ", notificationId: " + notificationId);
        // Đây là nơi bạn sẽ gọi ViewModel của Invitation để chấp nhận lời mời
        // Vì InvitationViewModel không thuộc NotificationFragment, bạn sẽ cần:
        // 1. Lấy một thể hiện của InvitationViewModel (có thể chia sẻ ViewModel giữa các Fragment/Activity)
        // 2. Hoặc, đơn giản là điều hướng đến InvitationDetailActivity và để nó xử lý việc này
        // Hiện tại, chúng ta đã quyết định click item sẽ mở InvitationDetailActivity
        // Nếu bạn muốn xử lý trực tiếp ở đây, bạn sẽ cần inject/khởi tạo InvitationViewModel
        // Ví dụ: new ViewModelProvider(requireActivity()).get(InvitationViewModel.class).acceptInvitation(fundId);
        Toast.makeText(getContext(), "Đang xử lý chấp nhận lời mời cho quỹ: " + fundId, Toast.LENGTH_SHORT).show();
        // Sau khi chấp nhận, cũng đánh dấu thông báo là đã đọc
        onMarkAsReadClick(notificationId);
        // Và làm mới danh sách thông báo
        notificationViewModel.fetchNotifications();
    }

    @Override
    public void onRejectInvitationClick(int fundId, int notificationId) {
        Log.d("NotificationFrag", "Reject invitation click for fundId: " + fundId + ", notificationId: " + notificationId);
        // Tương tự như trên, gọi InvitationViewModel để từ chối
        Toast.makeText(getContext(), "Đang xử lý từ chối lời mời cho quỹ: " + fundId, Toast.LENGTH_SHORT).show();
        // Sau khi từ chối, cũng đánh dấu thông báo là đã đọc
        onMarkAsReadClick(notificationId);
        // Và làm mới danh sách thông báo
        notificationViewModel.fetchNotifications();
    }
}