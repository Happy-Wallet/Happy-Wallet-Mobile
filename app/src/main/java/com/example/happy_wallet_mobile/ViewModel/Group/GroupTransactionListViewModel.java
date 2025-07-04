package com.example.happy_wallet_mobile.ViewModel.Group;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.GroupTransactionService;
import com.example.happy_wallet_mobile.Data.Remote.Response.GroupTransaction.GroupTransactionResponse;
import com.example.happy_wallet_mobile.Data.Repository.GroupTransactionRepository;
import com.example.happy_wallet_mobile.Model.GroupTransactionItem;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionUiModel;
import com.example.happy_wallet_mobile.View.Utilities.ResourceUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupTransactionListViewModel extends ViewModel {
    private final GroupTransactionRepository repository;

    private final MutableLiveData<List<GroupTransactionResponse>> rawFundTransactions = new MutableLiveData<>();
    private final MutableLiveData<List<GroupTransactionItem>> fundTransactionItems = new MutableLiveData<>();

    public LiveData<List<GroupTransactionItem>> getFundTransactionItems() {
        return fundTransactionItems;
    }

    public GroupTransactionListViewModel() {
        GroupTransactionService api = APIClient.getRetrofit().create(GroupTransactionService.class);
        repository = new GroupTransactionRepository(api);
    }

    public void fetchFundTransactions(Context context, int fundId) {
        String token = "Bearer " + UserPreferences.getToken();

        repository.getFundTransactions(token, fundId).observeForever(result -> {
            rawFundTransactions.setValue(result != null ? result : List.of());
            // Chuyển đổi sang GroupTransactionItem
            List<GroupTransactionItem> items = new ArrayList<>();
            if (result != null) {
                for (GroupTransactionResponse res : result) {
                    GroupTransactionItem item = mapResponseToItem(res, context);
                    items.add(item);
                }
            }
            fundTransactionItems.setValue(items);
        });
    }

    private GroupTransactionItem mapResponseToItem(GroupTransactionResponse res, Context context) {
        GroupTransactionItem item = new GroupTransactionItem();
        // Gán dữ liệu trực tiếp vì response đã phẳng
        item.setFund_transaction_id(res.getFund_transaction_id());
        item.setFund_id(res.getFund_id());
        item.setUser_id(res.getUser_id());
        item.setAmount(res.getAmount());
        item.setType(res.getType());
        item.setDescription(res.getDescription());
        item.setCreated_at(res.getCreated_at());
        item.setUpdated_at(res.getUpdated_at());
        item.setUsername(res.getUsername());
        item.setEmail(res.getEmail());
        item.setAvatar_url(res.getAvatar_url());

        // Category
        GroupTransactionItem.Category category = new GroupTransactionItem.Category();
        if (res.getCategory() != null) {
            String colorResName = res.getCategory().getColor_res();
            String iconResName = res.getCategory().getIcon_res();
            int colorResId = (colorResName != null) ?
                    ResourceUtility.getColorResId(context, colorResName) : R.color.Paolo_Veronese_Green;
            int iconResId = (iconResName != null) ?
                    ResourceUtility.getDrawableResId(context, iconResName) : R.drawable.ic_bell;

            category.setCategory_id(res.getCategory().getCategory_id());
            category.setIcon_res(iconResId);
            category.setColor_res(colorResId);
            category.setType(res.getCategory().getType());
            category.setName(res.getCategory().getName());
        }
        item.setCategory(category);

        return item;
    }

}
