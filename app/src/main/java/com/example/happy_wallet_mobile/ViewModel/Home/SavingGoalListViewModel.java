package com.example.happy_wallet_mobile.ViewModel.Home;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavingGoalListViewModel extends ViewModel {

    private final SavingGoalRepository repository = new SavingGoalRepository();
    private final MutableLiveData<List<SavingGoal>> _savingGoals = new MutableLiveData<>();
    public LiveData<List<SavingGoal>> savingGoals = _savingGoals;


    private final MutableLiveData<Pair<SavingGoal, Category>> _selectedSavingGoal = new MutableLiveData<>();
    public LiveData<Pair<SavingGoal, Category>> selectedSavingGoal = _selectedSavingGoal;

    public void fetchSavingGoals() {
        String token = UserPreferences.getToken();
        repository.getAllSavingGoals(token).observeForever(response -> {
            if (response != null) {
                List<SavingGoal> list = new ArrayList<>();
                for (SavingGoalResponse res : response) {
                    SavingGoal goal = new SavingGoal();
                    goal.setGoalId(res.getGoalId());
                    goal.setUserId(res.getUserId());
                    goal.setCategoryId(res.getCategoryId());
                    goal.setName(res.getName());
                    goal.setDescription(res.getDescription());
                    goal.setCurrentAmount(BigDecimal.valueOf(res.getCurrentAmount()));
                    goal.setTargetAmount(BigDecimal.valueOf(res.getTargetAmount()));
                    goal.setTargetDate(SavingGoal.parseIsoDate(res.getTargetDate()));
                    list.add(goal);
                }
                _savingGoals.setValue(list);
            } else {
                _savingGoals.setValue(new ArrayList<>());
            }
        });
    }

    public void selectSavingGoal(SavingGoal savingGoal, Category category) {
        _selectedSavingGoal.setValue(new Pair<>(savingGoal, category));
    }

    public void clearSelectedSavingGoal() {
        _selectedSavingGoal.setValue(null);
    }

}
