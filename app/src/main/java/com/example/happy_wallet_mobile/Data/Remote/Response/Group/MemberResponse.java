package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import com.google.gson.annotations.SerializedName;

public class MemberResponse {
    @SerializedName("user_id")
    private int userId;
    private String role;
    private String status;
    private String email;
    private String username;

    // Getters
    public int getUserId() { return userId; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
}