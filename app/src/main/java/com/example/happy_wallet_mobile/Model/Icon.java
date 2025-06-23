package com.example.happy_wallet_mobile.Model;

import java.util.Date;

public class Icon {
    private int IconId;
    private String ColorId;
    private String IconPath;

    // Constructors
    public Icon() {
    }

    public Icon(
            int iconId,
            String colorId,
            String iconPath) {
        IconId = iconId;
        ColorId = colorId;
        IconPath = iconPath;
    }

    public Icon(
            String colorId,
            String iconPath) {
        ColorId = colorId;
        IconPath = iconPath;
    }

    // Getters
    public int getIconId() {
        return IconId;
    }

    public String getColorId() {
        return ColorId;
    }

    public String getIconPath() {
        return IconPath;
    }

    // Setters
    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public void setColorId(String colorId) {
        ColorId = colorId;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }
}