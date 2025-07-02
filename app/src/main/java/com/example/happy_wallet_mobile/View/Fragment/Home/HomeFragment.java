package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.MonthIAEAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.HomeViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingStatusViewModel;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.*;

public class HomeFragment extends Fragment {

    private MainViewModel mainViewModel;
    private SavingStatusViewModel savingStatusViewModel;
    private HomeViewModel homeViewModel;
    private CategoryListViewModel categoryListViewModel;

    private PieChart pieChart;

    private TextView tvDay, tvMonth, tvYear;
    private String currentFilter = "month";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        savingStatusViewModel = new ViewModelProvider(requireActivity()).get(SavingStatusViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);

        categoryListViewModel.fetchCategories(requireContext());

        setupAccountBalance(view);
        setupSavingGoals(view);
        setupMonthIAE(view);
        setupPieChart(view);
        setupTimeFilter(view);

        homeViewModel.setData(requireContext()); // load dữ liệu

        return view;
    }

    private void setupAccountBalance(View view) {
        TextView tvAccountBalance = view.findViewById(R.id.tvAccountBalance);
        homeViewModel.getTotalBalance().observe(getViewLifecycleOwner(), totalBalance -> {
            if (totalBalance != null) {
                tvAccountBalance.setText(CurrencyUtility.format(totalBalance));
                tvAccountBalance.setTextColor(ContextCompat.getColor(requireContext(),
                        totalBalance.compareTo(BigDecimal.ZERO) < 0 ?
                                R.color.Radishical : R.color.Paolo_Veronese_Green));
            } else {
                tvAccountBalance.setText("0");
            }
        });
    }

    private void setupSavingGoals(View view) {
        RecyclerView rcvSavingGoals = view.findViewById(R.id.rcvSavingGoals);
        rcvSavingGoals.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        SavingGoalRecyclerViewAdapter adapter = new SavingGoalRecyclerViewAdapter(requireContext(), List.of(), List.of());
        rcvSavingGoals.setAdapter(adapter);

        homeViewModel.getSavingGoalList().observe(getViewLifecycleOwner(), adapter::updateSavingGoals);
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), adapter::updateCategories);

        adapter.setOnItemClickListener((savingGoal, category) -> {
            savingStatusViewModel.setSavingGoal(savingGoal);
            savingStatusViewModel.setCategory(category);
            mainViewModel.navigateSubBelow(new SavingStatusFragment());
        });

        adapter.setOnAddClickListener(() -> mainViewModel.navigateSubBelow(new AddSavingGoalFragment()));
    }

    private void setupMonthIAE(View view) {
        RecyclerView rcvMonthIAE = view.findViewById(R.id.rcvMonthIAE);
        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        MonthIAEAdapter adapter = new MonthIAEAdapter(List.of());
        rcvMonthIAE.setAdapter(adapter);

        homeViewModel.getMonthlyData().observe(getViewLifecycleOwner(), adapter::update);
    }

    private void setupPieChart(View view) {
        ConstraintLayout spendingLayout = view.findViewById(R.id.clSpendingAnalisisBG);
        View chartView = LayoutInflater.from(requireContext()).inflate(R.layout.item_pie_chart, spendingLayout, false);
        pieChart = chartView.findViewById(R.id.pieChart);
        spendingLayout.addView(chartView);

        configurePieChart();

        homeViewModel.getTransactionList().observe(getViewLifecycleOwner(), transactions -> {
            updatePieChart(currentFilter);
        });
    }

    private void configurePieChart() {
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
    }

    private void setupTimeFilter(View view) {
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);

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
            updatePieChart(currentFilter);
        };

        tvDay.setOnClickListener(filterClick);
        tvMonth.setOnClickListener(filterClick);
        tvYear.setOnClickListener(filterClick);

        // default chọn tháng
        highlightFilter(tvMonth, tvDay, tvYear);
    }

    private void highlightFilter(TextView active, TextView... others) {
        active.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
        for (TextView other : others) {
            other.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
        }
    }

    private void updatePieChart(String filter) {
//        List<Transaction> transactions = MockDataProvider.getMockTransactions();
//        List<Category> categories = MockDataProvider.getMockCategories();
        List<Transaction> transactions = homeViewModel.getTransactionList().getValue();
        List<Category> categories = categoryListViewModel.getCategoryList().getValue();
        if (transactions == null || categories == null) return;

        Map<String, Float> spendingByCategory = new LinkedHashMap<>();
        Map<String, Integer> categoryColorMap = new HashMap<>(); // map category name -> color

        for (Transaction t : transactions) {
            if (t.getType() == eType.EXPENSE && matchesFilter(t.getDate(), filter)) {
                Category category = findCategoryById(categories, t.getCategoryId());
                if (category == null) continue;

                String categoryName = category.getName();
                spendingByCategory.merge(categoryName, t.getAmount().abs().floatValue(), Float::sum);

                // chỉ lưu màu 1 lần
                categoryColorMap.putIfAbsent(categoryName,
                        ContextCompat.getColor(requireContext(), category.getColorRes()));
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        for (Map.Entry<String, Float> entry : spendingByCategory.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
            colors.add(categoryColorMap.get(entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(14f);

        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();
    }

    private Category findCategoryById(List<Category> categories, int id) {
        for (Category c : categories) {
            if (c.getCategoryId() == id) {
                Log.d("PieChart", "Found category id=" + id + " name=" + c.getName());
                return c;
            }
        }
        return null;
    }

    private boolean matchesFilter(Date date, String filter) {
        Calendar now = Calendar.getInstance();
        Calendar trans = Calendar.getInstance();
        trans.setTime(date);

        switch (filter) {
            case "day":
                return now.get(Calendar.YEAR) == trans.get(Calendar.YEAR)
                        && now.get(Calendar.DAY_OF_YEAR) == trans.get(Calendar.DAY_OF_YEAR);
            case "month":
                return now.get(Calendar.YEAR) == trans.get(Calendar.YEAR)
                        && now.get(Calendar.MONTH) == trans.get(Calendar.MONTH);
            case "year":
                return now.get(Calendar.YEAR) == trans.get(Calendar.YEAR);
            default:
                return true;
        }
    }

}
