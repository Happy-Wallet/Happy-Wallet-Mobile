package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.Transaction.CreateTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TransactionService {

    // GET /transactions
    @GET("transactions")
    Call<List<TransactionResponse>> getAllTransactions();

    // POST /transactions
    @POST("transactions")
    Call<CreateTransactionResponse> createTransaction(@Body CreateTransactionRequest request);
}
