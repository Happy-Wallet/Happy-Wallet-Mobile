package com.example.happy_wallet_mobile.Model;

import com.google.gson.annotations.SerializedName; // <<< THÊM DÒNG NÀY
import java.io.Serializable; // Nên thêm Serializable
import java.util.Date;

public class User implements Serializable { // Nên implements Serializable
    // Tên trường trong JSON: "userId"
    @SerializedName("userId")
    private int Id;

    // Tên trường trong JSON: "email"
    @SerializedName("email")
    private String Email;

    // Tên trường trong JSON: "username"
    @SerializedName("username")
    private String UserName;

    // Tên trường trong JSON: "avatarUrl"
    @SerializedName("avatarUrl")
    private String AvatarUrl;

    // Các trường khác có thể không có trong JSON phản hồi,
    // nếu không có, Gson sẽ để mặc định hoặc null.
    private String HashedPassword;
    private Date DateOfBirth;
    private Date CreatedDate;
    private Date UpdatedDate;
    private Date DeletedDate;


    // constructor
    public User(){}

    // Giữ các constructor hiện có hoặc tạo các constructor phù hợp với nhu cầu của bạn
    // Ví dụ, constructor này hữu ích khi chỉ cần các thông tin cơ bản của user
    public User(int id, String email, String userName, String avatarUrl) {
        this.Id = id;
        this.Email = email;
        this.UserName = userName;
        this.AvatarUrl = avatarUrl;
    }

    // Constructor bổ sung từ đề xuất trước đó (cũng hữu ích)
    public User(int id, String userName, String avatarUrl) {
        this.Id = id;
        this.UserName = userName;
        this.AvatarUrl = avatarUrl;
    }

    public User(int userId, String email, String username, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
    }

    // Các getters và setters (giữ nguyên, không cần thay đổi)
    public int getId() { return Id; }
    public String getUserName() { return UserName; }
    public String getHashedPassword() { return HashedPassword; }
    public String getEmail() { return Email; }
    public Date getDateOfBirth() { return DateOfBirth; }
    public String getAvatarUrl(){ return AvatarUrl; }
    public Date getCreatedDate() { return CreatedDate; }
    public Date getDeletedDate() { return DeletedDate; }
    public Date getUpdatedDate() { return UpdatedDate; }

    public void setId(int id) { Id = id; }
    public void setUserName(String userName) { UserName = userName; }
    public void setHashedPassword(String hashedPassword) { HashedPassword = hashedPassword; }
    public void setEmail(String email) { Email = email; }
    public void setDateOfBirth(Date dateOfBirth) { DateOfBirth = dateOfBirth; }
    public void setAvatarUrl(String avatarUrl) { AvatarUrl = avatarUrl; }
    public void setCreatedDate(Date createdDate) { CreatedDate = createdDate; }
    public void setUpdatedDate(Date updatedDate) { UpdatedDate = updatedDate; }
    public void setDeletedDate(Date deletedDate) { DeletedDate = deletedDate; }
}