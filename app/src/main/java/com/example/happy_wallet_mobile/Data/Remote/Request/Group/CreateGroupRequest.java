package com.example.happy_wallet_mobile.Data.Remote.Request.Group;

import java.util.List;

public class CreateGroupRequest {
    private String name;
    private double amount;
    private List<Integer> members;
    private String description;

    public CreateGroupRequest(String name, double amount, List<Integer> members, String description) {
        this.name = name;
        this.amount = amount;
        this.members = members;
        this.description = description;
    }
}
