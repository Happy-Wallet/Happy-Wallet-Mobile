package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.UpdateCategoryRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CreateCategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories")
    Call<List<CategoryResponse>> getAllCategories();

    @POST("categories")
    Call<CreateCategoryResponse> createCategory(@Body CreateCategoryRequest request);

    @PUT("categories/{id}")
    Call<CategoryResponse> updateCategory(@Path("id") int id, @Body UpdateCategoryRequest request);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Path("id") int categoryId);
}
