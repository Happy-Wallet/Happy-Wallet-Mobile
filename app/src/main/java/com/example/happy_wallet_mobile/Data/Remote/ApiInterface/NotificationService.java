package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Model.Notification; // Model Notification mới
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse; // Dùng chung cho accept/reject

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationService {

    @GET("notifications") // Endpoint để lấy tất cả thông báo của user
    Call<List<Notification>> getNotifications();

    @PUT("notifications/{notificationId}/read") // Endpoint để đánh dấu đã đọc
    Call<MessageResponse> markNotificationAsRead(@Path("notificationId") int notificationId);
}