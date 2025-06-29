package com.example.happy_wallet_mobile.Data.Remote.Response.User;

public class UserResponse {
    private int user_id;
    private String email;
    private String username;
    private String hashed_password;
    private String role;
    private String date_of_birth;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public int getUser_id() { return user_id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getHashed_password() { return hashed_password; }
    public String getRole() { return role; }
    public String getDate_of_birth() { return date_of_birth; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getDeleted_at() { return deleted_at; }
}
