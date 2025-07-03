package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;

import java.util.List;

public class SavingGoalListViewModel extends ViewModel {

    private final SavingGoalRepository repository = new SavingGoalRepository();
    private final MutableLiveData<List<SavingGoalResponse>> _savingGoals = new MutableLiveData<>();
    public LiveData<List<SavingGoalResponse>> savingGoals = _savingGoals;

    public void loadSavingGoals(String token) {
        repository.getAllSavingGoals(token).observeForever(response -> {
            _savingGoals.setValue(response);
        });
    }
}
