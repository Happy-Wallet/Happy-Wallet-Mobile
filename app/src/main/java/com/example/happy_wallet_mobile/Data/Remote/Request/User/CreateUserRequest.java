package com.example.happy_wallet_mobile.Data.Remote.Request.User;

import com.example.happy_wallet_mobile.Model.User;

import java.util.Date;

public class CreateUserRequest {
    private String email;
    private String username;
    private String role;

    public CreateUserRequest(String email, String username, String role) {
        this.email = email;
        this.username = username;
        this.role = role;
    }
}