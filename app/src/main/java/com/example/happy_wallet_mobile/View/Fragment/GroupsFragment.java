package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class GroupsFragment extends Fragment {

    MainViewModel mainViewModel;
    RecyclerView rcvGroup;
    ImageView ivEditGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        Log.d("GroupsFragment", "GroupsFragment on create view");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        rcvGroup = view.findViewById(R.id.rvGroups);
        ivEditGroup = view.findViewById(R.id.ivEditGroup);

        // set data for rcvGroup
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvGroup.setLayoutManager(layoutManager);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(
                requireContext(),
                MockDataProvider.getMockGroups(),
                MockDataProvider.getMockCategories(),
                MockDataProvider.getMockIcons());
        rcvGroup.setAdapter(groupRecyclerViewAdapter);

        // rcvGroups item click
        groupRecyclerViewAdapter.setOnItemClickListener(group -> {
            Log.d("GroupFragment", "rcvGroups item clicked");
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