package com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal;

import com.google.gson.annotations.SerializedName;

public class SavingGoalResponse {
    @SerializedName("goal_id")
    private int goalId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("current_amount")
    private double currentAmount;

    @SerializedName("target_amount")
    private double targetAmount;

    @SerializedName("target_date")
    private String targetDate;

    // Getters
    public int getGoalId() { return goalId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCurrentAmount() { return currentAmount; }
    public double getTargetAmount() { return targetAmount; }
    public String getTargetDate() { return targetDate; }
}
