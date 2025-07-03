package com.example.happy_wallet_mobile.Data.Remote.Response.Invitation;

import com.google.gson.annotations.SerializedName;

public class InvitationResponse {
    @SerializedName("fund_id")
    private int fundId;

    @SerializedName("fund_name")
    private String fundName;

    @SerializedName("fund_description")
    private String fundDescription;

    // Getters and Setters

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundDescription() {
        return fundDescription;
    }

    public void setFundDescription(String fundDescription) {
        this.fundDescription = fundDescription;
    }
}