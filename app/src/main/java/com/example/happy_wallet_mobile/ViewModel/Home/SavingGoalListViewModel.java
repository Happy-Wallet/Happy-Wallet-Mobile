package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SavingGoalListViewModel extends ViewModel {

    private final SavingGoalRepository repository = new SavingGoalRepository();
    private final MutableLiveData<List<SavingGoal>> _savingGoals = new MutableLiveData<>();
    public LiveData<List<SavingGoal>> savingGoals = _savingGoals;

    public void fetchSavingGoals() {
        String token = UserPreferences.getToken();
        repository.getAllSavingGoals(token).observeForever(response -> {
            if (response != null) {
                List<SavingGoal> list = new ArrayList<>();
                for (SavingGoalResponse res : response) {
                    SavingGoal goal = new SavingGoal();
                    goal.setSavingGoalId(res.getId());
                    goal.setUserId(res.getUser_id());
                    goal.setTitle(res.getName());
                    goal.setDescription(res.getDescription());
                    goal.setCurrentAmount(BigDecimal.valueOf(res.getAmount()));
                    goal.setTargetAmount(BigDecimal.valueOf(res.getTarget()));
                    goal.setStartDate(res.getStart_date());
                    goal.setEndDate(res.getEnd_date());
                    goal.setCategoryId(res.getCategory_id());

                    list.add(goal);
                }
                _savingGoals.setValue(list);
            } else {
                _savingGoals.setValue(new ArrayList<>());
            }
        });
    }
}
