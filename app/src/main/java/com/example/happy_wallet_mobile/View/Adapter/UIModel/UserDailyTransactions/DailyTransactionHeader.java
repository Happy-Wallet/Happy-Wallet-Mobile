package com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions;

import java.math.BigDecimal;

public class DailyTransactionHeader implements TransactionUiModel {
    private String date;
    private BigDecimal totalAmount;

    public DailyTransactionHeader(String date, BigDecimal totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
