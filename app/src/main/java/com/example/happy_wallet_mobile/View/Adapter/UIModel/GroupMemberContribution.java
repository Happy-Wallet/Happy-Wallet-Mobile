package com.example.happy_wallet_mobile.View.Adapter.UIModel;

import com.example.happy_wallet_mobile.Model.User;

import java.math.BigDecimal;

public class GroupMemberContribution {
    private User user;
    private String groupRole;
    private BigDecimal totalIncomeContribution;

    public GroupMemberContribution(User user, String groupRole, BigDecimal totalIncomeContribution) {
        this.user = user;
        this.groupRole = groupRole;
        this.totalIncomeContribution = totalIncomeContribution;
    }

    public User getUser() {
        return user;
    }

    public String getGroupRole() {
        return groupRole;
    }

    public BigDecimal getTotalIncomeContribution() {
        return totalIncomeContribution;
    }
}


