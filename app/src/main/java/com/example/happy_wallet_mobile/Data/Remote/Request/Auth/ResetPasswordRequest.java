package com.example.happy_wallet_mobile.Data.Remote.Request.Auth;

public class ResetPasswordRequest {
    private String email;
    private String otp;
    private String newPassword;

    public ResetPasswordRequest(String email, String otp, String newPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword = newPassword;
    }
}
