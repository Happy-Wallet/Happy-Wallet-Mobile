package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Group;

import java.util.List;

public class GroupsViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> _categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> categoryList = _categoryList;

    private final MutableLiveData<List<Group>> _groupList = new MutableLiveData<>();
    public LiveData<List<Group>> groupList = _groupList;

    public void loadMockData(){
        _categoryList.setValue(MockDataProvider.getMockCategories());
        _groupList.setValue(MockDataProvider.getMockGroups());
    }
}
