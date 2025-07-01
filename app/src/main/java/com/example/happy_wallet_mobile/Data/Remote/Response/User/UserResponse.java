package com.example.happy_wallet_mobile.Data.Remote.Response.User;

public class UserResponse {
    private int id;
    private String email;
    private String username;
    private String avatar_url;
    private String date_of_birth;
    private String created_at;
    private String updated_at;

    // Getters
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatar_url; }
    public String getDateOfBirth() { return date_of_birth; }
    public String getCreatedAt() { return created_at; }
    public String getUpdatedAt() { return updated_at; }
}
