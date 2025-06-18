package com.example.happy_wallet_mobile.Data.Remote.Request;

public class LoginRequest {
    private String UserName;
    private String Password;

    public LoginRequest(
            String userName,
            String password) {
        UserName = userName;
        Password = password;
    }
}
