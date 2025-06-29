package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Group implements Serializable {
    private int groupId;
    private int categoryId;
    private String name;
    private BigDecimal currentAmount;
    private boolean hasTarget;
    private BigDecimal targetAmount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructors
    public Group() {}

    public Group(
            int groupId,
            int categoryId,
            String name,
            BigDecimal currentAmount,
            boolean hasTarget,
            BigDecimal targetAmount,
            String description,
            Date createdAt,
            Date updatedAt,
            Date deletedAt) {
        this.groupId = groupId;
        this.categoryId = categoryId;
        this.name = name;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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
        this.categoryId = categoryId;
        this.name = name;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters
    public int getGroupId() { return groupId; }
    public int getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public boolean isHasTarget() { return hasTarget; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public String getDescription() { return description; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public Date getDeletedAt() { return deletedAt; }

    // Setters
    public void setGroupId(int groupId) { this.groupId = groupId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setName(String name) { this.name = name; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public void setHasTarget(boolean hasTarget) { this.hasTarget = hasTarget; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
}
