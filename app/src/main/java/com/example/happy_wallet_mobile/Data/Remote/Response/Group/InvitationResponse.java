package com.example.happy_wallet_mobile.Data.Remote.Response.Group;

import com.google.gson.annotations.SerializedName;

public class InvitationResponse {
    @SerializedName("fund_id")
    private int fundId;
    @SerializedName("fund_name")
    private String fundName;
    @SerializedName("fund_description")
    private String fundDescription;

    public int getFundId() {
        return fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public String getFundDescription() {
        return fundDescription;
    }
}