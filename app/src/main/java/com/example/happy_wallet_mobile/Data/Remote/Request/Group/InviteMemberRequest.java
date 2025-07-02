package com.example.happy_wallet_mobile.Data.Remote.Request.Group;

public class InviteMemberRequest {
    private String email;

    public InviteMemberRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}