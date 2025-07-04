package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Data.Remote.Request.GroupTransacion.GroupTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.GroupTransactionResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.MemberContributionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupTransactionService {
    @GET("fund_transactions/{fundId}")
    Call<List<GroupTransactionResponse>> getFundTransactions(
            @Header("Authorization") String token,
            @Path("fundId") int fundId
    );

    @POST("fund_transactions/{fundId}")
    Call<GroupTransactionResponse> createFundTransaction(
            @Header("Authorization") String token,
            @Path("fundId") int fundId,
            @Body GroupTransactionRequest request
    );

    @PUT("fund_transactions/{fundId}/transactions/{transactionId}")
    Call<GroupTransactionResponse> updateFundTransaction(
            @Header("Authorization") String token,
            @Path("fundId") int fundId,
            @Path("transactionId") int transactionId,
            @Body GroupTransactionRequest request
    );

    @DELETE("fund_transactions/{fundId}/transactions/{transactionId}")
    Call<Void> deleteFundTransaction(
            @Header("Authorization") String token,
            @Path("fundId") int fundId,
            @Path("transactionId") int transactionId
    );

    @GET("fund_transactions/{fundId}/contributions")
    Call<List<MemberContributionResponse>> getMemberContributions(
            @Header("Authorization") String token,
            @Path("fundId") int fundId
    );
}

