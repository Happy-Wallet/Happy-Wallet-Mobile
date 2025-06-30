package com.example.happy_wallet_mobile.View.Adapter.UIModel;

import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.Category;

public class TransactionItem implements TransactionUiModel {
    private Transaction transaction;
    private Category category;

    public TransactionItem(Transaction transaction, Category category) {
        this.transaction = transaction;
        this.category = category;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Category getCategory() {
        return category;
    }
}
