package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GroupResponse {
    @SerializedName("fund_id")
    private int id;
    private String name;
    private String description;
    @SerializedName("current_amount")
    private String currentAmount; // CHANGED: From double to String
    @SerializedName("has_target")
    private int hasTarget; // CHANGED: From boolean to int
    @SerializedName("target_amount")
    private Double targetAmount; // Nullable
    @SerializedName("target_end_date")
    private String targetEndDate; // String for date
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("creator_email")
    private String creatorEmail;
    @SerializedName("creator_username")
    private String creatorUsername;
    private List<MemberResponse> members;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCurrentAmount() { return currentAmount; }
    public int getHasTarget() { return hasTarget; }
    public Double getTargetAmount() { return targetAmount; }
    public String getTargetEndDate() { return targetEndDate; }
    public String getCategoryName() { return categoryName; }
    public String getCreatorEmail() { return creatorEmail; }
    public String getCreatorUsername() { return creatorUsername; }
    public List<MemberResponse> getMembers() { return members; }
}