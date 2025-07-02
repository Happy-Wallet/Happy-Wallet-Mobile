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

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.SavingGoal.SavingGoalResponse;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.Model.Transaction;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.MonthIAEAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.HomeViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingStatusViewModel;
import com.example.happy_wallet_mobile.Data.MockDataProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    MainViewModel mainViewModel;
    SavingStatusViewModel savingStatusViewModel;
    HomeViewModel homeViewModel;
    CategoryListViewModel categoryListViewModel;
    TextView tvAccountBalance;
    RecyclerView rcvMonthIAE, rcvSavingGoals;
    TextView tvDay, tvMonth, tvYear;
    private SavingGoalRecyclerViewAdapter savingGoalRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        savingStatusViewModel = new ViewModelProvider(requireActivity()).get(SavingStatusViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        SavingGoalListViewModel savingGoalListViewModel = new ViewModelProvider(this).get(SavingGoalListViewModel.class);

        categoryListViewModel.fetchCategories(requireContext());
        savingGoalListViewModel.fetchSavingGoals();

        tvAccountBalance = view.findViewById(R.id.tvAccountBalance);
        rcvMonthIAE = view.findViewById(R.id.rcvMonthIAE);
        rcvSavingGoals = view.findViewById(R.id.rcvSavingGoals);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);

        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvSavingGoals.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));


        tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));





        // set data for account balance
        homeViewModel.getTotalBalance().observe(getViewLifecycleOwner(), totalBalance -> {
            if (totalBalance != null) {
                tvAccountBalance.setText(CurrencyUtility.format(totalBalance));
                if (totalBalance.compareTo(BigDecimal.ZERO) < 0) {
                    tvAccountBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Radishical));
                } else {
                    tvAccountBalance.setTextColor(ContextCompat.getColor(requireContext(), R.color.Paolo_Veronese_Green));
                }
            } else {
                tvAccountBalance.setText("0");
            }

        });


        // set data for rcvSavingGoal
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvSavingGoals.setLayoutManager(layoutManager);
        SavingGoalRecyclerViewAdapter savingGoalRecyclerViewAdapter = new SavingGoalRecyclerViewAdapter(
                requireContext(),
                List.of(),
                List.of());

        rcvSavingGoals.setAdapter(savingGoalRecyclerViewAdapter);




        // Observe dữ liệu từ ViewModel
        savingGoalListViewModel.savingGoals.observe(getViewLifecycleOwner(), savingGoals -> {
            Log.d("HomeFragment", "savingGoals: " + savingGoals);
            savingGoalRecyclerViewAdapter.updateSavingGoals(savingGoals);
        });
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            Log.d("HomeFragment", "categories: " + categories);
            savingGoalRecyclerViewAdapter.updateCategories(categories);
        });


        //Item saving goal click
        savingGoalRecyclerViewAdapter.setOnItemClickListener((savingGoal, category) -> {
            Log.d("HomeFragment", "rcvSavingGoals item click");

            savingStatusViewModel.setSavingGoal(savingGoal);
            savingStatusViewModel.setCategory(category);
            mainViewModel.navigateSubBelow(new SavingStatusFragment());
        });


        //AddSavingGoal click
        savingGoalRecyclerViewAdapter.setOnAddClickListener(() -> {
            Log.d("HomeFragment", "rcvSavingGoals add new saving goal click");
            mainViewModel.navigateSubBelow(new AddSavingGoalFragment());
        });

        //tvDay click
        tvDay.setOnClickListener(v ->  {
            Log.d("HomeFragment", "tvDay click");
            tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
            tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
        });

        //tvMonth click
        tvMonth.setOnClickListener(v ->  {
            Log.d("HomeFragment", "tvMonth click");
            tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
            tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
        });

        //tvYear click
        tvYear.setOnClickListener(v ->  {
            Log.d("HomeFragment", "tvYear click");
            tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
        });

        // set data cho biểu đồ IAE (income and expense) từng tháng
        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        MonthIAEAdapter monthIAEAdapter = new MonthIAEAdapter(List.of());
        rcvMonthIAE.setAdapter(monthIAEAdapter);
        // observe data từ viewmodel
        homeViewModel.getMonthlyData().observe(getViewLifecycleOwner(), monthlyData -> {
            monthIAEAdapter.update(monthlyData);
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout spendingLayout = view.findViewById(R.id.clSpendingAnalisisBG);
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View chartView = inflater.inflate(R.layout.item_pie_chart, spendingLayout, false);
        PieChart pieChart = chartView.findViewById(R.id.pieChart);
        spendingLayout.addView(chartView);

        List<PieEntry> entries = getSpendingPieEntries("month");
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);
        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);

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

        pieChart.invalidate(); // cập nhật lại biểu đồ



        setupTimeFilter(view, pieChart);
    }
    private List<PieEntry> getSpendingPieEntries(String timeFilter) {
        List<Transaction> allTransactions = MockDataProvider.getMockTransactions();
        Map<String, Float> spendingByCategory = new HashMap<>();

        for (Transaction t : allTransactions) {
            if (t.getType() == eType.EXPENSE && matchesFilter(t.getDate(), timeFilter)) {
                Category category = findCategoryById(t.getCategoryId());
                if (category == null) continue;

                String categoryName = category.getName();
                float current = spendingByCategory.getOrDefault(categoryName, 0f);
                spendingByCategory.put(categoryName, current + t.getAmount().abs().floatValue());
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : spendingByCategory.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        return entries;
    }
    private Category findCategoryById(int categoryId) {
        for (Category c : MockDataProvider.getMockCategories()) {
            if (c.getCategoryId() == categoryId) return c;
        }
        return null;
    }

    private boolean matchesFilter(Date date, String filter) {
        Calendar now = Calendar.getInstance();
        Calendar transactionDate = Calendar.getInstance();
        transactionDate.setTime(date);

        switch (filter.toLowerCase()) {
            case "day":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
                        now.get(Calendar.DAY_OF_YEAR) == transactionDate.get(Calendar.DAY_OF_YEAR);
            case "month":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR) &&
                        now.get(Calendar.MONTH) == transactionDate.get(Calendar.MONTH);
            case "year":
                return now.get(Calendar.YEAR) == transactionDate.get(Calendar.YEAR);
            default:
                return true;
        }
    }
    private void setupTimeFilter(View view, PieChart pieChart) {
        TextView tvDay = view.findViewById(R.id.tvDay);
        TextView tvMonth = view.findViewById(R.id.tvMonth);
        TextView tvYear = view.findViewById(R.id.tvYear);

        View.OnClickListener filterClick = v -> {
            String filter;
            if (v.getId() == R.id.tvDay) {
                filter = "day";
                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            } else if (v.getId() == R.id.tvMonth) {
                filter = "month";
                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            } else if (v.getId() == R.id.tvYear) {
                filter = "year";
                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
            } else return;

            List<PieEntry> entries = getSpendingPieEntries(filter);
            PieDataSet newDataSet = new PieDataSet(entries, "");
            newDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            newDataSet.setValueTextSize(14f);
            newDataSet.setDrawValues(true);

            pieChart.setData(new PieData(newDataSet));
            pieChart.invalidate();
        };


        tvDay.setOnClickListener(filterClick);
        tvMonth.setOnClickListener(filterClick);
        tvYear.setOnClickListener(filterClick);
    }

}