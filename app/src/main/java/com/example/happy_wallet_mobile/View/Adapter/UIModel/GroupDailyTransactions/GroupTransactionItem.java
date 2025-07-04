package com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.GroupTransaction;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.User;

import java.math.BigDecimal;

public class GroupTransactionItem implements GroupTransactionUiModel {
    private User member;
    private Category category;
    private GroupTransaction transaction;

    public GroupTransactionItem(User member, Category category, GroupTransaction transaction) {
        this.member = member;
        this.category = category;
        this.transaction = transaction;
    }

    public User getMember() { return member; }
    public Category getCategory() { return category; }
    public GroupTransaction getTransaction() { return transaction; }
}

