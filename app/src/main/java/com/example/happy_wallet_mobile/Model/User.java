package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private String UserID;
    private String UserName;
    private String Email;
    private String Role;
    private Date CreatedDate;


    // constructor
    public User(
            String id,
            String userName,
            String email,
            String role){
        UserID = id;
        UserName = userName;
        Email = email;
        Role = role;
    }

    public User(
            String userName,
            String email,
            String role)
    {
        UserID = null;
        UserName = userName;
        Email = email;
        Role = role;
    }


    // getters
    public String getId() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }


    public String getRole() {
        return Role;
    }

    public Date getCreatedDate() {return CreatedDate;}


    // setters
    public void setId(String id) {
        UserID = id;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public void setRole(String role) {
        Role = role;
    }

    public void setCreatedDate(Date createdDate) {CreatedDate = createdDate; }
}
