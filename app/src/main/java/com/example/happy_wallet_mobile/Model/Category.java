package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {
    private int categoryId;
    private Integer userId;
    private int iconRes;
    private int colorRes;
    private eType type;
    private String name;
    private boolean isDefault;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructors
    public Category() {}

    public Category(
            int categoryId,
            Integer userId,
            int iconRes,
            int colorRes,
            eType type,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.categoryId = categoryId;
        this.userId = userId;
        this.iconRes = iconRes;
        this.colorRes = colorRes;
        this.type = type;
        this.name = name;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Category(
            Integer userId,
            int iconRes,
            int colorRes,
            eType type,
            String name,
            boolean isDefault,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.userId = userId;
        this.iconRes = iconRes;
        this.colorRes = colorRes;
        this.type = type;
        this.name = name;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getCategoryId() { return categoryId; }
    public Integer getUserId() { return userId; }
    public int getIconRes() { return iconRes; }
    public int getColorRes() { return colorRes; }
    public eType getType() { return type; }
    public String getName() { return name; }
    public boolean isDefault() { return isDefault; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }

    // Setters
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setIconRes(int iconRes) { this.iconRes = iconRes; }
    public void setColorRes(int colorRes) { this.colorRes = colorRes; }
    public void setType(eType type) { this.type = type; }
    public void setName(String name) { this.name = name; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
