package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.util.Date;

public class GroupMember implements Serializable {
    private int groupId;
    private int userId;
    private String role;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructors
    public GroupMember() {}

    public GroupMember(
            int groupId,
            int userId,
            String role,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getGroupId() { return groupId; }
    public int getUserId() { return userId; }
    public String getRole() { return role; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }

    // Setters
    public void setGroupId(int groupId) { this.groupId = groupId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setRole(String role) { this.role = role; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
