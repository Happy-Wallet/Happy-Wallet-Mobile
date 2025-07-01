package com.example.happy_wallet_mobile.Data.Remote.Request.Auth;

public class ResetPasswordRequest {
    private String token;
    private String newPassword;

    public ResetPasswordRequest(String token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }
}
