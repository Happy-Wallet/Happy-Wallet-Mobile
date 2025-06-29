package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

public class GroupTransactionResponse {
    private int transaction_id;
    private int group_id;
    private int user_id;
    private int category_id;
    private double amount;
    private String description;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public int getTransaction_id() { return transaction_id; }
    public int getGroup_id() { return group_id; }
    public int getUser_id() { return user_id; }
    public int getCategory_id() { return category_id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getDeleted_at() { return deleted_at; }
}
