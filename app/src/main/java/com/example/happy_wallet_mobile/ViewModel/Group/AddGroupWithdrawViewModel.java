package com.example.happy_wallet_mobile.ViewModel.Group;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.GroupTransactionService;
import com.example.happy_wallet_mobile.Data.Remote.Request.GroupTransacion.GroupTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.GroupTransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.GroupTransactionRepository;

import java.math.BigDecimal;

public class AddGroupWithdrawViewModel extends ViewModel {
    private final GroupTransactionRepository repository;
    private final MutableLiveData<GroupTransactionResponse> createResult = new MutableLiveData<>();

    public AddGroupWithdrawViewModel() {
        GroupTransactionService api = APIClient.getRetrofit().create(GroupTransactionService.class);
        repository = new GroupTransactionRepository(api);
    }

    public LiveData<GroupTransactionResponse> getCreateResult() {
        return createResult;
    }

    public void createGroupTransaction(int fundId, int categoryId, BigDecimal amount, String description) {
        String token = UserPreferences.getToken();

        // Tạo request dùng constructor
        GroupTransactionRequest request = new GroupTransactionRequest(
                amount,
                "expense",    // hoặc eType.INCOME.toDbValue()
                description,
                categoryId
        );

        repository.createFundTransaction(token, fundId, request).observeForever(result -> {
            createResult.setValue(result);
            createResult.setValue(null); // để reset observe

            if (result != null) {
                Log.d("ViewModel", "Tạo group transaction thành công: " + result.getFund_transaction_id());
            } else {
                Log.e("ViewModel", "Tạo group transaction thất bại");
            }
        });

    }
}
