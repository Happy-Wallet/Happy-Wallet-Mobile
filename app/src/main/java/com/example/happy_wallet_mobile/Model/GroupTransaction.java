package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class GroupTransaction {
    private int GroupId;
    private int TransactionId;
    private int UserId;
    private int CategoryId;
    private BigDecimal Amount;
    private String Description;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;
    private eType Type;

    // constructor
    public GroupTransaction(){}
    public GroupTransaction(
            int transactionId,
            int groupId,
            int userId,
            int categoryId,
            BigDecimal amount,
            String description,
            Date createdDate,
            Date updatedDate,
            Date deletedDate,
            eType type

    ){
        TransactionId = transactionId;
        GroupId = groupId;
        UserId = userId;
        CategoryId = categoryId;
        Amount = amount;
        Description= description;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
        Type = type;
    }

    // getters
    public int getGroupId() {
        return GroupId;
    }

    public int getUserId() {
        return UserId;
    }

    public int getTransactionId() {
        return TransactionId;
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

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public Date getUpdatedDate() {
        return UpdatedDate;
    }

    public Date getDeletedDate() {
        return DeletedDate;
    }

    public eType getType() {
        return Type;
    }

    // setters
    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }

    public void setDescription(String description) {
        Description = description;
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

    public void setType(eType type) {
        Type = type;
    }
}
