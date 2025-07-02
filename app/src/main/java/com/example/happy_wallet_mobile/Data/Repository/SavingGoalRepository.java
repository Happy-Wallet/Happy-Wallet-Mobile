package com.example.happy_wallet_mobile.Data.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.SavingGoalService;
import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.CreateSavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavingGoalRepository {
    private final SavingGoalService service;

    public SavingGoalRepository() {
        service = APIClient.getRetrofit().create(SavingGoalService.class);
    }

    public LiveData<List<SavingGoalResponse>> getAllSavingGoals(String token) {
        MutableLiveData<List<SavingGoalResponse>> data = new MutableLiveData<>();
        service.getAllSavingGoals("Bearer " + token).enqueue(new Callback<List<SavingGoalResponse>>() {
            @Override
            public void onResponse(Call<List<SavingGoalResponse>> call, Response<List<SavingGoalResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<SavingGoalResponse>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<CreateSavingGoalResponse> createSavingGoal(String token, CreateSavingGoalRequest request) {
        MutableLiveData<CreateSavingGoalResponse> data = new MutableLiveData<>();
        service.createSavingGoal("Bearer " + token, request).enqueue(new Callback<CreateSavingGoalResponse>() {
            @Override
            public void onResponse(Call<CreateSavingGoalResponse> call, Response<CreateSavingGoalResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CreateSavingGoalResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<SavingGoalResponse> updateSavingGoal(String token, int id, CreateSavingGoalRequest request) {
        MutableLiveData<SavingGoalResponse> data = new MutableLiveData<>();
        service.updateSavingGoal("Bearer " + token, id, request).enqueue(new Callback<SavingGoalResponse>() {
            @Override
            public void onResponse(Call<SavingGoalResponse> call, Response<SavingGoalResponse> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SavingGoalResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Boolean> deleteSavingGoal(String token, int id) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        service.deleteSavingGoal("Bearer " + token, id).enqueue(new Callback<Void>() {
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
