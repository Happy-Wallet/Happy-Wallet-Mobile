package com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal;

public class SavingGoalResponse {
    private int id;
    private int user_id;
    private String name;
    private double amount;
    private double target;
    private String description;
    private String start_date;
    private String end_date;
    private String created_at;
    private String updated_at;

    // Getters
    public int getId() { return id; }
    public int getUser_id() { return user_id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public double getTarget() { return target; }
    public String getDescription() { return description; }
    public String getStart_date() { return start_date; }
    public String getEnd_date() { return end_date; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
}
