package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse;
import com.example.happy_wallet_mobile.Data.Repository.NotificationRepository;
import com.example.happy_wallet_mobile.Model.Notification;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private NotificationRepository notificationRepository;
    private MutableLiveData<List<Notification>> _notifications = new MutableLiveData<>();
    public LiveData<List<Notification>> notifications = _notifications;

    private MutableLiveData<MessageResponse> _markAsReadResult = new MutableLiveData<>();
    public LiveData<MessageResponse> markAsReadResult = _markAsReadResult;

    public NotificationViewModel() {
        notificationRepository = new NotificationRepository();
    }

    public void fetchNotifications() {
        notificationRepository.getNotifications().observeForever(notificationsList -> {
            _notifications.setValue(notificationsList);
        });
    }

    public void markNotificationAsRead(int notificationId) {
        notificationRepository.markNotificationAsRead(notificationId).observeForever(response -> {
            _markAsReadResult.setValue(response);
        });
    }
}