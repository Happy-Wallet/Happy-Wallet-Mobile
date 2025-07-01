package com.example.happy_wallet_mobile.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CategoryService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CreateCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private final CategoryService categoryService;

    public CategoryRepository() {
        categoryService = APIClient.getRetrofit().create(CategoryService.class);
    }

    // get all categories
    public LiveData<List<CategoryResponse>> getAllCategories() {
        MutableLiveData<List<CategoryResponse>> data = new MutableLiveData<>();
        categoryService.getAllCategories().enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null); // hoặc có thể đẩy lên emptyList()
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    // create categories
    public LiveData<CreateCategoryResponse> createCategory(CreateCategoryRequest request) {
        MutableLiveData<CreateCategoryResponse> data = new MutableLiveData<>();
        categoryService.createCategory(request).enqueue(new Callback<CreateCategoryResponse>() {
            @Override
            public void onResponse(Call<CreateCategoryResponse> call, Response<CreateCategoryResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateCategoryResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
