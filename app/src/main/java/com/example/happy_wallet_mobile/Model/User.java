package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private String Id;  // Chuyển từ int sang String
    private String UserName;
    private String Email;
    private Date DateOfBirth;
    private String Role;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;


    // constructor
    public User(){}

    public User(
            String id,
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
        DateOfBirth = dateOfBirth;
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
        Id = null;
        UserName = userName;
        Email = email;
        DateOfBirth = dateOfBirth;
        Role = role;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
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

    public Date getUpdatedDate() { return UpdatedDate; }

    public Date getDeletedDate() { return DeletedDate; }

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

    public void setUpdatedDate(Date updatedDate) { UpdatedDate = updatedDate; }

    public void setDeletedDate(Date deletedDate) { DeletedDate = deletedDate; }
}
