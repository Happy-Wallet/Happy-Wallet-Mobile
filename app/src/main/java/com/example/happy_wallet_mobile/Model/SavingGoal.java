package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class SavingGoal {
    private int GoalId; 
    private int UserId; 
    private int IconId; 
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
                    int iconId, 
                    String name, 
                    BigDecimal currentAmount, 
                    BigDecimal targetAmount, 
                    String description, 
                    Date CreatedDate, 
                    Date UpdatedDate, 
                    Date DeletedDate) { 
        GoalId = goalId;
        UserId = userId;
        IconId = iconId;
        Name = name;
        CurrentAmount = currentAmount;
        TargetAmount = targetAmount;
        Description = description;
        CreatedDate = CreatedDate;
        UpdatedDate = UpdatedDate;
        DeletedDate = DeletedDate;
    }

    public SavingGoal(
            int userId, 
                    int iconId, 
                    String name, 
                    BigDecimal currentAmount, 
                    BigDecimal targetAmount, 
                    String description, 
                    Date createdDate,
                    Date updatedDate,
                    Date deletedDate) {
        UserId = userId;
        IconId = iconId;
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

    public int getIconId() {
        return IconId;
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

    public void setIconId(int iconId) {
        IconId = iconId;
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