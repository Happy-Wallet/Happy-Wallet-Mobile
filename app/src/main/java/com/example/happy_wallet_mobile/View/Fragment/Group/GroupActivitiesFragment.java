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
import com.example.happy_wallet_mobile.View.Adapter.GroupTransactionsRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.MonthIAEAdapter;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupActivitiesViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;

import java.util.List;


public class GroupActivitiesFragment extends Fragment {

    RecyclerView rcvMonthlyGraphs, rcvGroupDailyTransactions;
    TextView tvCancel;
    private GroupActivitiesViewModel groupActivitiesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_activities, container, false);

        groupActivitiesViewModel = new ViewModelProvider(requireActivity()).get(GroupActivitiesViewModel.class);

        rcvMonthlyGraphs = view.findViewById(R.id.rcvMonthlyGraphs);
        rcvGroupDailyTransactions = view.findViewById(R.id.rcvGroupDailyTransactions);
        tvCancel = view.findViewById(R.id.tvCancel);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //set data for rcvGroupDailyTransactions
        rcvGroupDailyTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        groupActivitiesViewModel.getGroupedUiData().observe(getViewLifecycleOwner(), uiData -> {
            GroupTransactionsRecyclerViewAdapter groupTransactionsRecyclerViewAdapter = new GroupTransactionsRecyclerViewAdapter(requireContext(), uiData);
            rcvGroupDailyTransactions.setAdapter(groupTransactionsRecyclerViewAdapter);
        });

        //set data for rcvMonthlyGraphs
        rcvMonthlyGraphs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        MonthIAEAdapter monthIAEAdapter = new MonthIAEAdapter(List.of());
        rcvMonthlyGraphs.setAdapter(monthIAEAdapter);
        // observe data từ viewmodel
        groupActivitiesViewModel.getMonthlyData().observe(getViewLifecycleOwner(), monthlyData -> {
            monthIAEAdapter.update(monthlyData);
        });

        return view;
    }
}