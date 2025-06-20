package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class FundTransaction {
    private String fundId; // Chuyển từ int sang String
    private String transactionId; // Chuyển từ int sang String
    private String userId;
    private String categoryId;
    private BigDecimal amount;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    // Constructors
    public FundTransaction() {}

    public FundTransaction(
            String fundId,
            String transactionId,
            String userId,
            String categoryId,
            BigDecimal amount,
            String description,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = fundId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    public FundTransaction(
            String transactionId,
            String userId,
            String categoryId,
            BigDecimal amount,
            String description,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = null;
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
