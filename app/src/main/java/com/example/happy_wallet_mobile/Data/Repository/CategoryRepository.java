package com.example.happy_wallet_mobile.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CategoryService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.UpdateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CreateCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private final CategoryService categoryService;
    private final String token;

    public CategoryRepository(String token) {
        categoryService = APIClient.getRetrofit().create(CategoryService.class);
        this.token = "Bearer " + token;
    }

    public LiveData<List<CategoryResponse>> getCategories(String type) {
        MutableLiveData<List<CategoryResponse>> data = new MutableLiveData<>();

        categoryService.getCategories(token, type).enqueue(new Callback<List<CategoryResponse>>() {
            @Override
            public void onResponse(Call<List<CategoryResponse>> call, Response<List<CategoryResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<CreateCategoryResponse> createCategory(String type, CreateCategoryRequest request) {
        MutableLiveData<CreateCategoryResponse> data = new MutableLiveData<>();

        categoryService.createCategory(token, type, request).enqueue(new Callback<CreateCategoryResponse>() {
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

    public LiveData<CategoryResponse> updateCategory(int categoryId, UpdateCategoryRequest request) {
        MutableLiveData<CategoryResponse> data = new MutableLiveData<>();

        categoryService.updateCategory(token, categoryId, request).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Boolean> deleteCategory(int categoryId) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();

        categoryService.deleteCategory(token, categoryId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                data.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                data.setValue(false);
            }
        });

        return data;
    }
}
