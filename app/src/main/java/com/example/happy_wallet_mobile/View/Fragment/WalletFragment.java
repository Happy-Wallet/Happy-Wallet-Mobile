package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.DailyTransactionsRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.TransactionUiModel;
import com.example.happy_wallet_mobile.ViewModel.WalletViewModel;

import java.util.List;


public class WalletFragment extends Fragment {

    RecyclerView rvTransactions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        WalletViewModel viewModel = new ViewModelProvider(this).get(WalletViewModel.class);

        rvTransactions = view.findViewById(R.id.rvTransactions);

        
        viewModel.uiModels.observe(getViewLifecycleOwner(), uiModels -> {
            DailyTransactionsRecyclerViewAdapter adapter = new DailyTransactionsRecyclerViewAdapter(getContext(), uiModels);
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        });


        viewModel.loadGroupedTransactions(
                MockDataProvider.getMockTransactions(),
                MockDataProvider.getMockCategories(),
                MockDataProvider.getMockIcons()
        );

        return view;
    }
}