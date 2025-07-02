package com.example.happy_wallet_mobile.ViewModel.Home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Data.Repository.TransactionRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;
import com.example.happy_wallet_mobile.View.Utilities.ResourceUtility;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HomeViewModel extends ViewModel {

    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository(UserPreferences.getToken());

    private final MutableLiveData<List<SavingGoal>> savingGoalList = new MutableLiveData<>();
    private final MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> totalBalance = new MutableLiveData<>();
    private final MutableLiveData<List<IncomeExpenseMonth>> monthlyData = new MutableLiveData<>();

    // --- Public getters ---
    public LiveData<List<SavingGoal>> getSavingGoalList() { return savingGoalList; }
    public LiveData<List<Transaction>> getTransactionList() { return transactionList; }
    public LiveData<BigDecimal> getTotalBalance() { return totalBalance; }
    public LiveData<List<IncomeExpenseMonth>> getMonthlyData() { return monthlyData; }

    // --- Data loading ---
    public void setData(Context context) {
        fetchTransactions();
        savingGoalList.setValue(MockDataProvider.getMockSavingGoals());
    }

    public void fetchTransactions() {
        String token = UserPreferences.getToken();
        if (token == null) return;

        LiveData<List<TransactionResponse>> source = transactionRepository.getTransactions("Bearer " + token, null);
        source.observeForever(transactionResponses -> {
            if (transactionResponses != null) {
                List<Transaction> transactions = convertResponsesToTransactions(transactionResponses);
                transactionList.postValue(transactions);

                // Tính balance
                BigDecimal total = BigDecimal.ZERO;
                for (Transaction t : transactions) {
                    if (t.getType() == eType.INCOME)
                        total = total.add(t.getAmount());
                    else
                        total = total.subtract(t.getAmount());
                }
                totalBalance.setValue(total);

                // Tính monthly
                loadMonthlyData(transactions);
            }
        });
    }

    public void loadMonthlyData(List<Transaction> transactions) {
        monthlyData.setValue(getMonthlyIncomeExpense(transactions));
    }

    // --- Helpers ---
    private List<Transaction> convertResponsesToTransactions(List<TransactionResponse> responses) {
        List<Transaction> list = new ArrayList<>();
        for (TransactionResponse r : responses) {
            list.add(new Transaction(
                    r.getTransaction_id(),
                    r.getUser_id(),
                    r.getTypeEnum(),
                    r.getCategory().getCategory_id(),
                    r.getAmount(),
                    r.getDescription(),
                    r.getDate(),
                    null
            ));
        }
        return list;
    }


    private List<IncomeExpenseMonth> getMonthlyIncomeExpense(List<Transaction> transactions) {
        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();

        for (Transaction t : transactions) {
            String monthYear = extractMonthYear(t.getDate());
            BigDecimal amount = t.getAmount().abs();

            if (t.getType() == eType.INCOME) {
                incomeMap.put(monthYear, incomeMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
            } else {
                expenseMap.put(monthYear, expenseMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
            }
        }

        Set<String> allMonths = new HashSet<>();
        allMonths.addAll(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        List<IncomeExpenseMonth> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());
        for (String month : allMonths) {
            try {
                Date parsed = sdf.parse(month);
                result.add(new IncomeExpenseMonth(parsed,
                        incomeMap.getOrDefault(month, BigDecimal.ZERO),
                        expenseMap.getOrDefault(month, BigDecimal.ZERO)
                ));
            } catch (ParseException ignored) {}
        }

        result.sort(Comparator.comparing(IncomeExpenseMonth::getDate));
        return result;
    }

    private String extractMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }
}
