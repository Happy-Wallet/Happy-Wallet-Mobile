package com.example.happy_wallet_mobile.Model;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    private int id; // ID của thông báo từ DB
    @SerializedName("user_id")
    private int userId;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("is_read")
    private boolean isRead;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    // Các trường mới cho lời mời
    @SerializedName("type") // Ví dụ: "general", "invitation"
    private String type;
    @SerializedName("fund_id") // ID của quỹ nếu đây là lời mời
    private Integer fundId; // Dùng Integer để có thể null

    public Notification(int id, int userId, String title, String description, boolean isRead,
                        String createdAt, String updatedAt, String type, Integer fundId) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.type = type;
        this.fundId = fundId;
    }

    public Notification(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = 0;
        this.userId = 0;
        this.isRead = false;
        this.createdAt = "";
        this.updatedAt = "";
        this.type = "general";
        this.fundId = null;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    // Phương thức tiện ích để kiểm tra nếu là lời mời
    public boolean isInvitation() {
        return "invitation".equalsIgnoreCase(type) && fundId != null;
    }
}