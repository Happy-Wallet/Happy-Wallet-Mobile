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
import java.text.ParseException; // Import ParseException
import java.text.SimpleDateFormat; // Import SimpleDateFormat
import java.util.ArrayList;
import java.util.Date; // Import Date
import java.util.List;
import java.util.Locale; // Import Locale
import java.util.concurrent.TimeUnit; // Import TimeUnit

public class GroupsFragment extends Fragment {

    private MainViewModel mainViewModel;
    private GroupsViewModel groupsViewModel;
    private GroupActivitiesViewModel groupActivitiesViewModel;
    private RecyclerView rcvGroups, rcvMembers, rcvMembersActivities;
    private ImageView ivEditGroup;
    private TextView tvGroupName, tvAvailableBalance, tvSeeMoreActivities, tvInviteMember, tvManageMember;
    // Đã loại bỏ tvTargetAmount, tvTargetEndDate
    private TextView tvTargetLabel, tvDaysRemaining; // tvDaysRemaining mới

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
        tvDaysRemaining = view.findViewById(R.id.tvDaysRemaining);


        // Set up rcvGroups
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvGroups.setLayoutManager(layoutManager);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(
                requireContext(),
                new ArrayList<>(),
                new ArrayList<>());
        rcvGroups.setAdapter(groupRecyclerViewAdapter);

        // Observe data from ViewModel for rcvGroups
        groupsViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            groupRecyclerViewAdapter.updateCategoryList(categories);
        });
        groupsViewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            groupRecyclerViewAdapter.updateGroupList(groups);

            if (!groups.isEmpty() && groupsViewModel.getCurrentGroup().getValue() == null) {
                Group firstGroup = groups.get(0);
                groupsViewModel.setCurrentGroup(firstGroup);
                groupsViewModel.loadFundDetail(firstGroup.getId());
                rcvGroups.scrollToPosition(0);
            }
        });

        // Observe current group details to update UI
        groupsViewModel.getCurrentGroup().observe(getViewLifecycleOwner(), currentGroup -> {
            if (currentGroup != null) {
                tvGroupName.setText(currentGroup.getName());

                // LOGIC MỚI: Hiển thị số dư / số tiền mục tiêu
                String balanceText = CurrencyUtility.format(currentGroup.getCurrentAmount());
                if (currentGroup.isHasTarget() && currentGroup.getTargetAmount() != null) {
                    balanceText += " / " + CurrencyUtility.format(currentGroup.getTargetAmount());
                }
                tvAvailableBalance.setText(balanceText);


                // Set color for available balance
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
            groupsViewModel.setCurrentGroup(group);
            groupsViewModel.loadFundDetail(group.getId());
        });

        // rcvGroups add more click listener
        groupRecyclerViewAdapter.setOnAddClickListener(() -> {
            Log.d("GroupsFragment", "rcvGroups add more clicked");
            mainViewModel.navigateSubBelow(new AddGroupFragment());
        });

        // ivEditGroup click listener
        ivEditGroup.setOnClickListener(v -> {
            Log.d("GroupsFragment", "ivEditGroup clicked");
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
}