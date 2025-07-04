package com.example.happy_wallet_mobile.Data.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.GroupTransactionService;
import com.example.happy_wallet_mobile.Data.Remote.Request.GroupTransacion.GroupTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.GroupTransactionResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.MemberContributionResponse;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.GroupTransactionItem;
import com.example.happy_wallet_mobile.Model.eType;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupTransactionRepository {
    private final GroupTransactionService api;

    public GroupTransactionRepository(GroupTransactionService api) {
        this.api = api;
    }

    public LiveData<List<GroupTransactionResponse>> getFundTransactions(String token, int fundId) {
        MutableLiveData<List<GroupTransactionResponse>> liveData = new MutableLiveData<>();
        api.getFundTransactions(token, fundId).enqueue(new Callback<List<GroupTransactionResponse>>() {
            @Override
            public void onResponse(Call<List<GroupTransactionResponse>> call, Response<List<GroupTransactionResponse>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("Repository", "getFundTransactions: failed " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GroupTransactionResponse>> call, Throwable t) {
                Log.e("Repository", "getFundTransactions: error", t);
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<GroupTransactionResponse> createFundTransaction(String token, int fundId, GroupTransactionRequest request) {
        MutableLiveData<GroupTransactionResponse> liveData = new MutableLiveData<>();
        api.createFundTransaction(token, fundId, request).enqueue(new Callback<GroupTransactionResponse>() {
            @Override
            public void onResponse(Call<GroupTransactionResponse> call, Response<GroupTransactionResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("Repository", "createFundTransaction: failed " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GroupTransactionResponse> call, Throwable t) {
                Log.e("Repository", "createFundTransaction: error", t);
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<GroupTransactionResponse> updateFundTransaction(String token, int fundId, int transactionId, GroupTransactionRequest request) {
        MutableLiveData<GroupTransactionResponse> liveData = new MutableLiveData<>();
        api.updateFundTransaction(token, fundId, transactionId, request).enqueue(new Callback<GroupTransactionResponse>() {
            @Override
            public void onResponse(Call<GroupTransactionResponse> call, Response<GroupTransactionResponse> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("Repository", "updateFundTransaction: failed " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GroupTransactionResponse> call, Throwable t) {
                Log.e("Repository", "updateFundTransaction: error", t);
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public LiveData<Boolean> deleteFundTransaction(String token, int fundId, int transactionId) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        api.deleteFundTransaction(token, fundId, transactionId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                liveData.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Repository", "deleteFundTransaction: error", t);
                liveData.postValue(false);
            }
        });
        return liveData;
    }

    public LiveData<List<MemberContributionResponse>> getMemberContributions(String token, int fundId) {
        MutableLiveData<List<MemberContributionResponse>> liveData = new MutableLiveData<>();
        api.getMemberContributions(token, fundId).enqueue(new Callback<List<MemberContributionResponse>>() {
            @Override
            public void onResponse(Call<List<MemberContributionResponse>> call, Response<List<MemberContributionResponse>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    Log.e("Repository", "getMemberContributions: failed " + response.code());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<MemberContributionResponse>> call, Throwable t) {
                Log.e("Repository", "getMemberContributions: error", t);
                liveData.postValue(null);
            }
        });
        return liveData;
    }
}

