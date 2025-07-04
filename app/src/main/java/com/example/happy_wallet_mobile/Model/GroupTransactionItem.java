package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;

public class GroupTransactionItem {
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
        private int icon_res;
        private int color_res;
        private String type;
        private String name;

        public int getCategory_id() { return category_id; }
        public int getIcon_res() { return icon_res; }
        public int getColor_res() { return color_res; }
        public String getType() { return type; }
        public String getName() { return name; }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setColor_res(int color_res) {
            this.color_res = color_res;
        }

        public void setIcon_res(int icon_res) {
            this.icon_res = icon_res;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public void setFund_transaction_id(int id) { this.fund_transaction_id = id; }
    public void setFund_id(int id) { this.fund_id = id; }
    public void setUser_id(int id) { this.user_id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String desc) { this.description = desc; }
    public void setCreated_at(String date) { this.created_at = date; }
    public void setUpdated_at(String date) { this.updated_at = date; }
    public void setUsername(String name) { this.username = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAvatar_url(String url) { this.avatar_url = url; }
    public void setCategory(Category category) { this.category = category; }

}

