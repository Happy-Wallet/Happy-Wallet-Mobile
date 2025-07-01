package com.example.happy_wallet_mobile.Data.Remote.Request.Category;

public class CreateCategoryRequest {
    private int user_id;
    private String name;
    private boolean is_default;

    public CreateCategoryRequest(int user_id, String name, boolean is_default) {
        this.user_id = user_id;
        this.name = name;
        this.is_default = is_default;
    }
}
