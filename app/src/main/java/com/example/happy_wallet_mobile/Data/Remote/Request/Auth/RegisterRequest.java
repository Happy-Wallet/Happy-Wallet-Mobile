package com.example.happy_wallet_mobile.Data.Remote.Request.Auth;

public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String date_of_birth;

    public RegisterRequest(String email, String username, String password, String  date_of_birth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.date_of_birth = date_of_birth;
    }
    public String getDate_of_birth() {
        return date_of_birth;
    }
}
