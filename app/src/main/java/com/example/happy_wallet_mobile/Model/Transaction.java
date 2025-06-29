package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private int userId;
    private int categoryId;
    private BigDecimal amount;
    private String description;
    private Date date;
    private eType type;
    private Date deletedDate;

    // Constructors
    public Transaction() {}

    public Transaction(
            int transactionId,
            int userId,
            int categoryId,
            BigDecimal amount,
            String description,
            Date date,
            eType type,
            Date deletedDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.deletedDate = deletedDate;
    }

    public Transaction(
            int userId,
            int categoryId,
            BigDecimal amount,
            String description,
            Date date,
            eType type,
            Date deletedDate) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.deletedDate = deletedDate;
    }

    // Getters
    public int getTransactionId() { return transactionId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public eType getType() { return type; }
    public Date getDeletedDate() { return deletedDate; }

    // Setters
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(Date date) { this.date = date; }
    public void setType(eType type) { this.type = type; }
    public void setDeletedDate(Date deletedDate) { this.deletedDate = deletedDate; }
}
