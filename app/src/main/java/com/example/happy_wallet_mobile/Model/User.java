package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private String Id;
    private String UserName;
    private String Email;
    private Date DateOfBirth;
    private String Role;
    private Date CreatedDate;


    // constructor
    public User(
            String id,
            String userName,
            String email,
            Date dateOfBirth,
            String role,
            Date createdDate){
        Id = id;
        UserName = userName;
        Email = email;
        DateOfBirth = dateOfBirth;
        Role = role;
        CreatedDate = createdDate;
    }

    public User(
            String userName,
            String email,
            Date dateOfBirth,
            String role,
            Date createdDate){
        Id = null;
        UserName = userName;
        Email = email;
        DateOfBirth = dateOfBirth;
        Role = role;
        CreatedDate = createdDate;
    }


    // getters
    public String getId() {
        return Id;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public String getRole() {
        return Role;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }


    // setters
    public void setId(String id) {
        Id = id;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }
}
