package com.example.happy_wallet_mobile.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.TransactionService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Transaction.CreateTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.CreateTransactionResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepository {
    private final TransactionService service;

    public TransactionRepository() {
        service = APIClient.getRetrofit().create(TransactionService.class);
    }

    public LiveData<List<TransactionResponse>> getAllTransactions() {
        MutableLiveData<List<TransactionResponse>> data = new MutableLiveData<>();
        service.getAllTransactions().enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<CreateTransactionResponse> createTransaction(CreateTransactionRequest request) {
        MutableLiveData<CreateTransactionResponse> data = new MutableLiveData<>();
        service.createTransaction(request).enqueue(new Callback<CreateTransactionResponse>() {
            @Override
            public void onResponse(Call<CreateTransactionResponse> call, Response<CreateTransactionResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateTransactionResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
