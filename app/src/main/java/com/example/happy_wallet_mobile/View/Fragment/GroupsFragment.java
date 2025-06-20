package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.GroupRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class GroupsFragment extends Fragment {

    RecyclerView rcvGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        rcvGroup = view.findViewById(R.id.rvGroups);

        // set data for rcvGroup
        List<Group> groupList = new ArrayList<Group>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvGroup.setLayoutManager(layoutManager);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(requireContext(), groupList);
        rcvGroup.setAdapter(groupRecyclerViewAdapter);

        return view;
    }
}