package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeoutException;

public class Transaction {
    private int TransactionId;
    private int UserId;
    private int CategoryId;
    private int IconId;
    private String Title;
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
            int categoryId,
            int iconId,
            String title,
            BigDecimal amount,
            String description,
            Date date,
            Date deletedDate) {
        TransactionId = transactionId;
        UserId = userId;
        CategoryId = categoryId;
        IconId = iconId;
        Title = title;
        Amount = amount;
        Description = description;
        Date = date;
        DeletedDate = deletedDate;
    }

    public Transaction(
            int userId,
            int categoryId,
            int iconId,
            String title,
            BigDecimal amount,
            String description,
            Date date,
            Date deletedDate) {
        UserId = userId;
        CategoryId = categoryId;
        IconId = iconId;
        Title = title;
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

    public int getCategoryId() {
        return CategoryId;
    }

    public int getIconId() {
        return IconId;
    }

    public String getTitle() {
        return Title;
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

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public void setTitle(String title) {
        Title = title;
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