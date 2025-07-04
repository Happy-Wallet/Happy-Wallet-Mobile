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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersContributionRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.GroupRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.MembersActivitiesRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
//import com.example.happy_wallet_mobile.ViewModel.Group.GroupActivitiesViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupTransactionListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GroupsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private GroupsViewModel groupsViewModel;
//    private GroupActivitiesViewModel groupActivitiesViewModel;
    private GroupTransactionListViewModel groupTransactionListViewModel;
    private RecyclerView rcvGroups, rcvMembers, rcvMembersActivities;
    private ImageView ivEditGroup;
    private TextView tvGroupName, tvAvailableBalance, tvSeeMoreActivities, tvInviteMember, tvManageMember;
    private TextView tvTargetLabel, tvDaysRemaining;
    private FrameLayout flFund, flWithdraw;
    private GroupMembersContributionRecyclerViewAdapter groupMembersRecyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        Log.d("GroupsFragment", "GroupsFragment onCreateView");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
//        groupActivitiesViewModel = new ViewModelProvider(requireActivity()).get(GroupActivitiesViewModel.class);
        groupTransactionListViewModel = new ViewModelProvider(requireActivity()).get(GroupTransactionListViewModel.class);

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
        tvDaysRemaining = view.findViewById(R.id.tvDaysRemaining);
        flFund = view.findViewById(R.id.flFund);
        flWithdraw = view.findViewById(R.id.flWithdraw);


        flFund.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new AddGroupContributeFragment());
        });

        flWithdraw.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new AddGroupWithdrawFragment());
        });

        // Set up rcvGroups
        rcvGroups.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(requireContext(), new ArrayList<>(), new ArrayList<>());
        rcvGroups.setAdapter(groupRecyclerViewAdapter);

        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), groupRecyclerViewAdapter::updateCategoryList);
        groupsViewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            groupRecyclerViewAdapter.updateGroupList(groups);
            if (!groups.isEmpty() && groupsViewModel.getCurrentGroup().getValue() == null) {
                groupsViewModel.setCurrentGroup(groups.get(0));
                rcvGroups.scrollToPosition(0);
            }
        });

        groupsViewModel.getCurrentGroup().observe(getViewLifecycleOwner(), currentGroup -> {
            if (currentGroup != null) {
                updateGroupInfoUI(currentGroup);

                // Fetch transaction khi đổi group
                groupTransactionListViewModel.fetchFundTransactions(requireContext(), currentGroup.getId());
            }
        });

        // Set up rcvMembersActivities
        rcvMembersActivities.setLayoutManager(new LinearLayoutManager(requireContext()));
        MembersActivitiesRecyclerViewAdapter membersActivitiesRecyclerViewAdapter = new MembersActivitiesRecyclerViewAdapter(
                requireContext(),
                new ArrayList<>());
        rcvMembersActivities.setAdapter(membersActivitiesRecyclerViewAdapter);

        groupTransactionListViewModel.getFundTransactionItems().observe(getViewLifecycleOwner(), items -> {
            Log.d("GroupsFragment", "Received transaction items size: " + (items != null ? items.size() : 0));
            membersActivitiesRecyclerViewAdapter.updateActivities(items);
        });


        // Set up rcvMembers
        rcvMembers.setLayoutManager(new LinearLayoutManager(requireContext()));
        groupMembersRecyclerViewAdapter = new GroupMembersContributionRecyclerViewAdapter(new ArrayList<>());
        rcvMembers.setAdapter(groupMembersRecyclerViewAdapter);

        groupsViewModel.getGroupMemberList().observe(getViewLifecycleOwner(), members -> {
            Log.d("GroupsFragment", "Updating members list. Size: " + (members != null ? members.size() : 0));
            groupMembersRecyclerViewAdapter.updateMemberList(members);
        });

        // Click listeners
        groupRecyclerViewAdapter.setOnItemClickListener(group -> {
            Log.d("GroupsFragment", group.getName() + " clicked");
            groupsViewModel.setCurrentGroup(group);
        });

        groupRecyclerViewAdapter.setOnAddClickListener(() -> {
            Log.d("GroupsFragment", "rcvGroups add more clicked");
            mainViewModel.navigateSubBelow(new AddGroupFragment());
        });

        ivEditGroup.setOnClickListener(v -> {
            if (groupsViewModel.getCurrentGroup().getValue() != null) {
                mainViewModel.navigateSubBelow(new EditGroupFragment());
            } else {
                Toast.makeText(requireContext(), "Vui lòng chọn một nhóm để chỉnh sửa.", Toast.LENGTH_SHORT).show();
            }
        });

//        tvSeeMoreActivities.setOnClickListener(v -> {
//            groupActivitiesViewModel.buildGroupUiData(
//                    groupsViewModel.getGroupTransactionList().getValue(),
//                    groupsViewModel.getGroupMemberUserList().getValue(),
//                    groupsViewModel.getCategoryList().getValue()
//            );
//            groupActivitiesViewModel.loadMonthlyData(groupsViewModel.getGroupTransactionList().getValue());
//            mainViewModel.navigateSubBelow(new GroupActivitiesFragment());
//        });

        tvInviteMember.setOnClickListener(v -> mainViewModel.navigateSubBelow(new InviteMemberFragment()));
        tvManageMember.setOnClickListener(v -> mainViewModel.navigateSubBelow(new ManageMembersFragment()));

        groupsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Show/hide progress bar here
        });

        groupsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                groupsViewModel.clearErrorMessage();
            }
        });

        groupsViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                groupsViewModel.clearSuccessMessage();
            }
        });

        groupsViewModel.loadAllFunds();

        return view;
    }

    private void updateGroupInfoUI(Group currentGroup) {
        tvGroupName.setText(currentGroup.getName());

        String balanceText = CurrencyUtility.format(currentGroup.getCurrentAmount());
        if (currentGroup.isHasTarget() && currentGroup.getTargetAmount() != null) {
            balanceText += " / " + CurrencyUtility.format(currentGroup.getTargetAmount());
        }
        tvAvailableBalance.setText(balanceText);

        if (currentGroup.getCurrentAmount().compareTo(BigDecimal.ZERO) < 0) {
            tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Radishical));
        } else {
            tvAvailableBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Paolo_Veronese_Green));
        }

        if (currentGroup.isHasTarget()) {
            tvTargetLabel.setVisibility(View.VISIBLE);
            tvDaysRemaining.setVisibility(View.VISIBLE);

            if (currentGroup.getTargetEndDate() != null && !currentGroup.getTargetEndDate().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date targetDate = sdf.parse(currentGroup.getTargetEndDate());
                    Date currentDate = new Date();

                    long diffInMillies = targetDate.getTime() - currentDate.getTime();
                    long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                    if (diffInDays > 0) {
                        tvDaysRemaining.setText(diffInDays + " day(s) remaining");
                    } else if (diffInDays == 0) {
                        tvDaysRemaining.setText("Last day!");
                    } else {
                        tvDaysRemaining.setText("Overdue " + Math.abs(diffInDays) + " day(s)");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    tvDaysRemaining.setText("Ngày không hợp lệ");
                }
            } else {
                tvDaysRemaining.setText("Ngày kết thúc không xác định");
            }

        } else {
            tvTargetLabel.setVisibility(View.GONE);
            tvDaysRemaining.setVisibility(View.GONE);
        }
    }
}
