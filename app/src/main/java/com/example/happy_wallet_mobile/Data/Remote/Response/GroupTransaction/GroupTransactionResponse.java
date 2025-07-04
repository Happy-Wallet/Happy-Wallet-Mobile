package com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction;

import java.math.BigDecimal;

public class GroupTransactionResponse {
    private int fund_transaction_id;
    private int fund_id;
    private int user_id;
    private BigDecimal amount;
    private String type;
    private String description;
    private String created_at;
    private String updated_at;
    private String username;
    private String email;
    private String avatar_url;
    private Category category;

    public int getFund_transaction_id() { return fund_transaction_id; }
    public int getFund_id() { return fund_id; }
    public int getUser_id() { return user_id; }
    public BigDecimal getAmount() { return amount; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAvatar_url() { return avatar_url; }
    public Category getCategory() { return category; }

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
}
