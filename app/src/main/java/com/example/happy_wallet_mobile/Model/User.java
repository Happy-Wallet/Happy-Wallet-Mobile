package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private int Id;
    private String UserName;
    private String Email;
    private String Role;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;


    // constructor\
    public User(){}
    public User(
            int id,
            String userName,
            String email,
            Date dateOfBirth,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        Id = id;
        UserName = userName;
        Email = email;
        Role = role;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }
    public User(
            String userName,
            String email,
            Date dateOfBirth,
            String role,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        UserName = userName;
        Email = email;
        Role = role;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }


    // getters
    public int getId() {
        return Id;
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

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public Date getDeletedDate() {
        return DeletedDate;
    }

    public Date getUpdatedDate() {
        return UpdatedDate;
    }

    // setters
    public void setId(int id) {
        Id = id;
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

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        UpdatedDate = updatedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        DeletedDate = deletedDate;
    }
}
