package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.Model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    private final MutableLiveData<List<SavingGoal>> _savingGoalList = new MutableLiveData<>();
    private final MutableLiveData<List<Transaction>> _transactionList = new MutableLiveData<>();
    private final MutableLiveData<BigDecimal> _totalBaclance = new MutableLiveData<>();

    public LiveData<List<Category>> categoryList = _categoryList;
    public LiveData<List<SavingGoal>> savingGoalList = _savingGoalList;
    public LiveData<List<Transaction>> TransactionList = _transactionList;
    public LiveData<BigDecimal> TotalBalance = _totalBaclance;

    public void setData() {
        _categoryList.setValue(MockDataProvider.getMockCategories());
        _savingGoalList.setValue(MockDataProvider.getMockSavingGoals());
        _transactionList.setValue(MockDataProvider.getMockTransactions());

        List<Transaction> transactions = MockDataProvider.getMockTransactions();
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction item : transactions) {
            total = total.add(item.getAmount());
        }
        _totalBaclance.setValue(total);
    }



}
