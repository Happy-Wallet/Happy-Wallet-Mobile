package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;

import java.util.List;

public class AddExpenditureViewModel extends ViewModel {

    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> CategoryList = _categoryList;

    public void setDate(){
        _categoryList.setValue(MockDataProvider.getMockCategories());
    }
}
