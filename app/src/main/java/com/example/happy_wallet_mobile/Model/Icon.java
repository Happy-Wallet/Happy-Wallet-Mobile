package com.example.happy_wallet_mobile.Model;

import java.io.Serializable;
import java.util.Date;

public class Icon implements Serializable {
    private int IconId;
    private String IconPath;

    // Constructors
    public Icon() {
    }

    public Icon(
            int iconId,
            String iconPath) {
        IconId = iconId;
        IconPath = iconPath;
    }

    public Icon(
            String iconPath) {
        IconPath = iconPath;
    }

    // Getters
    public int getIconId() {
        return IconId;
    }

    public String getIconPath() {
        return IconPath;
    }

    // Setters
    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }
}