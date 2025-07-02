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
import com.example.happy_wallet_mobile.ViewModel.Wallet.AddExpenditureViewModel;
import com.example.happy_wallet_mobile.ViewModel.Wallet.WalletViewModel;

import java.math.BigDecimal;
import java.util.List;


public class WalletFragment extends Fragment {

    MainViewModel mainViewModel;
    AddExpenditureViewModel addExpenditureViewModel;
    WalletViewModel walletViewModel;
    FrameLayout flAddIncome, flAddExpenditure;
    RecyclerView rvTransactions;
    TextView tvIncome, tvAvailableBalance, tvExpenses, tvDate;
    DailyTransactionsRecyclerViewAdapter dailyTransactionsRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        addExpenditureViewModel = new ViewModelProvider(requireActivity()).get(AddExpenditureViewModel.class);

        rvTransactions = view.findViewById(R.id.rvTransactions);
        flAddIncome = view.findViewById(R.id.flAddIncome);
        flAddExpenditure = view.findViewById(R.id.flAddExpenditure);
        tvIncome = view.findViewById(R.id.tvIncome);
        tvAvailableBalance = view.findViewById(R.id.tvAvailableBalance);
        tvExpenses = view.findViewById(R.id.tvExpenses);
        tvDate = view.findViewById(R.id.tvDate);

        dailyTransactionsRecyclerViewAdapter = new DailyTransactionsRecyclerViewAdapter(getContext(), List.of());

        setupRecyclerView();
        setupObservers();

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

        // set tvDate
        tvDate.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new SelectDateRangeFragment());
        });

        // fetch từ server
        walletViewModel.fetchTransactions();

        return view;
    }

    private void setupRecyclerView() {
        rvTransactions.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransactions.setAdapter(dailyTransactionsRecyclerViewAdapter);
    }

    private void setupObservers() {
        walletViewModel.uiModels.observe(getViewLifecycleOwner(), uiModels -> {
            dailyTransactionsRecyclerViewAdapter.updateData(uiModels); // nếu bạn dùng custom ListAdapter thì đổi thành submitList
        });

        // set total income
        walletViewModel.totalIncome.observe(getViewLifecycleOwner(), income ->
                tvIncome.setText(CurrencyUtility.format1(income))
        );

        // set total expense
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

        addExpenditureViewModel.getCreateTransactionResponse().observe(getViewLifecycleOwner(), response ->{
            if (response != null){
                walletViewModel.fetchTransactions();
            }
        });

    }
}