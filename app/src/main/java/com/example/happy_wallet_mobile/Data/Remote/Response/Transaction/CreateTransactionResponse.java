package com.example.happy_wallet_mobile.Data.Remote.Response.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class CreateTransactionResponse {
    private int transaction_id;
    private int user_id;
    private BigDecimal amount;
    private String type;
    private Date date;
    private String description;
    private Category category;

    public static class Category {
        private int category_id;
        private String icon_res;
        private String color_res;
        private String type;
        private String name;

        public int getCategory_id() { return category_id; }
        public String getIcon_res() { return icon_res; }
        public String getColor_res() { return color_res; }
        public String getType() { return type; }
        public String getName() { return name; }
    }


    public int getTransaction_id() {
        return transaction_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }
}
