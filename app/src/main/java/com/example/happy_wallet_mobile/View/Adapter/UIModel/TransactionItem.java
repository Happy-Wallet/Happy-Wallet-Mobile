package com.example.happy_wallet_mobile.View.Adapter.UIModel;

import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;

public class TransactionItem implements TransactionUiModel {
    private Transaction transaction;
    private Category category;
    private Icon icon;

    public TransactionItem(Transaction transaction, Category category, Icon icon) {
        this.transaction = transaction;
        this.category = category;
        this.icon = icon;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Category getCategory() {
        return category;
    }

    public Icon getIcon() {
        return icon;
    }
}
