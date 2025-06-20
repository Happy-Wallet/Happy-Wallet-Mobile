package com.example.happy_wallet_mobile.Model;

public class SavingGoal {
    private String Color, IconName, Title, Target, CurrentMoney;

    public SavingGoal(String color, String iconName, String title, String target, String currentMoney){
        Color = color;
        IconName = iconName;
        Title = title;
        Target = target;
        CurrentMoney = currentMoney;
    }

    // getters
    public String getTitle() {
        return Title;
    }
    public String getIconPath() {
        return IconName;
    }
    public String getTarget() {
        return Target;
    }
    public String getCurrentMoney() {
        return CurrentMoney;
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
    public void setTarget(String target) {
        Target = target;
    }
    public void setCurrentMoney(String currentMoney) {
        CurrentMoney = currentMoney;
    }
    public void setColor(String color) {
        Color = color;
    }
}
