package com.example.happy_wallet_mobile.Model; // Đảm bảo đúng package của bạn

public class FundActivity {
    private int activityId;
    private int fundId;
    private int userId;
    private Integer transactionId; // Có thể null, liên kết với giao dịch cá nhân
    private Integer fundTransactionId; // Có thể null, liên kết với giao dịch quỹ
    private String activityType; // Loại hoạt động: "contributed", "used", "created_fund", "joined_fund", "left_fund", "other"
    private Double amount; // Số tiền liên quan đến hoạt động (có thể null nếu không phải giao dịch tiền)
    private String description; // Mô tả ngắn gọn về hoạt động
    private String createdAt; // Thời gian hoạt động được tạo
    private String fundName; // Tên quỹ liên quan (từ JOIN trong query backend)
    private User user; // Thông tin người thực hiện hoạt động

    // Constructor
    public FundActivity(int activityId, int fundId, int userId, Integer transactionId, Integer fundTransactionId, String activityType, Double amount, String description, String createdAt, String fundName, User user) {
        this.activityId = activityId;
        this.fundId = fundId;
        this.userId = userId;
        this.transactionId = transactionId;
        this.fundTransactionId = fundTransactionId;
        this.activityType = activityType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.fundName = fundName;
        this.user = user;
    }

    // Getters
    public int getActivityId() {
        return activityId;
    }

    public int getFundId() {
        return fundId;
    }

    public int getUserId() {
        return userId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public Integer getFundTransactionId() {
        return fundTransactionId;
    }

    public String getActivityType() {
        return activityType;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getFundName() {
        return fundName;
    }

    public User getUser() {
        return user;
    }
}