package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

public class GroupMemberResponse {
    private int group_id;
    private int user_id;
    private String role;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public int getGroup_id() { return group_id; }
    public int getUser_id() { return user_id; }
    public String getRole() { return role; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getDeleted_at() { return deleted_at; }
}