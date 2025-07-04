package com.example.happy_wallet_mobile.Data.Remote.Request.GroupTransacion;

import java.math.BigDecimal;

public class GroupTransactionRequest {
    private BigDecimal amount;
    private String type;
    private String description;
    private Integer category_id;

    public GroupTransactionRequest(BigDecimal amount, String type, String description, Integer category_id) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.category_id = category_id;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCategory_id() { return category_id; }
    public void setCategory_id(Integer category_id) { this.category_id = category_id; }
}

