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

    List<DailyTransactionList> dailyTransactionListItemList;
    RecyclerView rvTransactions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        rvTransactions = view.findViewById(R.id.rvTransactions);

        dailyTransactionListItemList = new ArrayList<>();

        // Ngày 26/5/2025
        List<Transaction> day1Transactions = Arrays.asList(
                new Transaction("Eating", "150", "26/5/2025", "Food", "Bun bo cham nuoc mam siêu ngonnnnnnnn", "expenditure"),
                new Transaction("Salary", "150", "26/5/2025", "Income", "Cong ty nay tra luong minh", "income"),
                new Transaction("Saving for Cars", "5", "26/5/2025", "Saving", "sieu xe sieu nhanh sieu manh", "expenditure")
        );
        dailyTransactionListItemList.add(new DailyTransactionList("26/5/2025", "-5$", day1Transactions));
        List<Transaction> day2Transactions = Arrays.asList(
                new Transaction("Eating", "150", "25/5/2025", "Food", "my goi thoi ma tan 150 nghin", "expenditure"),
                new Transaction("Eating", "150", "25/5/2025", "Food", "skibidi yeat yeat", "expenditure"),
                new Transaction("Eating", "150", "25/5/2025", "Food", "Mi cay han quoc sieu ngon sieu cay ba chay bo chet", "expenditure")
        );
        dailyTransactionListItemList.add(new DailyTransactionList("25/5/2025", "-450$", day2Transactions));

        DailyTransactionsRecyclerViewAdapter adapter = new DailyTransactionsRecyclerViewAdapter(dailyTransactionListItemList);
        rvTransactions.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTransactions.setAdapter(adapter);
        return view;
    }
}