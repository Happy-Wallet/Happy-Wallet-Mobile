package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class Category {
    private int CategoryId;
    private int UserId;
    private int IconId;
    private String ColorCode;
    private String Name;
    private boolean IsDefault;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;

    // Constructors
    public Category() {
    }

    public Category(
            int categoryId,
            Integer userId,
            int iconId,
            String colorCode,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        CategoryId = categoryId;
        UserId = userId;
        IconId = iconId;
        ColorCode = colorCode;
        Name = name;
        IsDefault = isDefault;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    public Category(
            Integer userId,
            int iconId,
            String colorCode,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        UserId = userId;
        IconId = iconId;
        ColorCode = colorCode;
        Name = name;
        IsDefault = isDefault;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    // Getters
    public int getCategoryId() {
        return CategoryId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public int getIconId() {
        return IconId;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public String getName() {
        return Name;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    // Setters
    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDefault(boolean aDefault) {
        IsDefault = aDefault;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        DeletedAt = deletedAt;
    }
}