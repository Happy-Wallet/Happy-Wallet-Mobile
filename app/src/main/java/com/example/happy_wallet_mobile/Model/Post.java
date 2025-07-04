package com.example.happy_wallet_mobile.Model; // Đảm bảo đúng package của bạn

import java.util.List;

public class Post {
    private int postId;
    private int userId;
    private String imageUrl; // URL của ảnh bài đăng (có thể null)
    private String caption; // Nội dung văn bản của bài đăng
    private String createdAt; // Thời gian bài đăng được tạo
    private User user; // Thông tin của người đăng bài (bao gồm username và avatarUrl)
    private List<FundActivity> activities; // Danh sách các hoạt động tài chính được đính kèm
    private List<Comment> comments; // Danh sách các bình luận trên bài đăng

    // Constructor
    public Post(int postId, int userId, String imageUrl, String caption, String createdAt, User user, List<FundActivity> activities, List<Comment> comments) {
        this.postId = postId;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.createdAt = createdAt;
        this.user = user;
        this.activities = activities;
        this.comments = comments;
    }

    // Getters
    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public List<FundActivity> getActivities() {
        return activities;
    }

    public List<Comment> getComments() {
        return comments;
    }

    // Setters (nếu cần, ví dụ khi thêm một comment mới vào post hiện có)
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}