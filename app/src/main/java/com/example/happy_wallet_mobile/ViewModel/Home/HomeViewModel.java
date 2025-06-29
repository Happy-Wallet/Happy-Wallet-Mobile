package com.example.happy_wallet_mobile.ViewModel.Home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.IncomeExpenseMonth;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.IncomeExpenseMonth;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;

import java.math.BigDecimal;
import java.util.List;

public class HomeViewModel extends ViewModel {
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
        categoryList.setValue(MockDataProvider.getMockCategories());
        savingGoalList.setValue(MockDataProvider.getMockSavingGoals());
        transactionList.setValue(MockDataProvider.getMockTransactions());

        List<Transaction> transactions = MockDataProvider.getMockTransactions();
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction item : transactions) {
            total = total.add(item.getAmount());
        }
        totalBalance.setValue(total);

        loadMonthlyData(transactionList.getValue());
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


}
