package com.example.happy_wallet_mobile.Data.Remote.Request.Auth;

import com.example.happy_wallet_mobile.Model.User;

public class RegisterRequest {
    private String email;
    private String username;
    private String password;

    public RegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
