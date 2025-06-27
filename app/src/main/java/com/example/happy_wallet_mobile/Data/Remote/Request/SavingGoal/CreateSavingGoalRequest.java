package com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal;

public class CreateSavingGoalRequest {
    private int user_id;
    private String name;
    private double amount;
    private double target;
    private String description;
    private String start_date;
    private String end_date;

    public CreateSavingGoalRequest(int user_id, String name, double amount, double target,
                                   String description, String start_date, String end_date) {
        this.user_id = user_id;
        this.name = name;
        this.amount = amount;
        this.target = target;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

}
