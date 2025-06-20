package com.example.happy_wallet_mobile.Model;

public class Category {
    private String IconPath, Title;

    public Category(String _title){
        Title = _title;
        IconPath = null;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public String getIconPath() {
        return IconPath;
    }

    public String getTitle() {
        return Title;
    }
}
