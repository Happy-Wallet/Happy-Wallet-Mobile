package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SavingGoal implements Serializable {
    private int GoalId; 
    private int UserId; 
    private int CategoryId;
    private String Name;
    private String Description;
    private BigDecimal CurrentAmount; 
    private BigDecimal TargetAmount;
    private Date TargetDate;

    // Constructors
    public SavingGoal() {
    }

    public SavingGoal(
            int goalId, 
            int userId,
            int categoryId,
            String name,
            String description,
            BigDecimal currentAmount,
            BigDecimal targetAmount,
            Date targetDate) {
        GoalId = goalId;
        UserId = userId;
        CategoryId = categoryId;
        Name = name;
        Description = description;
        CurrentAmount = currentAmount;
        TargetAmount = targetAmount;
        TargetDate = targetDate;
    }

    public SavingGoal(
            int userId, 
            int categoryId,
            String name,
            String description,
            BigDecimal currentAmount,
            BigDecimal targetAmount,
            Date targetDate) {
        UserId = userId;
        CategoryId = categoryId;
        Name = name;
        Description = description;
        CurrentAmount = currentAmount;
        TargetAmount = targetAmount;
        TargetDate = targetDate;
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

    public String getDescription() {
        return Description;
    }

    public BigDecimal getCurrentAmount() {
        return CurrentAmount;
    }

    public BigDecimal getTargetAmount() {
        return TargetAmount;
    }

    public Date getTargetDate() {
        return TargetDate;
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

    public void setDescription(String description) {
        Description = description;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        CurrentAmount = currentAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        TargetAmount = targetAmount;
    }

    public void setTargetDate(Date targetDate) {
        TargetDate = targetDate;
    }
}