package com.example.happy_wallet_mobile.Data.Remote.Response;

import com.example.happy_wallet_mobile.Model.User;

public class RegisterResponse {
    private String token;
    private User user;
    private String message;
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
