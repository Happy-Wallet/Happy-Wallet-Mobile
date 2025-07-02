package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.MonthIAEAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.HomeViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private MainViewModel mainViewModel;
    SavingGoalListViewModel savingGoalListViewModel;
    private HomeViewModel homeViewModel;
    private CategoryListViewModel categoryListViewModel;
    private SavingGoalRecyclerViewAdapter savingGoalRecyclerViewAdapter;
    private PieChart pieChart;
    private String currentFilter = "month";

    private TextView tvDay, tvMonth, tvYear, tvAccountBalance;
    private RecyclerView rcvMonthIAE, rcvSavingGoals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Init ViewModels
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        savingGoalListViewModel = new ViewModelProvider(requireActivity()).get(SavingGoalListViewModel.class);

        // Fetch initial data
        homeViewModel.fetchTransactions();
        categoryListViewModel.fetchCategories(requireContext());
        savingGoalListViewModel.fetchSavingGoals();

        // Setup views
        tvAccountBalance = view.findViewById(R.id.tvAccountBalance);
        rcvMonthIAE = view.findViewById(R.id.rcvMonthIAE);
        rcvSavingGoals = view.findViewById(R.id.rcvSavingGoals);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);
        pieChart = view.findViewById(R.id.pieChart);

        tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));

        setupPieChart();

        // Setup adapters
        savingGoalRecyclerViewAdapter = new SavingGoalRecyclerViewAdapter(requireContext(), List.of(), List.of());
        rcvSavingGoals.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvSavingGoals.setAdapter(savingGoalRecyclerViewAdapter);

        MonthIAEAdapter monthIAEAdapter = new MonthIAEAdapter(List.of());
        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvMonthIAE.setAdapter(monthIAEAdapter);

        // Observers
        homeViewModel.getTotalBalance().observe(getViewLifecycleOwner(), this::updateAccountBalance);
        homeViewModel.getMonthlyData().observe(getViewLifecycleOwner(), monthIAEAdapter::update);
        homeViewModel.getTransactionList().observe(getViewLifecycleOwner(), transactions -> updatePieChart(transactions, currentFilter));
        savingGoalListViewModel.savingGoals.observe(getViewLifecycleOwner(), savingGoalRecyclerViewAdapter::updateSavingGoals);
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), savingGoalRecyclerViewAdapter::updateCategories);

        // Events
        setupSavingGoalClick();
        setupTimeFilter(view);

        return view;
    }

    private void updateAccountBalance(BigDecimal totalBalance) {
        if (totalBalance != null) {
            tvAccountBalance.setText(CurrencyUtility.format(totalBalance));
            int colorRes = totalBalance.compareTo(BigDecimal.ZERO) < 0 ? R.color.Radishical : R.color.Paolo_Veronese_Green;
            tvAccountBalance.setTextColor(ContextCompat.getColor(requireContext(), colorRes));
        } else {
            tvAccountBalance.setText("0");
        }
    }

    private void setupSavingGoalClick() {
        savingGoalRecyclerViewAdapter.setOnItemClickListener((savingGoal, category) -> {
            savingGoalListViewModel.selectSavingGoal(savingGoal, category);
            mainViewModel.navigateSubBelow(new SavingStatusFragment());
        });

        savingGoalRecyclerViewAdapter.setOnAddClickListener(() -> {
            mainViewModel.navigateSubBelow(new AddSavingGoalFragment());
        });
    }


    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawCenterText(false);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12f);

        // Thêm spacing để legend thoáng hơn
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
        legend.setFormSize(12f);
        legend.setForm(Legend.LegendForm.CIRCLE);
    }


    private void updatePieChart(List<Transaction> transactions, String timeFilter) {
        if (transactions == null) return;

        Map<String, Float> spendingByCategory = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getType() == eType.EXPENSE && matchesFilter(t.getDate(), timeFilter)) {
                Category category = findCategoryById(t.getCategoryId());
                if (category == null) continue;

                String categoryName = category.getName();
                spendingByCategory.put(categoryName,
                        spendingByCategory.getOrDefault(categoryName, 0f) + t.getAmount().abs().floatValue());
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : spendingByCategory.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();
    }

    private boolean matchesFilter(java.util.Date date, String filter) {
        Calendar now = Calendar.getInstance();
        Calendar transactionDate = Calendar.getInstance();
        transactionDate.setTime(date);

        switch (filter.toLowerCase()) {
            case "day":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR)
                        && now.get(Calendar.DAY_OF_YEAR) == transactionDate.get(Calendar.DAY_OF_YEAR);
            case "month":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR)
                        && now.get(Calendar.MONTH) == transactionDate.get(Calendar.MONTH);
            case "year":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR);
            default:
                return true;
        }
    }

    private Category findCategoryById(int categoryId) {
        List<Category> categories = categoryListViewModel.getCategoryList().getValue();
        if (categories != null) {
            for (Category c : categories) {
                if (c.getCategoryId() == categoryId) return c;
            }
        }
        return null;
    }

    private void setupTimeFilter(View view) {
        View.OnClickListener filterClick = v -> {
            if (v.getId() == R.id.tvDay) {
                currentFilter = "day";
                highlightFilter(tvDay, tvMonth, tvYear);
            } else if (v.getId() == R.id.tvMonth) {
                currentFilter = "month";
                highlightFilter(tvMonth, tvDay, tvYear);
            } else if (v.getId() == R.id.tvYear) {
                currentFilter = "year";
                highlightFilter(tvYear, tvDay, tvMonth);
            }

            updatePieChart(homeViewModel.getTransactionList().getValue(), currentFilter);
        };

        tvDay.setOnClickListener(filterClick);
        tvMonth.setOnClickListener(filterClick);
        tvYear.setOnClickListener(filterClick);
    }

    private void highlightFilter(TextView selected, TextView unselected1, TextView unselected2) {
        selected.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
        unselected1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
        unselected2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
    }
}
