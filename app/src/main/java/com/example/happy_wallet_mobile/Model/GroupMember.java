package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.util.Date;

public class GroupMember implements Serializable {
    private int GroupId;
    private int UserId;
    private String Role;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;

    // constructor
    public GroupMember(){}
    public GroupMember(
            int groupId,
            int userId,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        GroupId = groupId;
        UserId = userId;
        Role = role;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }
    public GroupMember(
            int userId,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        UserId = userId;
        Role = role;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }

    // getters
    public int getGroupId() {
        return GroupId;
    }

    public int getUserId() {
        return UserId;
    }

    public String getRole() {
        return Role;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public Date getUpdatedDate() {
        return UpdatedDate;
    }

    public Date getDeletedDate() {
        return DeletedDate;
    }

    // setters
    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        UpdatedDate = updatedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        DeletedDate = deletedDate;
    }
}
