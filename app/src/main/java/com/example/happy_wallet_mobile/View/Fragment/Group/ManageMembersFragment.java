package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersContributionRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.GroupMembersManagementRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.ManageMembersViewModel;

import java.util.List;

public class ManageMembersFragment extends Fragment {

    RecyclerView rcvMembers;
    GroupsViewModel groupsViewModel;
    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_members, container, false);

        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);

        rcvMembers = view.findViewById(R.id.rcvMembers);
        tvCancel = view.findViewById(R.id.tvCancel);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });


        //set data for rcvMembers
        rcvMembers.setLayoutManager(new LinearLayoutManager(requireContext()));
        GroupMembersManagementRecyclerViewAdapter groupMembersManagementRecyclerViewAdapter = new GroupMembersManagementRecyclerViewAdapter(
                List.of()
        );
        rcvMembers.setAdapter(groupMembersManagementRecyclerViewAdapter);
        //observe data from vm
        groupsViewModel.getGroupMemberContributionList().observe(getViewLifecycleOwner(), list -> {
            groupMembersManagementRecyclerViewAdapter.updateMemberContributionList(list);
        });

        return view;
    }
}