package com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal;

public class SavingGoalResponse {
    private int goal_id;
    private int user_id;
    private int category_id;
    private String name;
    private String description;
    private double current_amount;
    private double target_amount;
    private String target_date;

    public int getGoal_id() { return goal_id; }
    public int getUser_id() { return user_id; }
    public int getCategory_id() { return category_id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getCurrent_amount() { return current_amount; }
    public double getTarget_amount() { return target_amount; }
    public String getTarget_date() { return target_date; }
}
