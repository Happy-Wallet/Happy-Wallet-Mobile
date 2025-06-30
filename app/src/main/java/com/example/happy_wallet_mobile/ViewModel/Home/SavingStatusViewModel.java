package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;

public class SavingStatusViewModel extends ViewModel {
    private final MutableLiveData<SavingGoal> _savingGoal = new MutableLiveData<>();
    private final MutableLiveData<Category> _category = new MutableLiveData<>();

    public LiveData<SavingGoal> savingGoal = _savingGoal;
    public LiveData<Category> category = _category;

    // setters
    public void setSavingGoal(SavingGoal savingGoal) {
        _savingGoal.setValue(savingGoal);
    }

    public void setCategory(Category category) {
        _category.setValue(category);
    }

}
