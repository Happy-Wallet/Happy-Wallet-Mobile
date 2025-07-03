package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import com.example.happy_wallet_mobile.Model.GroupMember; // Import GroupMember
import com.google.gson.annotations.SerializedName; // Import này nếu bạn dùng GSON

import java.util.List;

public class GroupResponse {
    @SerializedName("fund_id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("current_amount")
    private String currentAmount; // Đảm bảo kiểu dữ liệu phù hợp với backend
    @SerializedName("has_target")
    private boolean hasTarget;
    @SerializedName("target_amount")
    private Double targetAmount; // Đảm bảo kiểu dữ liệu phù hợp với backend
    @SerializedName("description")
    private String description;
    @SerializedName("target_end_date")
    private String targetEndDate; // Đảm bảo kiểu dữ liệu phù hợp với backend
    @SerializedName("creator_email")
    private String creatorEmail;
    @SerializedName("creator_username")
    private String creatorUsername;
    @SerializedName("creator_avatar_url") // Nếu bạn muốn hiển thị avatar của người tạo
    private String creatorAvatarUrl;

    @SerializedName("members") // Đây là phần quan trọng để Retrofit tự động ánh xạ mảng thành viên
    private List<GroupMember> members;

    // Constructors (nếu cần)
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrentAmount() { return currentAmount; }
    public boolean getHasTarget() { return hasTarget; }
    public Double getTargetAmount() { return targetAmount; }
    public String getDescription() { return description; }
    public String getTargetEndDate() { return targetEndDate; }
    public String getCreatorEmail() { return creatorEmail; }
    public String getCreatorUsername() { return creatorUsername; }
    public String getCreatorAvatarUrl() { return creatorAvatarUrl; }

    public List<GroupMember> getMembers() {
        return members;
    }

    // Setters (nếu cần)
    // public void setId(int id) { this.id = id; }
    // ... các setters khác
}