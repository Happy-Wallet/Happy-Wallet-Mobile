package com.example.happy_wallet_mobile.Data.Remote.Request.Category;

public class UpdateCategoryRequest {
    private String icon_res;
    private String color_res;
    private String name;

    public UpdateCategoryRequest(String icon_res, String color_res, String name) {
        this.icon_res = icon_res;
        this.color_res = color_res;
        this.name = name;
    }

    // Getters & Setters
    public String getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(String icon_res) {
        this.icon_res = icon_res;
    }

    public String getColor_res() {
        return color_res;
    }

    public void setColor_res(String color_res) {
        this.color_res = color_res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
