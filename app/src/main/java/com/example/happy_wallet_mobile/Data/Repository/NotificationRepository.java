package com.example.happy_wallet_mobile.Data.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.NotificationService;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse;
import com.example.happy_wallet_mobile.Model.Notification;

import java.io.IOException; // Import IOException
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationRepository {
    private final NotificationService notificationService;

    public NotificationRepository() {
        notificationService = APIClient.getRetrofit().create(NotificationService.class);
    }

    public LiveData<List<Notification>> getNotifications() {
        MutableLiveData<List<Notification>> result = new MutableLiveData<>();
        notificationService.getNotifications().enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> notifications = response.body();
                    if (notifications != null) {
                        Log.d("NotificationRepo", "API Response Code (Success): " + response.code());
                        Log.d("NotificationRepo", "Notifications received count: " + notifications.size());
                        if (!notifications.isEmpty()) {
                            Log.d("NotificationRepo", "First Notification Title: " + notifications.get(0).getTitle());
                            Log.d("NotificationRepo", "First Notification Description: " + notifications.get(0).getDescription());
                            Log.d("NotificationRepo", "First Notification Type: " + notifications.get(0).getType());
                            Log.d("NotificationRepo", "First Notification Fund ID: " + notifications.get(0).getFundId());
                            Log.d("NotificationRepo", "First Notification isRead: " + notifications.get(0).isRead());
                        }
                    } else {
                        Log.w("NotificationRepo", "Response body for notifications is null.");
                    }
                    result.setValue(notifications);
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("NotificationRepo", "Error reading error body: " + e.getMessage());
                    }
                    Log.e("NotificationRepo", "Failed to get notifications: " + response.code() + " - " + response.message() + " | Error Body: " + errorBody);
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.e("NotificationRepo", "Error getting notifications (network/connection issue): ", t);
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<MessageResponse> markNotificationAsRead(int notificationId) {
        MutableLiveData<MessageResponse> result = new MutableLiveData<>();
        notificationService.markNotificationAsRead(notificationId).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("NotificationRepo", "Mark as read success: " + response.code() + " - " + (response.body() != null ? response.body().getMessage() : "No message"));
                    result.setValue(response.body());
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("NotificationRepo", "Error reading error body for mark as read: " + e.getMessage());
                    }
                    Log.e("NotificationRepo", "Failed to mark notification as read: " + response.code() + " - " + response.message() + " | Error Body: " + errorBody);
                    result.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.e("NotificationRepo", "Error marking notification as read (network/connection issue): ", t);
                result.setValue(null);
            }
        });
        return result;
    }
}