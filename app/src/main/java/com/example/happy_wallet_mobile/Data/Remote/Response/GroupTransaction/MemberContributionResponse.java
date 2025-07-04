package com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction;

import java.math.BigDecimal;

public class MemberContributionResponse {
    private int user_id;
    private String username;
    private String email;
    private String avatar_url;
    private BigDecimal total_contribution;

    public int getUser_id() { return user_id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAvatar_url() { return avatar_url; }
    public BigDecimal getTotal_contribution() { return total_contribution; }
}

