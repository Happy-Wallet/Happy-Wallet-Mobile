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
import android.widget.FrameLayout;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.DailyTransactionsRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.WalletViewModel;


public class WalletFragment extends Fragment {

    MainViewModel mainViewModel;
    WalletViewModel walletViewModel;
    FrameLayout flAddIncome, flAddExpenditure;
    RecyclerView rvTransactions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        rvTransactions = view.findViewById(R.id.rvTransactions);
        flAddIncome = view.findViewById(R.id.flAddIncome);
        flAddExpenditure = view.findViewById(R.id.flAddExpenditure);

        // add income
        flAddIncome.setOnClickListener(v -> {
            Log.d("WalletFragment", "tvIncome clicked");
            mainViewModel.navigateSubBelow(new AddIncomeFragment());
        });

        // add expenditure
        flAddExpenditure.setOnClickListener(v -> {
            Log.d("WalletFragment", "tvExpenditure clicked");
            mainViewModel.navigateSubBelow(new AddExpenditureFragment());
        });

        walletViewModel.uiModels.observe(getViewLifecycleOwner(), uiModels -> {
            DailyTransactionsRecyclerViewAdapter adapter = new DailyTransactionsRecyclerViewAdapter(getContext(), uiModels);
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        });

        walletViewModel.loadGroupedTransactions(
                MockDataProvider.getMockTransactions(),
                MockDataProvider.getMockCategories(),
                MockDataProvider.getMockIcons()
        );

        return view;
    }
}