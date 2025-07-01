package com.example.happy_wallet_mobile.ViewModel.Wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;

import java.util.List;

public class AddIncomeViewModel extends ViewModel {

    private final MutableLiveData<List<CategoryResponse>> _categoryList = new MutableLiveData<>();
    public LiveData<List<CategoryResponse>> CategoryList = _categoryList;

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public void loadDataFromServer() {
        categoryRepository.getAllCategories().observeForever(_categoryList::setValue);
    }
}
