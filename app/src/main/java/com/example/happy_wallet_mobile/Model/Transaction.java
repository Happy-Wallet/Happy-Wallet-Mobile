package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int TransactionId;
    private int UserId;
    private eType Type;
    private int CategoryId;
    private BigDecimal Amount;
    private String Description;
    private Date Date;
    private Date DeletedDate;

    // Constructors
    public Transaction() {
    }

    public Transaction(
            int transactionId,
            int userId,
            eType type,
            int categoryId,
            BigDecimal amount,
            String description,
            Date date,
            Date deletedDate) {
        TransactionId = transactionId;
        UserId = userId;
        Type = type;
        CategoryId = categoryId;
        Amount = amount;
        Description = description;
        Date = date;
        DeletedDate = deletedDate;
    }

    public Transaction(
            int userId,
            eType type,
            int categoryId,
            BigDecimal amount,
            String description,
            Date date,
            Date deletedDate) {
        UserId = userId;
        Type = type;
        CategoryId = categoryId;
        Amount = amount;
        Description = description;
        Date = date;
        DeletedDate = deletedDate;
    }


    // Getters
    public int getTransactionId() {
        return TransactionId;
    }

    public int getUserId() {
        return UserId;
    }

    public eType getType() {
        return Type;
    }

    public int getCategoryId() {
        return CategoryId;
    }


    public BigDecimal getAmount() {
        return Amount;
    }

    public String getDescription() {
        return Description;
    }

    public Date getDate() {
        return Date;
    }

    public Date getDeletedDate() {
        return DeletedDate;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setType(eType type) {
        Type = type;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }


    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public void setDeletedDate(Date deletedDate) {
        DeletedDate = deletedDate;
    }
}