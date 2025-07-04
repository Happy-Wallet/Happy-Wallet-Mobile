package com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions;

import com.example.happy_wallet_mobile.Model.GroupTransactionItem.Category;

import java.math.BigDecimal;

public class GroupTransactionItem implements GroupTransactionUiModel {
    private String username;
    private Category category;
    private String description;
    private BigDecimal amount;
    private String type;


    public GroupTransactionItem(String username,
                                com.example.happy_wallet_mobile.Model.GroupTransactionItem.Category category,
                                String description,
                                BigDecimal amount,
                                String type) {
        this.username = username;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getUsername() { return username; }
    public Category getCategory() { return category; }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
    public String getType() { return type; }
}
