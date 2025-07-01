package com.example.happy_wallet_mobile.Data.Remote.Request.Category;

public class CreateCategoryRequest {
    private int user_id;
    private int icon_res;
    private int color_res;
    private String type;
    private String name;
    private boolean is_default;

    public CreateCategoryRequest(int user_id, int icon_res, int color_res, String type, String name, boolean is_default) {
        this.user_id = user_id;
        this.icon_res = icon_res;
        this.color_res = color_res;
        this.type = type;
        this.name = name;
        this.is_default = is_default;
    }

}

