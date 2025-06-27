package com.example.happy_wallet_mobile.Data.Remote.Request.Transaction;

public class CreateTransactionRequest {
    private int user_id;
    private int category_id;
    private double amount;
    private String description;
    private String date;
    private String source;
    private String image_url;
    private boolean warning;

    public CreateTransactionRequest(
            int user_id,
            int category_id,
            double amount,
            String description,
            String date,
            String source,
            String image_url,
            boolean warning) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.source = source;
        this.image_url = image_url;
        this.warning = warning;
    }

}
