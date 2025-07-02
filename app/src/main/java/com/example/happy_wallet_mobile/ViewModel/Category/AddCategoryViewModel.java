package com.example.happy_wallet_mobile.ViewModel.Category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CreateCategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;

public class AddCategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;
    private final MutableLiveData<CreateCategoryResponse> createCategoryResult = new MutableLiveData<>();

    public AddCategoryViewModel() {
        categoryRepository = new CategoryRepository(UserPreferences.getToken());
    }

    public LiveData<CreateCategoryResponse> getCreateCategoryResult() {
        return createCategoryResult;
    }

    public void createCategory(String type, CreateCategoryRequest request) {
        // Lấy LiveData từ repository và observe nội bộ, rồi postValue
        categoryRepository.createCategory(type, request).observeForever(result -> {
            createCategoryResult.setValue(result);
            createCategoryResult.setValue(null);
        });
    }
}
