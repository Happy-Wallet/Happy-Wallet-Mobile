package com.example.happy_wallet_mobile.Model; // Đảm bảo đúng package của bạn

public class Comment {
    private int commentId;
    private int postId; // ID của bài đăng mà bình luận thuộc về
    private int userId;
    private String commentText;
    private String createdAt; // Thời gian bình luận được tạo
    private User user; // Thông tin của người bình luận (bao gồm username và avatarUrl)

    // Constructor
    public Comment(int commentId, int postId, int userId, String commentText, String createdAt, User user) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
        this.createdAt = createdAt;
        this.user = user;
    }

    // Getters
    public int getCommentId() {
        return commentId;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
}