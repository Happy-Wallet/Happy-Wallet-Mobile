package com.example.happy_wallet_mobile.Data.Remote.Response.Category;

public class CategoryResponse {
    private int category_id;
    private Integer user_id;
    private int icon_res;
    private int color_res;
    private String type;
    private String name;
    private boolean is_default;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public int getCategory_id() { return category_id; }
    public Integer getUser_id() { return user_id; }
    public int getIcon_res() { return icon_res; }
    public int getColor_res() { return color_res; }
    public String getType() { return type; }
    public String getName() { return name; }
    public boolean isIs_default() { return is_default; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getDeleted_at() { return deleted_at; }
}
