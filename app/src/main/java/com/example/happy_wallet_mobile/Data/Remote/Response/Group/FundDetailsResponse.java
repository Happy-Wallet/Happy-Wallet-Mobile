package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import com.google.gson.annotations.SerializedName;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.FundDetailsResponse;
// Dựa trên cấu trúc phản hồi từ exports.getFundDetails trong fundController.js
public class FundDetailsResponse {
    @SerializedName("fund_id")
    private int fundId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("current_amount")
    private double currentAmount;
    @SerializedName("has_target")
    private boolean hasTarget;
    @SerializedName("target_amount")
    private Double targetAmount; // Dùng Double để có thể null
    @SerializedName("target_end_date")
    private String targetEndDate; // Hoặc kiểu Date nếu bạn parse

    // Thêm các trường khác nếu có trong response của bạn, ví dụ: creator_email, creator_username, members

    // Constructors (nếu cần, hoặc Gson sẽ tự động tạo)
    public FundDetailsResponse(int fundId, String name, String description, double currentAmount,
                               boolean hasTarget, Double targetAmount, String targetEndDate) {
        this.fundId = fundId;
        this.name = name;
        this.description = description;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.targetEndDate = targetEndDate;
    }

    // Getters
    public int getFundId() {
        return fundId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public boolean getHasTarget() {
        return hasTarget;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public String getTargetEndDate() {
        return targetEndDate;
    }
}