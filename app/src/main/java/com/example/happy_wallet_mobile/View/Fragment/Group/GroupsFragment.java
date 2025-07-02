package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersContributionRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.GroupRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.MembersActivitiesRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupActivitiesViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList; // Import ArrayList for initial empty lists

public class GroupsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private GroupsViewModel groupsViewModel;
    private GroupActivitiesViewModel groupActivitiesViewModel;
    private RecyclerView rcvGroups, rcvMembers, rcvMembersActivities;
    private ImageView ivEditGroup;
    private TextView tvGroupName, tvAvailableBalance, tvSeeMoreActivities, tvInviteMember, tvManageMember;
    private TextView tvTargetAmount, tvTargetEndDate, tvTargetLabel; // New TextViews for target info

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        Log.d("GroupsFragment", "GroupsFragment on create view");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        groupActivitiesViewModel = new ViewModelProvider(requireActivity()).get(GroupActivitiesViewModel.class);

        // Initialize Views
        rcvGroups = view.findViewById(R.id.rcvGroups);
        rcvMembers = view.findViewById(R.id.rcvMembers);
        rcvMembersActivities = view.findViewById(R.id.rcvMembersActivities);
        ivEditGroup = view.findViewById(R.id.ivEditGroup);
        tvGroupName = view.findViewById(R.id.tvGroupName);
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
        tvSeeMoreActivities = view.findViewById(R.id.tvSeeMoreActivities);
        tvInviteMember = view.findViewById(R.id.tvInviteMember);
        tvManageMember = view.findViewById(R.id.tvManageMember);

        tvTargetLabel = view.findViewById(R.id.tvTargetLabel);
        tvTargetAmount = view.findViewById(R.id.tvTargetAmount);
        tvTargetEndDate = view.findViewById(R.id.tvTargetEndDate);


        // Set up rcvGroups
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvGroups.setLayoutManager(layoutManager);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(
                requireContext(),
                new ArrayList<>(), // Initial empty list for groups
                new ArrayList<>()); // Initial empty list for categories
        rcvGroups.setAdapter(groupRecyclerViewAdapter);

        // Observe data from ViewModel for rcvGroups
        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            groupRecyclerViewAdapter.updateCategoryList(categories);
        });
        groupsViewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            groupRecyclerViewAdapter.updateGroupList(groups);

            // Automatically select the first group when groups data is loaded
            // Only load details if currentGroup is null (first load)
            if (!groups.isEmpty() && groupsViewModel.getCurrentGroup().getValue() == null) {
                Group firstGroup = groups.get(0);
                groupsViewModel.setCurrentGroup(firstGroup); // Set current group in ViewModel
                groupsViewModel.loadFundDetail(firstGroup.getId()); // Load details for the first group

                // Scroll to the first item (optional)
                rcvGroups.scrollToPosition(0);
            }
        });

        // Observe current group details to update UI
        groupsViewModel.getCurrentGroup().observe(getViewLifecycleOwner(), currentGroup -> {
            if (currentGroup != null) {
                tvGroupName.setText(currentGroup.getName());
                tvAvailableBalance.setText(CurrencyUtility.format(currentGroup.getCurrentAmount()));

                // Set color for available balance
                if (currentGroup.getCurrentAmount().compareTo(BigDecimal.ZERO) < 0) {
                    tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Radishical));
                } else {
                    tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Paolo_Veronese_Green));
                }

                // Update target information visibility and text
                if (currentGroup.isHasTarget()) {
                    tvTargetLabel.setVisibility(View.VISIBLE);
                    tvTargetAmount.setVisibility(View.VISIBLE);
                    tvTargetEndDate.setVisibility(View.VISIBLE);

                    tvTargetLabel.setText("Mục tiêu:");
                    tvTargetAmount.setText("Số tiền: " + (currentGroup.getTargetAmount() != null ? CurrencyUtility.format(currentGroup.getTargetAmount()) : "N/A"));
                    tvTargetEndDate.setText("Ngày kết thúc: " + (currentGroup.getTargetEndDate() != null ? currentGroup.getTargetEndDate() : "N/A"));
                } else {
                    tvTargetLabel.setVisibility(View.GONE);
                    tvTargetAmount.setVisibility(View.GONE);
                    tvTargetEndDate.setVisibility(View.GONE);
                }
            }
        });


        // Set up rcvMembersActivities
        rcvMembersActivities.setLayoutManager(new LinearLayoutManager(requireContext()));
        MembersActivitiesRecyclerViewAdapter membersActivitiesRecyclerViewAdapter = new MembersActivitiesRecyclerViewAdapter(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        rcvMembersActivities.setAdapter(membersActivitiesRecyclerViewAdapter);
        // Observe data from vm for rcvMembersActivities
        groupsViewModel.getGroupTransactionList().observe(getViewLifecycleOwner(), transactions -> {
            membersActivitiesRecyclerViewAdapter.updateActivities(transactions);
            membersActivitiesRecyclerViewAdapter.refresh();
        });
        groupsViewModel.getGroupMemberUserList().observe(getViewLifecycleOwner(), memberUser -> {
            membersActivitiesRecyclerViewAdapter.updateGroupMembers(memberUser);
            membersActivitiesRecyclerViewAdapter.refresh();
        });
        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            membersActivitiesRecyclerViewAdapter.updateCategories(categories);
            membersActivitiesRecyclerViewAdapter.refresh();
        });

        // Set up rcvMembers (Contributions)
        rcvMembers.setLayoutManager(new LinearLayoutManager(requireContext()));
        GroupMembersContributionRecyclerViewAdapter groupMembersRecyclerViewAdapter = new GroupMembersContributionRecyclerViewAdapter(
                new ArrayList<>()
        );
        rcvMembers.setAdapter(groupMembersRecyclerViewAdapter);
        // Observe data from vm for rcvMembers
        groupsViewModel.getGroupMemberContributionList().observe(getViewLifecycleOwner(), list -> {
            groupMembersRecyclerViewAdapter.updateMemberContributionList(list);
        });


        // rcvGroups item click listener
        groupRecyclerViewAdapter.setOnItemClickListener(group -> {
            Log.d("GroupsFragment", group.getName() + " clicked");
            groupsViewModel.setCurrentGroup(group); // Set current group in ViewModel
            groupsViewModel.loadFundDetail(group.getId()); // Load details for the clicked group via API
            // UI will be updated by the observer for getCurrentGroup()
        });

        // rcvGroups add more click listener
        groupRecyclerViewAdapter.setOnAddClickListener(() -> {
            Log.d("GroupsFragment", "rcvGroups add more clicked");
            mainViewModel.navigateSubBelow(new AddGroupFragment());
        });

        // ivEditGroup click listener
        ivEditGroup.setOnClickListener(v -> {
            Log.d("GroupsFragment", "ivEditGroup clicked");
            // Ensure currentGroup is set before navigating to EditGroupFragment
            if (groupsViewModel.getCurrentGroup().getValue() != null) {
                mainViewModel.navigateSubBelow(new EditGroupFragment());
            } else {
                Toast.makeText(requireContext(), "Vui lòng chọn một nhóm để chỉnh sửa.", Toast.LENGTH_SHORT).show();
            }
        });

        // tvSeeMoreActivities clicked
        tvSeeMoreActivities.setOnClickListener(v -> {
            Log.d("GroupsFragment", "tvSeeMoreActivites clicked");
            groupActivitiesViewModel.buildGroupUiData(
                    groupsViewModel.getGroupTransactionList().getValue(),
                    groupsViewModel.getGroupMemberUserList().getValue(),
                    groupsViewModel.getCategoryList().getValue()
            );
            groupActivitiesViewModel.loadMonthlyData(groupsViewModel.getGroupTransactionList().getValue());
            mainViewModel.navigateSubBelow(new GroupActivitiesFragment());
        });

        // tvInviteMember clicked
        tvInviteMember.setOnClickListener(v -> {
            Log.d("GroupsFragment", "tvInviteMember clicked");
            mainViewModel.navigateSubBelow(new InviteMemberFragment());
        });

        // tvManageMember clicked
        tvManageMember.setOnClickListener(v -> {
            Log.d("GroupsFragment", "tvManageMember clicked");
            mainViewModel.navigateSubBelow(new ManageMembersFragment());
        });

        // Observe ViewModel states for API calls
        groupsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // You can show/hide a ProgressBar here
            // Example: if (isLoading) showProgressBar(); else hideProgressBar();
        });

        groupsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                groupsViewModel.clearErrorMessage(); // Clear message after showing
            }
        });

        groupsViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                groupsViewModel.clearSuccessMessage(); // Clear message after showing
            }
        });

        // Initial data load when the fragment is created/resumed
        // Load all funds from the API
        groupsViewModel.loadAllFunds();

        return view;
    }
}