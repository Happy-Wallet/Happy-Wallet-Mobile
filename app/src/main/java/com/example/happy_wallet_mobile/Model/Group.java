package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Group {
    private int Id;
    private int CategoryId;
    private String Name;
    private BigDecimal CurrentAmount;
    private boolean HasTarget;
    private BigDecimal TargetAmount;
    private String Description;
    private Date CreatedAt;
    private Date UpdatedAt;
    private Date DeletedAt;

    // Constructors
    public Group() {
    }

    public Group(
            int id,
            int categoryId,
            String name,
            BigDecimal currentAmount,
            boolean hasTarget,
            BigDecimal targetAmount,
            String description,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        Id = id;
        CategoryId = categoryId;
        Name = name;
        CurrentAmount = currentAmount;
        HasTarget = hasTarget;
        TargetAmount = targetAmount;
        Description = description;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    public Group(
            int categoryId,
            String name,
            BigDecimal currentAmount,
            boolean hasTarget,
            BigDecimal targetAmount,
            String description,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        CategoryId = categoryId;
        Name = name;
        CurrentAmount = currentAmount;
        HasTarget = hasTarget;
        TargetAmount = targetAmount;
        Description = description;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        DeletedAt = deletedAt;
    }

    // Getters
    public int getId() {
        return Id;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public String getName() {
        return Name;
    }

    public BigDecimal getCurrentAmount() {
        return CurrentAmount;
    }

    public boolean isHasTarget() {
        return HasTarget;
    }

    public BigDecimal getTargetAmount() {
        return TargetAmount;
    }

    public String getDescription() {
        return Description;
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
    public void setId(int Id) {
        Id = Id;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        CurrentAmount = currentAmount;
    }

    public void setHasTarget(boolean hasTarget) {
        HasTarget = hasTarget;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        TargetAmount = targetAmount;
    }

    public void setDescription(String description) {
        Description = description;
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