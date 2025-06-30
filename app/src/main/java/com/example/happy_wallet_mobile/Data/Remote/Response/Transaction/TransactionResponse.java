package com.example.happy_wallet_mobile.Data.Remote.Response.Transaction;

public class TransactionResponse {
    private int id;
    private int user_id;
    private int category_id;
    private double amount;
    private String description;
    private String date;
    private String source;
    private String image_url;
    private boolean warning;

    // Getters
    public int getId() { return id; }
    public int getUser_id() { return user_id; }
    public int getCategory_id() { return category_id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getSource() { return source; }
    public String getImage_url() { return image_url; }
    public boolean isWarning() { return warning; }
}
