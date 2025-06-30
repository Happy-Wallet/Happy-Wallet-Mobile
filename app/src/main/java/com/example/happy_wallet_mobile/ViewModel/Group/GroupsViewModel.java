package com.example.happy_wallet_mobile.ViewModel.Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.Model.GroupMember;
import com.example.happy_wallet_mobile.Model.GroupTransaction;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupMemberContribution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupsViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categoryList = new MutableLiveData<>();
    public LiveData<List<Category>> getCategoryList(){
        return categoryList;
    }

    private final MutableLiveData<List<Group>> groupList = new MutableLiveData<>();
    public LiveData<List<Group>> getGroupList(){
        return groupList;
    }

    private final MutableLiveData<List<User>> groupMemberList = new MutableLiveData<>();
    public LiveData<List<User>> getGroupMemberList(){
        return groupMemberList;
    }

    private final MutableLiveData<List<GroupTransaction>> groupTransactionList = new MutableLiveData<>();
    public LiveData<List<GroupTransaction>> getGroupTransactionList(){
        return groupTransactionList;
    }

    private final MutableLiveData<List<GroupMemberContribution>> groupMemberContributionList = new MutableLiveData<>();
    public LiveData<List<GroupMemberContribution>> getGroupMemberContributionList() {
        return groupMemberContributionList;
    }

    public void loadMockData(){
        categoryList.setValue(MockDataProvider.getMockCategories());
        groupList.setValue(MockDataProvider.getMockGroups());
    }

    public void LoadGroupDetail(Group group){
        List<GroupMember> allMembers = MockDataProvider.getMockGroupMembers();
        List<GroupTransaction> allTransactions = MockDataProvider.getMockGroupTransactions();
        List<User> allUsers = MockDataProvider.getMockUsers();

        // Lọc member thuộc group
        List<GroupMember> membersInGroup = allMembers.stream()
                .filter(m -> m.getGroupId() == group.getId())
                .collect(Collectors.toList());

        // Lấy userId của members
        Set<Integer> memberUserIds = membersInGroup.stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toSet());

        // Lọc users tương ứng
        List<User> usersInGroup = allUsers.stream()
                .filter(u -> memberUserIds.contains(u.getId()))
                .collect(Collectors.toList());

        // Lọc transactions của group
        List<GroupTransaction> transactionsInGroup = allTransactions.stream()
                .filter(t -> t.getGroupId() == group.getId())
                .collect(Collectors.toList());

        // Set LiveData cho fragment
        groupMemberList.setValue(usersInGroup);
        groupTransactionList.setValue(transactionsInGroup);

        // Build danh sách contributions
        List<GroupMemberContribution> contributions = new ArrayList<>();
        for (GroupMember member : membersInGroup) {
            User user = allUsers.stream()
                    .filter(u -> u.getId() == member.getUserId())
                    .findFirst()
                    .orElse(null);

            if (user != null) {
                BigDecimal totalIncome = transactionsInGroup.stream()
                        .filter(t -> t.getUserId() == user.getId() && t.getType() == eType.INCOME)
                        .map(GroupTransaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                contributions.add(new GroupMemberContribution(user, member.getRole(), totalIncome));
            }
        }
        groupMemberContributionList.setValue(contributions);

    }
}

