package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.GroupMember;
import com.example.happy_wallet_mobile.Model.GroupTransaction;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionHeader;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionItem;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupDailyTransactions.GroupTransactionUiModel;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class GroupActivitiesViewModel extends ViewModel {

    private final MutableLiveData<List<GroupTransactionUiModel>> groupedUiData = new MutableLiveData<>();
    public LiveData<List<GroupTransactionUiModel>> getGroupedUiData() {
        return groupedUiData;
    }

    public void buildGroupUiData(
            List<GroupTransaction> transactions,
            List<User> users,
            List<Category> categories
    ) {
        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));
        Map<Integer, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        Map<String, List<GroupTransaction>> transactionsByDate = new TreeMap<>(Collections.reverseOrder());
        for (GroupTransaction t : transactions) {
            String dateStr = t.getCreatedDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .toString();
            transactionsByDate.computeIfAbsent(dateStr, k -> new ArrayList<>()).add(t);
        }

        List<GroupTransactionUiModel> uiModels = new ArrayList<>();
        for (Map.Entry<String, List<GroupTransaction>> entry : transactionsByDate.entrySet()) {
            String date = entry.getKey();
            List<GroupTransaction> dayTransactions = entry.getValue();

            BigDecimal totalAmount = dayTransactions.stream()
                    .map(t -> t.getType() == eType.INCOME
                            ? t.getAmount()
                            : t.getAmount().negate())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            uiModels.add(new GroupTransactionHeader(date, totalAmount));

            for (GroupTransaction t : dayTransactions) {
                User user = userMap.get(t.getUserId());
                Category category = categoryMap.get(t.getCategoryId());

                uiModels.add(new GroupTransactionItem(
                        user,
                        category,
                        t
                ));
            }
        }
        groupedUiData.setValue(uiModels);
    }

}

