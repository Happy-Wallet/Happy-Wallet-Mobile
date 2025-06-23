package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionUiModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class WalletViewModel extends ViewModel {

    private final MutableLiveData<List<TransactionUiModel>> _uiModels = new MutableLiveData<>();
    public LiveData<List<TransactionUiModel>> uiModels = _uiModels;

    public void loadGroupedTransactions(List<Transaction> transactions, List<Category> categories, List<Icon> icons) {
        _uiModels.setValue(groupTransactionsByDate(transactions, categories, icons));
    }

    private List<TransactionUiModel> groupTransactionsByDate(
            List<Transaction> transactions,
            List<Category> categories,
            List<Icon> icons
    ) {
        List<TransactionUiModel> uiModels = new ArrayList<>();

        Map<String, List<Transaction>> groupedMap = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        for (Transaction t : transactions) {
            String key = sdf.format(t.getDate());
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        for (Map.Entry<String, List<Transaction>> entry : groupedMap.entrySet()) {
            String date = entry.getKey();
            List<Transaction> dayTransactions = entry.getValue();

            BigDecimal total = BigDecimal.ZERO;
            for (Transaction t : dayTransactions) {
                total = total.add(t.getAmount());
            }

            uiModels.add(new DailyTransactionHeader(date, total));

            for (Transaction t : dayTransactions) {
                Category c = findCategoryById(categories, t.getCategoryId());
                Icon i = findIconById(icons, t.getIconId());
                uiModels.add(new TransactionItem(t, c, i));
            }
        }

        return uiModels;
    }

    private Category findCategoryById(List<Category> list, int id) {
        for (Category c : list) {
            if (c.getCategoryId() == id) return c;
        }
        return null;
    }

    private Icon findIconById(List<Icon> list, int id) {
        for (Icon i : list) {
            if (i.getIconId() == id) return i;
        }
        return null;
    }
}
