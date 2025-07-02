package com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal;

public class CreateSavingGoalRequest {
    private int user_id;
    private String name;
    private double current_amount;
    private double target_amount;
    private String description;
    private String start_date;
    private String target_date;
    private int category_id;

    public CreateSavingGoalRequest(int user_id, String name, double amount, double target,
                                   String description, String start_date, String end_date, int category_id) {
        this.user_id = user_id;
        this.name = name;
        this.current_amount = amount;
        this.target_amount = target;
        this.description = description;
        this.start_date = start_date;
        this.target_date = end_date;
        this.category_id = category_id;
    }

    public int getUser_id() { return user_id; }
    public String getName() { return name; }
    public double getCurrent_amount() { return current_amount; }
    public double getTarget_amount() { return target_amount; }
    public String getDescription() { return description; }
    public String getStart_date() { return start_date; }
    public String getTarget_date() { return target_date; }
    public int getCategory_id() { return category_id; }
}



