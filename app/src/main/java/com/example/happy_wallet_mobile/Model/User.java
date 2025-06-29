package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private int userId;
    private String email;
    private String userName;
    private String hashedPassword;
    private String role;
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructors
    public User() {}

    public User(
            int userId,
            String email,
            String userName,
            String hashedPassword,
            String role,
            Date dateOfBirth,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public User(
            String email,
            String userName,
            String hashedPassword,
            String role,
            Date dateOfBirth,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.email = email;
        this.userName = userName;
        this.hashedPassword = hashedPassword;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getUserName() { return userName; }
    public String getHashedPassword() { return hashedPassword; }
    public String getRole() { return role; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }

    // Setters
    public void setUserId(int id) { this.userId = id; }
    public void setEmail(String email) { this.email = email; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public void setRole(String role) { this.role = role; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
