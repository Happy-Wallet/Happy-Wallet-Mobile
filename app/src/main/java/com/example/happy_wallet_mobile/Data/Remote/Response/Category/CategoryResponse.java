package com.example.happy_wallet_mobile.Data.Remote.Response.Category;

public class CategoryResponse {
    private int category_id;
    private Integer user_id; // Có thể null
    private String icon_res;
    private String color_res;
    private String type;
    private String name;

    // Getters & Setters
    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
