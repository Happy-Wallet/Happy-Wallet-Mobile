package com.example.happy_wallet_mobile.Model;

import com.google.gson.annotations.SerializedName; // Import này nếu bạn dùng GSON

public class GroupMember {
    @SerializedName("user_id") // Ánh xạ từ "user_id" trong JSON
    private int userId;
    @SerializedName("role")
    private String role;
    @SerializedName("status")
    private String status;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;
    @SerializedName("avatar_url") // Ánh xạ từ "avatar_url" trong JSON
    private String avatarUrl;

    public GroupMember(int userId, String role, String status, String email, String username, String avatarUrl) {
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.email = email;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }

    // Setters (optional, add if needed)
    public void setUserId(int userId) { this.userId = userId; }
    public void setRole(String role) { this.role = role; }
    public void setStatus(String status) { this.status = status; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}