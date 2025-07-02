package com.example.happy_wallet_mobile.ViewModel.Home;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.TransactionRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.IncomeExpenseMonth;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.happy_wallet_mobile.Model.eType;

import java.math.BigDecimal;
import java.util.List;

public class HomeViewModel extends ViewModel {
    TransactionRepository transactionRepository = new TransactionRepository();
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    private final MutableLiveData<List<SavingGoal>> savingGoalList = new MutableLiveData<>();
    private final MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> totalBalance = new MutableLiveData<>();

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
    public LiveData<List<SavingGoal>> getSavingGoalList(){
        return savingGoalList;
    }
    public LiveData<List<Transaction>> getTransactionList(){
        return transactionList;
    }
    public LiveData<BigDecimal> getTotalBalance(){
        return totalBalance;
    }

    public void setData() {
        fetchTransactions();

        categoryList.setValue(MockDataProvider.getMockCategories());
        savingGoalList.setValue(MockDataProvider.getMockSavingGoals());
    }

    private final MutableLiveData<List<IncomeExpenseMonth>> monthlyData = new MutableLiveData<>();

    public LiveData<List<IncomeExpenseMonth>> getMonthlyData() {
        return monthlyData;
    }

    public void loadMonthlyData(List<Transaction> transactions) {
        monthlyData.setValue(getMonthlyIncomeExpense(transactions));
    }

    private List<IncomeExpenseMonth> getMonthlyIncomeExpense(List<Transaction> transactions) {
        Map<String, BigDecimal> incomeMap = new HashMap<>();
        Map<String, BigDecimal> expenseMap = new HashMap<>();

        for (Transaction t : transactions) {
            String monthYear = extractMonthYear(t.getDate());
            BigDecimal amount = t.getAmount().abs();

            if (t.getType() == eType.INCOME) {
                incomeMap.put(monthYear,
                        incomeMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
            } else if (t.getType() == eType.EXPENSE) {
                expenseMap.put(monthYear,
                        expenseMap.getOrDefault(monthYear, BigDecimal.ZERO).add(amount));
            }
        }

        Set<String> allMonths = new HashSet<>();
        allMonths.addAll(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        SimpleDateFormat sdf = new SimpleDateFormat("M/yyyy", Locale.getDefault());

        List<IncomeExpenseMonth> result = new ArrayList<>();
        for (String month : allMonths) {
            try {
                Date parsedDate = sdf.parse(month);

                result.add(new IncomeExpenseMonth(
                        parsedDate,
                        incomeMap.getOrDefault(month, BigDecimal.ZERO),
                        expenseMap.getOrDefault(month, BigDecimal.ZERO)
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // Sort theo ngày tăng dần
        result.sort(Comparator.comparing(IncomeExpenseMonth::getDate));
        return result;
    }


    private String extractMonthYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return month + "/" + year;
    }


    private Date convertToDate(String monthYear) {
        try {
            return new SimpleDateFormat("M/yyyy", Locale.getDefault()).parse(monthYear);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    public void fetchTransactions() {
        String token = UserPreferences.getToken();
        if (token == null) return;

        LiveData<List<TransactionResponse>> source = transactionRepository.getTransactions("Bearer " + token, null);

        source.observeForever(transactionResponses -> {
            if (transactionResponses != null) {
                List<Transaction> transactions = convertResponsesToTransactions(transactionResponses);
                transactionList.postValue(transactions);

                // Tính lại total balance
                if (transactions != null) {
                    BigDecimal total = BigDecimal.ZERO;
                    for (Transaction item : transactions) {
                        if (item.getType() == eType.INCOME)
                            total = total.add(item.getAmount());
                        else
                            total = total.subtract(item.getAmount());
                    }
                    totalBalance.setValue(total);


                }


                // Tính lại monthly data
                loadMonthlyData(transactions);
            }
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

    private List<Transaction> convertResponsesToTransactions(List<TransactionResponse> responses) {
        List<Transaction> list = new ArrayList<>();
        for (TransactionResponse r : responses) {
            list.add(convertToTransaction(r));
        }
        return list;
    }
}
