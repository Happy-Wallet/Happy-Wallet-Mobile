package com.example.happy_wallet_mobile.Data.Remote.Response.Category;

public class CreateCategoryResponse {
    private String message;
    private int category_id;
    private int user_id;
    private String icon_res;
    private String color_res;
    private String type;
    private String name;

    // Getters & Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
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
