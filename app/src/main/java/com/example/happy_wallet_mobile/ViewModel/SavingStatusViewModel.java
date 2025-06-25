package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.SavingGoal;

public class SavingStatusViewModel extends ViewModel {
    private final MutableLiveData<SavingGoal> _savingGoal = new MutableLiveData<>();
    private final MutableLiveData<Category> _category = new MutableLiveData<>();
    private final MutableLiveData<Icon> _icon = new MutableLiveData<>();

    public LiveData<SavingGoal> savingGoal = _savingGoal;
    public LiveData<Category> category = _category;
    public LiveData<Icon> icon = _icon;

    // setters
    public void setSavingGoal(SavingGoal savingGoal) {
        _savingGoal.setValue(savingGoal);
    }

    public void setCategory(Category category) {
        _category.setValue(category);
    }

    public void setIcon(Icon icon) {
        _icon.setValue(icon);
    }
}
