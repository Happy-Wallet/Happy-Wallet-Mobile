package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    private final MutableLiveData<List<Icon>> _iconList = new MutableLiveData<>();
    private final MutableLiveData<List<SavingGoal>> _savingGoalList = new MutableLiveData<>();

    public LiveData<List<Category>> categoryList = _categoryList;
    public LiveData<List<Icon>> iconList = _iconList;
    public LiveData<List<SavingGoal>> savingGoalList = _savingGoalList;

    // constructor
    public HomeViewModel() {
        loadMockData();
    }

    private void loadMockData() {
        _categoryList.setValue(MockDataProvider.getMockCategories());
        _iconList.setValue(MockDataProvider.getMockIcons());
        _savingGoalList.setValue(MockDataProvider.getMockSavingGoals());
    }

}
