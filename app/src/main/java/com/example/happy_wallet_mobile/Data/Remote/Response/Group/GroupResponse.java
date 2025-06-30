package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import java.util.List;

public class GroupResponse {
    private int id;
    private String name;
    private double amount;
    private String description;
    private String created_at;
    private String updated_at;
    private List<Integer> members;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public List<Integer> getMembers() { return members; }
}
