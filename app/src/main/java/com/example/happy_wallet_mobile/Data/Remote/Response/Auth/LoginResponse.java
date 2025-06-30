package com.example.happy_wallet_mobile.Data.Remote.Response.Auth;

import com.example.happy_wallet_mobile.Model.User;

public class LoginResponse {
    private String token;
    private int userId;
    private String username;
    private String email;

    public String getToken() { return token; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
