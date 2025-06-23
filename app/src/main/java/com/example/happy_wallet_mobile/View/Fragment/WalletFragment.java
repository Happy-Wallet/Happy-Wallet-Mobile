package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.DailyTransactionsRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.DailyTransactionList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalletFragment extends Fragment {

    List<DailyTransactionList> dailyTransactionListItemList = new ArrayList<DailyTransactionList>();
    RecyclerView rvTransactions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        rvTransactions = view.findViewById(R.id.rvTransactions);


        DailyTransactionsRecyclerViewAdapter adapter = new DailyTransactionsRecyclerViewAdapter(dailyTransactionListItemList);
        rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTransactions.setAdapter(adapter);
        return view;
    }
}