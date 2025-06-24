package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SavingGoal implements Serializable {
    private int GoalId; 
    private int UserId; 
    private int CategoryId;
    private String Name; 
    private BigDecimal CurrentAmount; 
    private BigDecimal TargetAmount; 
    private String Description; 
    private Date CreatedDate; 
    private Date UpdatedDate; 
    private Date DeletedDate; 

    // Constructors
    public SavingGoal() {
    }

    public SavingGoal(
            int goalId, 
                    int userId, 
                    int categoryId,
                    String name, 
                    BigDecimal currentAmount, 
                    BigDecimal targetAmount, 
                    String description, 
                    Date createdDate,
                    Date updatedDate,
                    Date deletedDate) {
        GoalId = goalId;
        UserId = userId;
        CategoryId = categoryId;
        Name = name;
        CurrentAmount = currentAmount;
        TargetAmount = targetAmount;
        Description = description;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }

    public SavingGoal(
            int userId, 
                    int categoryId,
                    String name, 
                    BigDecimal currentAmount, 
                    BigDecimal targetAmount, 
                    String description, 
                    Date createdDate,
                    Date updatedDate,
                    Date deletedDate) {
        UserId = userId;
        CategoryId = categoryId;
        Name = name;
        CurrentAmount = currentAmount;
        TargetAmount = targetAmount;
        Description = description;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }

    // Getters
    public int getGoalId() {
        return GoalId;
    }

    public int getUserId() {
        return UserId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public String getName() {
        return Name;
    }

    public BigDecimal getCurrentAmount() {
        return CurrentAmount;
    }

    public BigDecimal getTargetAmount() {
        return TargetAmount;
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

    // Setters
    public void setGoalId(int goalId) {
        GoalId = goalId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        CurrentAmount = currentAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        TargetAmount = targetAmount;
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
}