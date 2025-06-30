package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.GroupRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.MembersActivitiesRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.math.BigDecimal;
import java.util.List;


public class GroupsFragment extends Fragment {

    MainViewModel mainViewModel;
    GroupsViewModel groupsViewModel;
    RecyclerView rcvGroups, rcvMembers, rcvMembersActivities;
    ImageView ivEditGroup;
    TextView tvGroupName, tvAvailableBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        Log.d("GroupsFragment", "GroupsFragment on create view");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);

        groupsViewModel.loadMockData();

        rcvGroups = view.findViewById(R.id.rcvGroups);
        rcvMembers = view.findViewById(R.id.rcvMembers);
        rcvMembersActivities = view.findViewById(R.id.rcvMembersActivities);
        ivEditGroup = view.findViewById(R.id.ivEditGroup);
        tvGroupName = view.findViewById(R.id.tvGroupName);
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);

        // set data for rcvGroup
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvGroups.setLayoutManager(layoutManager);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(
                requireContext(),
                List.of(),
                List.of());
        rcvGroups.setAdapter(groupRecyclerViewAdapter);
        // observe data from viewmodel
        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            groupRecyclerViewAdapter.updateCategoryList(categories);
        });
        groupsViewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            groupRecyclerViewAdapter.updateGroupList(groups);

            // tự động chọn group đầu tiên khi mở fragment
            if (!groups.isEmpty()) {
                Group firstGroup = groups.get(0);
                groupsViewModel.LoadGroupDetail(firstGroup);

                tvGroupName.setText(firstGroup.getName());
                tvAvailableBalance.setText(CurrencyUtility.format(firstGroup.getCurrentAmount()));
                if (firstGroup.getCurrentAmount().compareTo(BigDecimal.ZERO) < 0) {
                    tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Radishical));
                } else {
                    tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Paolo_Veronese_Green));
                }

                rcvGroups.scrollToPosition(0);
            }
        });

        //set data for rcvMembersActivities
        rcvMembersActivities.setLayoutManager(new LinearLayoutManager(requireContext()));
        MembersActivitiesRecyclerViewAdapter membersActivitiesRecyclerViewAdapter = new MembersActivitiesRecyclerViewAdapter(
                List.of(),
                List.of(),
                List.of()
        );
        rcvMembersActivities.setAdapter(membersActivitiesRecyclerViewAdapter);
        // observe data from vm
        groupsViewModel.getGroupTransactionList().observe(getViewLifecycleOwner(), transactions -> {
            membersActivitiesRecyclerViewAdapter.updateActivities(transactions);
            membersActivitiesRecyclerViewAdapter.refresh();
        });
        groupsViewModel.getGroupMemberList().observe(getViewLifecycleOwner(), members -> {
            membersActivitiesRecyclerViewAdapter.updateGroupMembers(members);
            membersActivitiesRecyclerViewAdapter.refresh();
        });

        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            membersActivitiesRecyclerViewAdapter.updateCategories(categories);
            membersActivitiesRecyclerViewAdapter.refresh();
        });

        //set data for rcvMembers
        rcvMembers.setLayoutManager(new LinearLayoutManager(requireContext()));
        GroupMembersRecyclerViewAdapter groupMembersRecyclerViewAdapter = new GroupMembersRecyclerViewAdapter(
                List.of()
        );
        rcvMembers.setAdapter(groupMembersRecyclerViewAdapter);
        //observe data from vm
        groupsViewModel.getGroupMemberContributionList().observe(getViewLifecycleOwner(), list -> {
            groupMembersRecyclerViewAdapter.updateMemberContributionList(list);
        });




        // rcvGroups item click
        groupRecyclerViewAdapter.setOnItemClickListener(group -> {
            Log.d("GroupFragment", group.getName() + " clicked");
            groupsViewModel.LoadGroupDetail(group);

            tvGroupName.setText(group.getName());

            tvAvailableBalance.setText(CurrencyUtility.format(group.getCurrentAmount()));
            if (group.getCurrentAmount().compareTo(BigDecimal.ZERO) < 0) {
                tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Radishical));
            } else {
                tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Paolo_Veronese_Green));
            }
        });

        // rcvGroups add more click
        groupRecyclerViewAdapter.setOnAddClickListener(() -> {
            Log.d("GroupFragment", "rcvGroups add more clicked");
            mainViewModel.navigateSubBelow(new AddGroupFragment());
        });

        // ivEditGroup
        ivEditGroup.setOnClickListener(v -> {
            Log.d("GroupFragment", "ivEditGroup clicked");
            EditGroupFragment editGroupFragment = new EditGroupFragment();
            mainViewModel.navigateSubBelow(editGroupFragment);
        });

        return view;
    }
}