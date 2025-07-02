package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;

public class EditSavingGoalViewModel extends ViewModel {

    private final SavingGoalRepository savingGoalRepository = new SavingGoalRepository();

    private final MutableLiveData<SavingGoal> _savingGoal = new MutableLiveData<>();
    private final MutableLiveData<Category> _category = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _updateResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _deleteResult = new MutableLiveData<>();

    public LiveData<SavingGoal> savingGoal = _savingGoal;
    public LiveData<Category> category = _category;
    public LiveData<Boolean> updateResult = _updateResult;
    public LiveData<Boolean> deleteResult = _deleteResult;

    // Setter
    public void setSavingGoal(SavingGoal goal) {
        _savingGoal.setValue(goal);
    }

    public void setCategory(Category category) {
        _category.setValue(category);
    }

    // Gọi API bên trong ViewModel
    public void updateSavingGoal(int goalId, CreateSavingGoalRequest request) {
        String token = UserPreferences.getToken();

        savingGoalRepository.updateSavingGoal(token, goalId, request).observeForever(response -> {
            if (response != null) {
                _updateResult.postValue(true);
            } else {
                _updateResult.postValue(false);
            }
        });
    }

    public void deleteSavingGoal(int goalId) {
        String token = UserPreferences.getToken();
        savingGoalRepository.deleteSavingGoal(token, goalId).observeForever(success -> {
            _deleteResult.postValue(success != null && success);
        });
    }

    // Để reset LiveData state
    public void clearUpdateResult() {
        _updateResult.setValue(null);
    }

    public void clearDeleteResult() {
        _deleteResult.setValue(null);
    }
}
