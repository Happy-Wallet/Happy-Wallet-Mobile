package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {
    private int CategoryId;
    private int UserId;
    private int ColorRes;
    private int IconRes;
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
            int colorRes,
            int iconRes,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        CategoryId = categoryId;
        UserId = userId;
        ColorRes = colorRes;
        IconRes = iconRes;
        Name = name;
        IsDefault = isDefault;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    public Category(
            Integer userId,
            int colorRes,
            int iconRes,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        UserId = userId;
        ColorRes = colorRes;
        IconRes = iconRes;
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

    public int getColorRes() {
        return ColorRes;
    }

    public int getIconRes() {
        return IconRes;
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

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setColorRes(int colorRes) {
        ColorRes = colorRes;
    }

    public void setIconRes(int iconRes) {
        IconRes = iconRes;
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