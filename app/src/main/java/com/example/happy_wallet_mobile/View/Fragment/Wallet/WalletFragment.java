package com.example.happy_wallet_mobile.View.Fragment.Wallet;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.DailyTransactionsRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Fragment.SelectDateRangeFragment;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.Wallet.WalletViewModel;

import java.math.BigDecimal;


public class WalletFragment extends Fragment {

    MainViewModel mainViewModel;
    WalletViewModel walletViewModel;
    FrameLayout flAddIncome, flAddExpenditure;
    RecyclerView rvTransactions;
    TextView tvIncome, tvAvailableBalance, tvExpenses, tvDate;
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
        tvIncome = view.findViewById(R.id.tvIncome);
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
        tvExpenses = view.findViewById(R.id.tvExpenses);
        tvDate = view.findViewById(R.id.tvDate);

        // reset viewmodel data
        walletViewModel.getData();

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

        // set daily transaction data
        walletViewModel.uiModels.observe(getViewLifecycleOwner(), uiModels -> {
            DailyTransactionsRecyclerViewAdapter adapter = new DailyTransactionsRecyclerViewAdapter(getContext(), uiModels);
            rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTransactions.setAdapter(adapter);
        });
        walletViewModel.loadGroupedTransactions();


        // set tvDate
        tvDate.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new SelectDateRangeFragment());
        });

        // set total income
        walletViewModel.totalIncome.observe(getViewLifecycleOwner(), income ->
                tvIncome.setText(CurrencyUtility.format1(income))
        );

        // set total expenses
        walletViewModel.totalExpenses.observe(getViewLifecycleOwner(), expenses ->
                tvExpenses.setText(CurrencyUtility.format1(expenses))
        );

        // set available balance
        walletViewModel.availableBalance.observe(getViewLifecycleOwner(), balance -> {
            tvAvailableBalance.setText(CurrencyUtility.format1(balance));

            int color = ContextCompat.getColor(
                    requireContext(),
                    balance.compareTo(BigDecimal.ZERO) >= 0
                            ? R.color.Paolo_Veronese_Green
                            : R.color.Radishical
            );
            tvAvailableBalance.setTextColor(color);
        });

        return view;
    }
}