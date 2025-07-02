package com.example.happy_wallet_mobile.Data.Remote.Request.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class CreateTransactionRequest {
    private int category_id;
    private BigDecimal amount;
    private String description;
    private String date;

    public CreateTransactionRequest(
            int category_id,
            BigDecimal amount,
            String description,
            String date) {
        this.category_id = category_id;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Getter & Setter nếu cần
    public int getCategory_id() {
        return category_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
