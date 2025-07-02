package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.util.ArrayList;
import java.util.List;

public class EditSavingGoalViewModel extends ViewModel {
    private final MutableLiveData<SavingGoal> _savingGoal = new MutableLiveData<>();
    private final MutableLiveData<Category> _category = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();

    public LiveData<SavingGoal> savingGoal = _savingGoal;
    public LiveData<Category> category = _category;
    public LiveData<List<Category>> categoryList = _categoryList;
    private final SavingGoalRepository savingGoalRepository = new SavingGoalRepository(); // Thêm repository

    private final MutableLiveData<Boolean> _updateResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _deleteResult = new MutableLiveData<>();
    public LiveData<Boolean> updateResult = _updateResult;
    public LiveData<Boolean> deleteResult = _deleteResult;

    // Setters
    public void setCategory(Category category) {
        _category.setValue(category);
    }

    public void setSavingGoal(SavingGoal savingGoal) {
        _savingGoal.setValue(savingGoal);
    }

    public void updateSavingGoal(String token, int goalId, CreateSavingGoalRequest request) {
        savingGoalRepository.updateSavingGoal(token, goalId, request).observeForever(response -> {
            _updateResult.setValue(response != null);
        });
    }

    public void deleteSavingGoal(String token, int goalId) {
        savingGoalRepository.deleteSavingGoal(token, goalId).observeForever(success -> {
            _deleteResult.setValue(success);
        });
    }
}
