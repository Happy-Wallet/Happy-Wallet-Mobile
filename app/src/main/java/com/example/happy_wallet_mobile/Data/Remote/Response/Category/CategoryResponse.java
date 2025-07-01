package com.example.happy_wallet_mobile.Data.Remote.Response.Category;

public class CategoryResponse {
    private int id;
    private int user_id;
    private String name;
    private boolean is_default;
    private String created_at;
    private String updated_at;

    // Getters
    public int getId() { return id; }
    public int getUser_id() { return user_id; }
    public String getName() { return name; }
    public boolean isIs_default() { return is_default; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
}
