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
    private final TransactionService transactionService;

    public TransactionRepository() {
        transactionService = APIClient.getRetrofit().create(TransactionService.class);
    }

    // get transactions
    public LiveData<List<TransactionResponse>> getTransactions(String bearerToken, String type) {
        MutableLiveData<List<TransactionResponse>> data = new MutableLiveData<>();
        transactionService.getTransactions(bearerToken, type)
                .enqueue(new Callback<List<TransactionResponse>>() {
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

    // create transaction
    public LiveData<CreateTransactionResponse> createTransaction(String bearerToken, String type, CreateTransactionRequest request) {
        MutableLiveData<CreateTransactionResponse> data = new MutableLiveData<>();
        transactionService.createTransaction(bearerToken, type, request)
                .enqueue(new Callback<CreateTransactionResponse>() {
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

    // update transaction
    public LiveData<TransactionResponse> updateTransaction(String bearerToken, int id, CreateTransactionRequest request) {
        MutableLiveData<TransactionResponse> data = new MutableLiveData<>();
        transactionService.updateTransaction(bearerToken, id, request)
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        if (response.isSuccessful()) {
                            data.setValue(response.body());
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    // delete transaction
    public LiveData<Boolean> deleteTransaction(String bearerToken, int id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        transactionService.deleteTransaction(bearerToken, id)
                .enqueue(new Callback<Void>() {
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
