package com.example.happy_wallet_mobile.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List; // Thêm import này

public class Group {
    private int id; // Changed from Id to id for consistency
    private Integer categoryId; // Nullable
    private String name; // Changed from Name to name
    private BigDecimal currentAmount; // Changed from CurrentAmount to currentAmount
    private boolean hasTarget; // Changed from HasTarget to hasTarget
    private BigDecimal targetAmount; // Changed from TargetAmount to targetAmount
    private String description; // Changed from Description to description
    private Date createdAt; // Changed from CreatedAt to createdAt
    private Date updatedAt; // Changed from UpdatedAt to updatedAt
    private Date deletedAt; // Changed from DeletedAt to deletedAt

    // New fields from backend getFundDetails
    private String targetEndDate; // Added to match backend
    private String creatorEmail;
    private String creatorUsername;
    private String categoryName;
    private List<GroupMember> members; // Use a local model for members

    // Constructors (adjust as needed based on your usage)
    public Group() {
    }

    // Full constructor (consider generating with IDE for convenience)
    public Group(int id, Integer categoryId, String name, BigDecimal currentAmount, boolean hasTarget,
                 BigDecimal targetAmount, String description, Date createdAt, Date updatedAt, Date deletedAt,
                 String targetEndDate, String creatorEmail, String creatorUsername, String categoryName,
                 List<GroupMember> members) {
        this.id = id;
        this.categoryId = categoryId;
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
        this.categoryName = categoryName;
        this.members = members;
    }

    // Getters and Setters (adjust for new fields)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

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

    // New Getters and Setters
    public String getTargetEndDate() { return targetEndDate; }
    public void setTargetEndDate(String targetEndDate) { this.targetEndDate = targetEndDate; }

    public String getCreatorEmail() { return creatorEmail; }
    public void setCreatorEmail(String creatorEmail) { this.creatorEmail = creatorEmail; }

    public String getCreatorUsername() { return creatorUsername; }
    public void setCreatorUsername(String creatorUsername) { this.creatorUsername = creatorUsername; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public List<GroupMember> getMembers() { return members; }
    public void setMembers(List<GroupMember> members) { this.members = members; }
}