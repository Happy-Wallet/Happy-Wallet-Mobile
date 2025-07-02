package com.example.happy_wallet_mobile.ViewModel.Wallet;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.DailyTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.UserDailyTransactions.TransactionUiModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class WalletViewModel extends AndroidViewModel {
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

    public WalletViewModel(@NonNull Application application) {
        super(application);
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
                List<Transaction> transactions = convertResponsesToTransactions(responses);
                List<Category> categories = convertResponsesToCategories(responses);

                _transactionList.setValue(transactions);
                _categoryList.setValue(categories);

                _uiModels.setValue(groupTransactionsByDate(transactions, categories));
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

    private List<Category> convertResponsesToCategories(List<TransactionResponse> responses) {
        List<Category> categories = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Context context = getApplication().getApplicationContext();

        for (TransactionResponse r : responses) {
            TransactionResponse.Category c = r.getCategory();
            if (!seen.contains(c.getCategory_id())) {
                seen.add(c.getCategory_id());

                Category category = new Category();
                category.setCategoryId(c.getCategory_id());
                int iconResId = context.getResources().getIdentifier(
                        c.getIcon_res(), "drawable", context.getPackageName());
                category.setIconRes(iconResId != 0 ? iconResId : R.drawable.ic_bell);

                int colorResId = context.getResources().getIdentifier(
                        c.getColor_res(), "color", context.getPackageName());
                category.setColorRes(colorResId != 0 ? colorResId : R.color.black);

                category.setType(eType.fromString(c.getType()));
                category.setName(c.getName());
                categories.add(category);
            }
        }
        return categories;
    }


    private List<Transaction> convertResponsesToTransactions(List<TransactionResponse> responses) {
        List<Transaction> list = new ArrayList<>();
        for (TransactionResponse r : responses) {
            list.add(convertToTransaction(r));
        }
        return list;
    }


    // group transaction
    private List<TransactionUiModel> groupTransactionsByDate(
            List<Transaction> transactions,
            List<Category> categories) {

        List<TransactionUiModel> uiModels = new ArrayList<>();

        // Build map categoryId -> Category
        Map<Integer, Category> categoryMap = new HashMap<>();
        for (Category c : categories) {
            categoryMap.put(c.getCategoryId(), c);
        }

        // Group transactions by date
        Map<String, List<Transaction>> groupedMap = new TreeMap<>(Collections.reverseOrder());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        for (Transaction t : transactions) {
            String key = sdf.format(t.getDate());
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        // Build UI model
        for (Map.Entry<String, List<Transaction>> entry : groupedMap.entrySet()) {
            String date = entry.getKey();
            List<Transaction> dayTransactions = entry.getValue();

            BigDecimal total = BigDecimal.ZERO;
            for (Transaction t : dayTransactions) {
                if (t.getType() == eType.INCOME)
                    total = total.add(t.getAmount());
                else
                    total = total.subtract(t.getAmount());
            }

            uiModels.add(new DailyTransactionHeader(date, total));

            for (Transaction t : dayTransactions) {
                Category c = categoryMap.get(t.getCategoryId());
                uiModels.add(new TransactionItem(t, c));
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
