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

    public void loadMockData(){
        categoryList.setValue(MockDataProvider.getMockCategories());
        groupList.setValue(MockDataProvider.getMockGroups());
    }

    // load groups' members and transactions
    public void LoadGroupDetail(Group group){
        List<GroupMember> allMembers = MockDataProvider.getMockGroupMembers();
        List<GroupTransaction> allTransactions = MockDataProvider.getMockGroupTransactions();
        List<User> allUsers = MockDataProvider.getMockUsers();

        // Lọc các member thuộc group
        List<GroupMember> membersInGroup = allMembers.stream()
                .filter(m -> m.getGroupId() == group.getId())
                .collect(Collectors.toList());

        // Lấy danh sách userId của members
        Set<Integer> memberUserIds = membersInGroup.stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toSet());

        // Lọc user tương ứng
        List<User> usersInGroup = allUsers.stream()
                .filter(u -> memberUserIds.contains(u.getId()))
                .collect(Collectors.toList());

        // Lọc transaction của group
        List<GroupTransaction> transactionsInGroup = allTransactions.stream()
                .filter(t -> t.getGroupId() == group.getId())
                .collect(Collectors.toList());

        // Set giá trị cho LiveData
        groupMemberList.setValue(usersInGroup);
        groupTransactionList.setValue(transactionsInGroup);
    }
}
