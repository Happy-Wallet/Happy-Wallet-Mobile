package com.example.happy_wallet_mobile.ViewModel.Category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.UpdateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;

public class EditCategoryViewModel extends ViewModel {

    private final CategoryRepository categoryRepository;

    private final MutableLiveData<CategoryResponse> updateCategoryResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deleteCategoryResult = new MutableLiveData<>();

    public EditCategoryViewModel() {
        categoryRepository = new CategoryRepository(UserPreferences.getToken());
    }

    public LiveData<CategoryResponse> getUpdateCategoryResult() {
        return updateCategoryResult;
    }

    public LiveData<Boolean> getDeleteCategoryResult() {
        return deleteCategoryResult;
    }

    public void updateCategory(int categoryId, UpdateCategoryRequest request) {
        categoryRepository.updateCategory(categoryId, request).observeForever(result -> {
            updateCategoryResult.setValue(result);
            updateCategoryResult.setValue(null);
        });
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteCategory(categoryId).observeForever(success -> {
            deleteCategoryResult.setValue(success);
            deleteCategoryResult.setValue(null);
        });
    }
}
