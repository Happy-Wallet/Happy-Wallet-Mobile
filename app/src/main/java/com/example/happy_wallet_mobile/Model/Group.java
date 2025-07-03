package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
    private int id;
    private String name;
    private BigDecimal currentAmount;
    private boolean hasTarget;
    private BigDecimal targetAmount;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    private String targetEndDate;
    private String creatorEmail;
    private String creatorUsername;

    private List<GroupMember> members;

    // THÊM DÒNG NÀY: Trường để lưu màu icon (transient để không bị Gson serialize)
    private transient int iconColor;

    public Group() {
    }

    // Constructor đầy đủ
    public Group(int id, String name, BigDecimal currentAmount, boolean hasTarget,
                 BigDecimal targetAmount, String description, Date createdAt, Date updatedAt, Date deletedAt,
                 String targetEndDate, String creatorEmail, String creatorUsername,
                 List<GroupMember> members) {
        this.id = id;
        this.name = name;
        this.currentAmount = currentAmount;
        this.hasTarget = hasTarget;
        this.targetAmount = targetAmount;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.targetEndDate = targetEndDate;
        this.creatorEmail = creatorEmail;
        this.creatorUsername = creatorUsername;
        this.members = members;
        this.iconColor = 0; // Khởi tạo màu mặc định là 0 (hoặc một giá trị không hợp lệ)
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public boolean isHasTarget() { return hasTarget; }
    public void setHasTarget(boolean hasTarget) { this.hasTarget = hasTarget; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }

    public String getTargetEndDate() { return targetEndDate; }
    public void setTargetEndDate(String targetEndDate) { this.targetEndDate = targetEndDate; }

    public String getCreatorEmail() { return creatorEmail; }
    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }

    public String getCreatorUsername() { return creatorUsername; }
    public void setCreatorUsername(String creatorUsername) { this.creatorUsername = creatorUsername; }

    public List<GroupMember> getMembers() { return members; }
    public void setMembers(List<GroupMember> members) { this.members = members; }

    // THÊM GETTER VÀ SETTER CHO iconColor
    public int getIconColor() {
        return iconColor;
    }

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;
    }
}