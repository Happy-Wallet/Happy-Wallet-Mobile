package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class FundMember {
    private String fundId; // Chuyển từ int sang String
    private String userId; // Chuyển từ int sang String
    private String role;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    // Constructors
    public FundMember() {}

    public FundMember(
            String fundId,
            String userId,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = fundId;
        this.userId = userId;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    public FundMember(
            String userId,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = null;
        this.userId = userId;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    // Getters and Setters
    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }
}
