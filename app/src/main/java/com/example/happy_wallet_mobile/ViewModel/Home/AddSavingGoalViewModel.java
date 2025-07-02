package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Data.Repository.SavingGoalRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;

import java.util.List;

public class AddSavingGoalViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> categoryList = _categoryList;

    private final MutableLiveData<Boolean> _createResult = new MutableLiveData<>();
    public LiveData<Boolean> createResult = _createResult;

    private final SavingGoalRepository savingGoalRepository = new SavingGoalRepository();

    // Dùng khi muốn test offline
    public void loadMockData() {
        _categoryList.setValue(MockDataProvider.getMockCategories());
    }


    // Gọi API tạo mới saving goal
    public void createSavingGoal(String token, CreateSavingGoalRequest request) {
        savingGoalRepository.createSavingGoal(token, request).observeForever(response -> {
            _createResult.setValue(response != null);
            _createResult.setValue(null);
        });
    }
}
