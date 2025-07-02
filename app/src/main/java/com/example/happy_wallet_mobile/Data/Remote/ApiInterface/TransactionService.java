package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Transaction.CreateTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.CreateTransactionResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;

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

public interface TransactionService {
    @POST("/transactions/{type}")
    Call<CreateTransactionResponse> createTransaction(
            @Header("Authorization") String bearerToken,
            @Path("type") String type,
            @Body CreateTransactionRequest request
    );

    @GET("/transactions")
    Call<List<TransactionResponse>> getTransactions(
            @Header("Authorization") String bearerToken,
            @Query("type") String type
    );

    @PUT("/transactions/{id}")
    Call<TransactionResponse> updateTransaction(
            @Header("Authorization") String bearerToken,
            @Path("id") int id,
            @Body CreateTransactionRequest request
    );

    @DELETE("/transactions/{id}")
    Call<Void> deleteTransaction(
            @Header("Authorization") String bearerToken,
            @Path("id") int id
    );
}
