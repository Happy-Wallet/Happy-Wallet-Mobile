package com.example.happy_wallet_mobile.Model;

public class Icon {
    private String iconId; // Chuyển từ int sang String
    private String colorId; // color_id

    // Constructors
    public Icon() {}

    public Icon(
            String iconId,
            String colorId) {
        this.iconId = iconId;
        this.colorId = colorId;
    }

    public Icon(
            String colorId) {
        this.iconId = null;
        this.colorId = colorId;
    }

    // Getters and Setters
    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
}
