package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;



import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.CreateSavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SavingGoalService {

    @GET("saving_goals")
    Call<List<SavingGoalResponse>> getAllSavingGoals();

    @POST("saving_goals")
    Call<CreateSavingGoalResponse> createSavingGoal(@Body CreateSavingGoalRequest request);
}
