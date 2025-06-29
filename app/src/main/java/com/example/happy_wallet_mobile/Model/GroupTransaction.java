package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class GroupTransaction {
    private int transactionId;
    private int groupId;
    private int userId;
    private int categoryId;
    private BigDecimal amount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructors
    public GroupTransaction() {}

    public GroupTransaction(
            int transactionId,
            int groupId,
            int userId,
            int categoryId,
            BigDecimal amount,
            String description,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.transactionId = transactionId;
        this.groupId = groupId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public int getGroupId() { return groupId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }

    // Setters
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
