package com.example.happy_wallet_mobile.Data.Remote.Request.Category;

public class UpdateCategoryRequest {
    private int icon_res;
    private int color_res;
    private String name;
    private String type;
    private boolean is_default;

    public UpdateCategoryRequest(int icon_res, int color_res, String name, String type, boolean is_default) {
        this.icon_res = icon_res;
        this.color_res = color_res;
        this.name = name;
        this.type = type;
        this.is_default = is_default;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public int getColor_res() {
        return color_res;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}