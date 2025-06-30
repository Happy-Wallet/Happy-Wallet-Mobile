package com.example.happy_wallet_mobile.ViewModel.Wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
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

    private final MutableLiveData<List<Transaction>> _transactionList = new MutableLiveData<>();
    public LiveData<List<Transaction>> transactionList = _transactionList;

    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> categoryList = _categoryList;


    private final MediatorLiveData<BigDecimal> _totalIncome = new MediatorLiveData<>();
    public LiveData<BigDecimal> totalIncome = _totalIncome;

    private final MediatorLiveData<BigDecimal> _totalExpenses = new MediatorLiveData<>();
    public LiveData<BigDecimal> totalExpenses = _totalExpenses;

    private final MediatorLiveData<BigDecimal> _availableBalance = new MediatorLiveData<>();
    public LiveData<BigDecimal> availableBalance = _availableBalance;


    public void getData() {
        List<Transaction> transactions = MockDataProvider.getMockTransactions();
        List<Category> categories = MockDataProvider.getMockCategories();

        _transactionList.setValue(transactions);
        _categoryList.setValue(categories);

        _uiModels.setValue(groupTransactionsByDate());
        updateTotals();
    }

    public WalletViewModel() {
        _totalIncome.setValue(BigDecimal.ZERO);
        _totalExpenses.setValue(BigDecimal.ZERO);
        _availableBalance.setValue(BigDecimal.ZERO);

        _totalIncome.addSource(_transactionList, t -> updateTotals());
        _totalExpenses.addSource(_transactionList, t -> updateTotals());
        _availableBalance.addSource(_transactionList, t -> updateTotals());
    }


    public void loadGroupedTransactions() {
        _uiModels.setValue(groupTransactionsByDate());
    }

    private List<TransactionUiModel> groupTransactionsByDate() {
        List<TransactionUiModel> uiModels = new ArrayList<>();

        List<Transaction> transactions = _transactionList.getValue();
        List<Category> categories = _categoryList.getValue();

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
                uiModels.add(new TransactionItem(t, c));
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

    private void updateTotals() {
        List<Transaction> transactions = _transactionList.getValue();
        if (transactions == null) {
            _totalIncome.setValue(BigDecimal.ZERO);
            _totalExpenses.setValue(BigDecimal.ZERO);
            _availableBalance.setValue(BigDecimal.ZERO);
            return;
        }

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expenses = BigDecimal.ZERO;

        for (Transaction item : transactions) {
            if (item.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                income = income.add(item.getAmount());
            } else if (item.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                expenses = expenses.add(item.getAmount());
            }
        }

        _totalIncome.setValue(income);
        _totalExpenses.setValue(expenses);
        _availableBalance.setValue(income.add(expenses));
    }


}
