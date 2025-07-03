package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private int CategoryId;
    private Integer UserId;
    private Integer ColorRes;
    private Integer IconRes;
    private eType Type;
    private String Name;
    private boolean IsDefault;

    // Constructors
    public Category() {
    }

    public Category(
            Integer categoryId,
            Integer userId,
            Integer colorRes,
            Integer iconRes,
            eType type,
            String name,
            boolean isDefault
    ) {
        this.CategoryId = categoryId != null ? categoryId : -1;
        this.UserId = userId;
        this.ColorRes = colorRes;
        this.IconRes = iconRes;
        this.Type = type;
        this.Name = name;
        this.IsDefault = isDefault;
    }

    // Getters
    public int getCategoryId() {
        return CategoryId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public Integer getColorRes() {
        return ColorRes;
    }

    public Integer getIconRes() {
        return IconRes;
    }

    public eType getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    // Setters
    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setColorRes(Integer colorRes) {
        ColorRes = colorRes;
    }

    public void setIconRes(Integer iconRes) {
        IconRes = iconRes;
    }

    public void setType(eType type) {
        Type = type;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
    }
}
