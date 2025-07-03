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
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryService {

    @GET("/categories")
    Call<List<CategoryResponse>> getCategories(
            @Header("Authorization") String token,
            @Query("type") String type
    );

    @POST("/category/{type}")
    Call<CreateCategoryResponse> createCategory(
            @Header("Authorization") String token,
            @Path("type") String type,
            @Body CreateCategoryRequest request
    );

    @PUT("/category/{id}")
    Call<CategoryResponse> updateCategory(
            @Header("Authorization") String token,
            @Path("id") int categoryId,
            @Body UpdateCategoryRequest request
    );

    @DELETE("/category/{id}")
    Call<Void> deleteCategory(
            @Header("Authorization") String token,
            @Path("id") int categoryId
    );
}
