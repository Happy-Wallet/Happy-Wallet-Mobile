package com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions;

import java.math.BigDecimal;

public class GroupTransactionHeader implements GroupTransactionUiModel {
    private String date;
    private BigDecimal totalAmount;

    public GroupTransactionHeader(String date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public String getDate() { return date; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
