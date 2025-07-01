package com.example.happy_wallet_mobile.ViewModel.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
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

    private final CategoryRepository categoryRepository = new CategoryRepository();

    // Setters
    public void setCategory(Category category) {
        _category.setValue(category);
    }

    public void setSavingGoal(SavingGoal savingGoal) {
        _savingGoal.setValue(savingGoal);
    }

    public void loadDataFromServer() {
        categoryRepository.getAllCategories().observeForever(responseList -> {
            if (responseList != null) {
                List<Category> categories = new ArrayList<>();
                _categoryList.setValue(categories);
            } else {
                _categoryList.setValue(new ArrayList<>()); // hoặc null tuỳ bạn xử lý
            }
        });
    }


    public EditSavingGoalViewModel() {
        loadDataFromServer();
    }
}
