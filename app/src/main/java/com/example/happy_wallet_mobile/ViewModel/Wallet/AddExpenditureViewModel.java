package com.example.happy_wallet_mobile.ViewModel.Wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Request.Transaction.CreateTransactionRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Transaction.CreateTransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.TransactionRepository;
import com.example.happy_wallet_mobile.Model.Category;

import java.math.BigDecimal;
import java.util.List;

public class AddExpenditureViewModel extends ViewModel {

    private final TransactionRepository transactionRepository = new TransactionRepository();

    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> getCategoryList(){
        return categoryList;
    }
    private final MutableLiveData<CreateTransactionResponse> createTransactionResponse = new MutableLiveData<>();
    public LiveData<CreateTransactionResponse> getCreateTransactionResponse() {
        return createTransactionResponse;
    }

    public void setData(){
        categoryList.setValue(MockDataProvider.getMockCategories());
    }


    // create Transactions
    public void createTransaction(int categoryId, BigDecimal amount, String description, String date) {
        String token = UserPreferences.getToken();
        if (token == null) return;

        CreateTransactionRequest request = new CreateTransactionRequest(
                categoryId,
                amount,
                description,
                date
        );

        LiveData<CreateTransactionResponse> source = transactionRepository.createTransaction(
                "Bearer " + token,
                "expense",
                request
        );

        source.observeForever(response -> {
            createTransactionResponse.setValue(response);
            createTransactionResponse.setValue(null);
        });


    }

}
