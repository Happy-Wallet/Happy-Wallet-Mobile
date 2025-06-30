package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CreateCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryService {
    @GET("categories")
    Call<List<CategoryResponse>> getAllCategories();

    @POST("categories")
    Call<CreateCategoryResponse> createCategory(@Body CreateCategoryRequest request);
}
