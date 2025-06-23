package com.example.happy_wallet_mobile.View.Adapter.UIModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Transaction;

import java.util.List;

public class DailyTransactionList {
    private String date;
    private String totalAmount;
    private List<Transaction> transactions = MockDataProvider.getMockTransactions();

    public DailyTransactionList(String date, String totalAmount, List<Transaction> transactions) {
        this.date = date;
        this.totalAmount = totalAmount;
        this.transactions = transactions;
    }

    public String getDate() {
        return date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

