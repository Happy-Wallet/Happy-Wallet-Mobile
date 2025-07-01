package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class User {
    private int Id;
    private String Email;
    private String UserName;
    private String HashedPassword;
    private Date DateOfBirth;
    private String AvatarUrl;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;


    // constructor\
    public User(){}
    public User(
            int id,
            String email,
            String userName,
            String hashedPassword,
            Date dateOfBirth,
            String avatarUrl,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        Id = id;
        Email = email;
        UserName = userName;
        HashedPassword = hashedPassword;
        DateOfBirth = dateOfBirth;
        AvatarUrl = avatarUrl;
        CreatedDate = createdDate;
        UpdatedDate = updatedDate;
        DeletedDate = deletedDate;
    }
    public User(
            String email,
            String userName,
            String hashedPassword,
            Date dateOfBirth,
            String avatarUrl,
            Date createdDate,
            Date updatedDate,
            Date deletedDate){
        Email = email;
        UserName = userName;
        HashedPassword = hashedPassword;
        DateOfBirth = dateOfBirth;
        AvatarUrl = avatarUrl;
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

    public String getHashedPassword() {
        return HashedPassword;
    }

    public String getEmail() {
        return Email;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public String getAvatarUrl(){ return AvatarUrl; }
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

    public void setHashedPassword(String hashedPassword) {
        HashedPassword = hashedPassword;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
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
