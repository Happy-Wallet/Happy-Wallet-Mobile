package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Fund {
    private String fundId; // Chuyển từ int sang String
    private String iconId; // Chuyển từ int sang String
    private String name;
    private BigDecimal currentAmount;
    private boolean hasTarget; // Đổi tên biến
    private BigDecimal targetAmount;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    // Constructors
    public Fund() {}

    public Fund(
            String fundId,
            String iconId,
            String name,
            BigDecimal currentAmount,
            boolean hasTarget,
            BigDecimal targetAmount,
            String description,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = fundId;
        this.iconId = iconId;
        this.name = name;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    public Fund(
            String iconId,
            String name,
            BigDecimal currentAmount,
            boolean hasTarget,
            BigDecimal targetAmount,
            String description,
            Date createdDate,
            Date updatedDate,
            Date deletedDate) {
        this.fundId = null;
        this.iconId = iconId;
        this.name = name;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    // Getters and Setters
    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    public void setHasTarget(boolean hasTarget) {
        this.hasTarget = hasTarget;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }
}
