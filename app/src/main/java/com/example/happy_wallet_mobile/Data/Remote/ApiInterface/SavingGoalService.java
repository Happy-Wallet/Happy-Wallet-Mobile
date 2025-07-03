package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;



import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.CreateSavingGoalResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SavingGoalService {

    @GET("saving_goals")
    Call<List<SavingGoalResponse>> getAllSavingGoals(@Header("Authorization") String token);

    @POST("saving_goals")
    Call<CreateSavingGoalResponse> createSavingGoal(@Header("Authorization") String token, @Body CreateSavingGoalRequest request);

    @PUT("saving_goals/{id}")
    Call<SavingGoalResponse> updateSavingGoal(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body CreateSavingGoalRequest request
    );

    @DELETE("saving_goals/{id}")
    Call<Void> deleteSavingGoal(
            @Header("Authorization") String token,
            @Path("id") int id
    );
}
