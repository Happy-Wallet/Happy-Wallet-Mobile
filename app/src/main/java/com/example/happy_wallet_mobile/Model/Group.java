package com.example.happy_wallet_mobile.Model;

public class Group {
    private String Color, IconName, Title;

    public Group(String color, String iconName, String title){
        Color = color;
        IconName = iconName;
        Title = title;
    }

    // getters
    public String getTitle() {
        return Title;
    }
    public String getIconPath() {
        return IconName;
    }

    public String getColor() {
        return Color;
    }

    // setters
    public void setTitle(String title) {
        Title = title;
    }
    public void setIcon(String iconName) {
        IconName = iconName;
    }

    public void setColor(String color) {
        Color = color;
    }
}
