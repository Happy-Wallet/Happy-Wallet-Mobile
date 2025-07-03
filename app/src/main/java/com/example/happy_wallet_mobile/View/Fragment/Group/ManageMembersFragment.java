package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.Model.GroupMember;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersManagementRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel; // Cần GroupsViewModel để lấy group hiện tại
import com.example.happy_wallet_mobile.ViewModel.Group.ManageMembersViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.util.ArrayList;

public class ManageMembersFragment extends Fragment {

    private ManageMembersViewModel manageMembersViewModel;
    private GroupsViewModel groupsViewModel; // Để lấy group hiện tại
    private MainViewModel mainViewModel; // Để navigate
    private RecyclerView rcvMembers;
    private TextView tvCancel;

    private GroupMembersManagementRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_members, container, false);

        manageMembersViewModel = new ViewModelProvider(this).get(ManageMembersViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        rcvMembers = view.findViewById(R.id.rcvMembers);
        tvCancel = view.findViewById(R.id.tvCancel);

        // Setup RecyclerView
        rcvMembers.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new GroupMembersManagementRecyclerViewAdapter(new ArrayList<>());
        rcvMembers.setAdapter(adapter);

        // Observe LiveData from ViewModel
        manageMembersViewModel.getMembersList().observe(getViewLifecycleOwner(), members -> {
            Log.d("ManageMembersFragment", "Members list updated: " + (members != null ? members.size() : 0));
            adapter.updateMemberList(members);
        });

        manageMembersViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Hiển thị/ẩn ProgressBar nếu cần
        });

        manageMembersViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                manageMembersViewModel.clearMessages();
            }
        });

        manageMembersViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                manageMembersViewModel.clearMessages();
            }
        });

        adapter.setOnMemberActionListener(new GroupMembersManagementRecyclerViewAdapter.OnMemberActionListener() {
            @Override
            public void onDeleteClick(GroupMember member) {
                Group currentGroup = groupsViewModel.getCurrentGroup().getValue();
                if (currentGroup != null) {
                    Log.d("ManageMembersFragment", "Attempting to delete member: " + member.getUsername() + " from fund: " + currentGroup.getId());
                    manageMembersViewModel.removeMember(currentGroup.getId(), member.getUserId());
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy quỹ hiện tại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChangeRoleClick(GroupMember member, String newRole) {
                Group currentGroup = groupsViewModel.getCurrentGroup().getValue();
                if (currentGroup != null) {
                    Log.d("ManageMembersFragment", "Attempting to change role of " + member.getUsername() + " to " + newRole + " in fund: " + currentGroup.getId());
                    manageMembersViewModel.updateMemberRole(currentGroup.getId(), member.getUserId(), newRole);
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy quỹ hiện tại.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Group currentGroup = groupsViewModel.getCurrentGroup().getValue();
        if (currentGroup != null) {
            manageMembersViewModel.setCurrentManagedGroup(currentGroup);
            manageMembersViewModel.loadMembers(currentGroup.getId());
        } else {
            Toast.makeText(requireContext(), "Vui lòng chọn một nhóm để quản lý thành viên.", Toast.LENGTH_LONG).show();
            mainViewModel.navigateBack();
        }

        tvCancel.setOnClickListener(v -> mainViewModel.navigateBack());

        return view;
    }
}