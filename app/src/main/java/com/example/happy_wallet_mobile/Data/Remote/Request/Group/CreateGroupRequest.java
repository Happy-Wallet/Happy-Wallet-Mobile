package com.example.happy_wallet_mobile.Data.Remote.Request.Group;

import com.google.gson.annotations.SerializedName; // Thêm import này cho SerializedName

public class CreateGroupRequest {
    private String name;
    private String description;
    @SerializedName("has_target")
    private boolean hasTarget;
    @SerializedName("target_amount")
    private Double targetAmount;
    @SerializedName("target_end_date")
    private String targetEndDate;
    @SerializedName("category_id")
    private Integer categoryId;

    public CreateGroupRequest(String name, String description, boolean hasTarget, Double targetAmount, String targetEndDate, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.targetEndDate = targetEndDate;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public String getTargetEndDate() {
        return targetEndDate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}