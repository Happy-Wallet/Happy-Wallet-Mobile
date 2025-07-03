package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SavingGoal implements Serializable {
    private int goalId;
    private int userId;
    private int categoryId;
    private String name;
    private String description;
    private BigDecimal currentAmount;
    private BigDecimal targetAmount;
    private Date targetDate;

    // Constructors
    public SavingGoal() { }

    public SavingGoal(int goalId, int userId, int categoryId, String name, String description,
                      BigDecimal currentAmount, BigDecimal targetAmount, Date targetDate) {
        this.goalId = goalId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
    }

    // Getters
    public int getGoalId() { return goalId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public Date getTargetDate() { return targetDate; }

    // Setters
    public void setGoalId(int goalId) { this.goalId = goalId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setTargetDate(Date targetDate) { this.targetDate = targetDate; }

    // Static helper
    public static Date parseIsoDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
