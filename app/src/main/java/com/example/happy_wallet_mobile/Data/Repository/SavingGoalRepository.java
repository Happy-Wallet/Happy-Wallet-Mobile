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

    public LiveData<List<SavingGoalResponse>> getAllSavingGoals() {
        MutableLiveData<List<SavingGoalResponse>> data = new MutableLiveData<>();
        service.getAllSavingGoals().enqueue(new Callback<List<SavingGoalResponse>>() {
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

    public LiveData<CreateSavingGoalResponse> createSavingGoal(CreateSavingGoalRequest request) {
        MutableLiveData<CreateSavingGoalResponse> data = new MutableLiveData<>();
        service.createSavingGoal(request).enqueue(new Callback<CreateSavingGoalResponse>() {
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
}
