package com.example.happy_wallet_mobile.ViewModel.Category;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Utilities.ResourceUtility;

import java.util.ArrayList;
import java.util.List;

public class CategoryListViewModel extends ViewModel {

    private final CategoryRepository categoryRepository = new CategoryRepository(UserPreferences.getToken());
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }

    private final MutableLiveData<Category> selectedCategory = new MutableLiveData<>();
    public LiveData<Category> getSelectedCategory() {
        return selectedCategory;
    }
    public void setSelectCategory(Category category) {
        selectedCategory.setValue(category);
    }
    private final MutableLiveData<eType> type = new MutableLiveData<>();
    public LiveData<eType> getType(){
        return type;
    }
    public void setType(eType type){
        this.type.setValue(type);
    }

    public void fetchCategories(Context context) {
        LiveData<List<CategoryResponse>> source = categoryRepository.getCategories(null);
        source.observeForever(categoryResponses -> {
            Log.d("CategoryListViewModel", "CategoryListResponse: " + categoryResponses);
            if (categoryResponses != null) {
                Log.d("CategoryListViewModel", "categoryResponse: " + categoryResponses);
                categoryList.setValue(convertResponsesToCategories(context, categoryResponses));
                Log.d("CategoryListViewModel", "categoryList: " + categoryList);
            } else {
                categoryList.setValue(null);
            }
        });
    }

    private List<Category> convertResponsesToCategories(Context context, List<CategoryResponse> responses) {
        List<Category> list = new ArrayList<>();
        for (CategoryResponse r : responses) {
            int categoryId = r.getCategory_id();
            Integer userId = r.getUser_id();
            String colorResName = r.getColor_res();
            String iconResName = r.getIcon_res();

            int colorResId = (colorResName != null) ?
                    ResourceUtility.getColorResId(context, colorResName) : R.color.Paolo_Veronese_Green;
            int iconResId = (iconResName != null) ?
                    ResourceUtility.getDrawableResId(context, iconResName) : R.drawable.ic_bell;

            boolean isDefault = (userId == null);

            list.add(new Category(
                    categoryId,
                    userId,
                    colorResId,
                    iconResId,
                    com.example.happy_wallet_mobile.Model.eType.fromString(r.getType()),
                    r.getName(),
                    isDefault
            ));
        }
        return list;
    }
}
