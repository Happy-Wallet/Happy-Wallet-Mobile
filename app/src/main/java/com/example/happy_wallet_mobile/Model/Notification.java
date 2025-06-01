package com.example.happy_wallet_mobile.Model;

import com.example.happy_wallet_mobile.View.Fragment.NotificationFragment;

public class Notification {
    private String Title, Description;

    // constructor
    public Notification(String title, String description){
        Title = title;
        Description = description;
    }

    // set
    public void setTitle(String title){
        Title = title;
    }
    public void setDescription(String description) {
        Description = description;
    }

    // get
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
}
