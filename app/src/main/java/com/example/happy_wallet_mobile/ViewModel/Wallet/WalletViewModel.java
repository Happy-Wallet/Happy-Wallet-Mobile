package com.example.happy_wallet_mobile.ViewModel.Wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.TransactionRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionUiModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class WalletViewModel extends ViewModel {
    private final TransactionRepository transactionRepository = new TransactionRepository();

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

    public WalletViewModel() {
        _totalIncome.setValue(BigDecimal.ZERO);
        _totalExpenses.setValue(BigDecimal.ZERO);
        _availableBalance.setValue(BigDecimal.ZERO);

        _totalIncome.addSource(_transactionList, t -> updateTotals());
        _totalExpenses.addSource(_transactionList, t -> updateTotals());
        _availableBalance.addSource(_transactionList, t -> updateTotals());
    }

    public void fetchTransactions() {
        String token = UserPreferences.getToken();

        if (token == null) return;

        LiveData<List<TransactionResponse>> source = transactionRepository.getTransactions("Bearer " + token, null);

        _totalIncome.addSource(source, responses -> {
            if (responses != null) {
                _uiModels.setValue(groupTransactionsByDate(responses));
                List<Transaction> transactions = convertResponsesToTransactions(responses);
                _transactionList.setValue(transactions);
            }
            _totalIncome.removeSource(source);
        });
    }

    private Transaction convertToTransaction(TransactionResponse r) {
        return new Transaction(
                r.getTransaction_id(),
                r.getUser_id(),
                r.getTypeEnum(),
                r.getCategory().getCategory_id(),
                r.getAmount(),
                r.getDescription(),
                r.getDate(),
                null
        );
    }

    private Category convertToCategory(TransactionResponse.Category c) {
        Category category = new Category();
        category.setCategoryId(c.getCategory_id());
        category.setIconRes(Integer.parseInt(c.getIcon_res()));
        category.setColorRes(Integer.parseInt(c.getColor_res()));
        category.setType(eType.fromString(c.getType()));
        category.setName(c.getName());
        return category;
    }

    private List<Transaction> convertResponsesToTransactions(List<TransactionResponse> responses) {
        List<Transaction> list = new ArrayList<>();
        for (TransactionResponse r : responses) {
            list.add(convertToTransaction(r));
        }
        return list;
    }


    // group transaction
    private List<TransactionUiModel> groupTransactionsByDate(List<TransactionResponse> responses) {
        List<TransactionUiModel> uiModels = new ArrayList<>();

        Map<String, List<TransactionResponse>> groupedMap = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        for (TransactionResponse r : responses) {
            String key = sdf.format(r.getDate());
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(r);
        }

        for (Map.Entry<String, List<TransactionResponse>> entry : groupedMap.entrySet()) {
            String date = entry.getKey();
            List<TransactionResponse> dayTransactions = entry.getValue();

            BigDecimal total = BigDecimal.ZERO;
            for (TransactionResponse r : dayTransactions) {
                if (r.getTypeEnum() == eType.INCOME)
                    total = total.add(r.getAmount());
                else
                    total = total.subtract(r.getAmount());
            }

            uiModels.add(new DailyTransactionHeader(date, total));

            for (TransactionResponse r : dayTransactions) {
                uiModels.add(new TransactionItem(convertToTransaction(r), convertToCategory(r.getCategory())));
            }
        }

        return uiModels;
    }

    // update total incomde, expense, balance
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
            if (item.getType() == eType.INCOME) {
                income = income.add(item.getAmount());
            } else {
                expenses = expenses.add(item.getAmount());
            }
        }

        _totalIncome.setValue(income);
        _totalExpenses.setValue(expenses);
        _availableBalance.setValue(income.subtract(expenses));
    }
}
