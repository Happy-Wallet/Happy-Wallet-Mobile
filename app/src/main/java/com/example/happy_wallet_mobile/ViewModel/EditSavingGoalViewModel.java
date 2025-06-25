package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.util.List;

public class EditSavingGoalViewModel extends ViewModel {
    private final MutableLiveData<SavingGoal> _savingGoal = new MutableLiveData<>();
    private final MutableLiveData<Category> _category = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    private final MutableLiveData<List<Icon>> _iconList = new MutableLiveData<>();

    public LiveData<SavingGoal> savingGoal = _savingGoal;
    public LiveData<Category> category = _category;
    public LiveData<List<Category>> categoryList = _categoryList;
    public LiveData<List<Icon>> iconList = _iconList;

    // setters
    public void setCategory(Category category) {
        _category.setValue(category);
    }
    public void setSavingGoal(SavingGoal savingGoal) {
        _savingGoal.setValue(savingGoal);
    }

    // constructor
    public EditSavingGoalViewModel(){
        _categoryList.setValue(MockDataProvider.getMockCategories());
        _iconList.setValue(MockDataProvider.getMockIcons());
    }
}
