package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import java.util.List;

public class GroupResponse {
    private int group_id;
    private int category_id;
    private String name;
    private double current_amount;
    private boolean has_target;
    private double target_amount;
    private String description;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public int getGroup_id() { return group_id; }
    public int getCategory_id() { return category_id; }
    public String getName() { return name; }
    public double getCurrent_amount() { return current_amount; }
    public boolean isHas_target() { return has_target; }
    public double getTarget_amount() { return target_amount; }
    public String getDescription() { return description; }
    public String getCreated_at() { return created_at; }
    public String getUpdated_at() { return updated_at; }
    public String getDeleted_at() { return deleted_at; }
}
