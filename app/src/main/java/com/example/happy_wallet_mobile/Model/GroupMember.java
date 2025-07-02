package com.example.happy_wallet_mobile.Model;

public class GroupMember {
    private int userId;
    private String role;
    private String status;
    private String email;
    private String username;

    public GroupMember(int userId, String role, String status, String email, String username) {
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.email = email;
        this.username = username;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }

    // Setters (optional, add if needed)
    public void setUserId(int userId) { this.userId = userId; }
    public void setRole(String role) { this.role = role; }
    public void setStatus(String status) { this.status = status; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
}