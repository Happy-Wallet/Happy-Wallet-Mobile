package com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal;

import com.google.gson.annotations.SerializedName;

public class CreateSavingGoalResponse {
    private String message;

    @SerializedName("goal_id")
    private int goalId;

    public String getMessage() {
        return message;
    }

    public int getGoalId() {
        return goalId;
    }
}

