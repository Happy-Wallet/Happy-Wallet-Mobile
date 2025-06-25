package com.example.happy_wallet_mobile.View.Fragment;

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
import android.widget.TextView;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.HomeViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.SavingStatusViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    MainViewModel mainViewModel;
    SavingStatusViewModel savingStatusViewModel;
    HomeViewModel homeViewModel;
    TextView tvAccountBalance;
    RecyclerView rcvMonthIAE, rcvSavingGoals;
    TextView tvDay, tvMonth, tvYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        savingStatusViewModel = new ViewModelProvider(requireActivity()).get(SavingStatusViewModel.class);

        tvAccountBalance = view.findViewById(R.id.tvAccountBalance);
        rcvMonthIAE = view.findViewById(R.id.rcvMonthIAE);
        rcvSavingGoals = view.findViewById(R.id.rcvSavingGoals);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);

        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvSavingGoals.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));



        // set data for rcvSavingGoal
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvSavingGoals.setLayoutManager(layoutManager);
        SavingGoalRecyclerViewAdapter savingGoalRecyclerViewAdapter = new SavingGoalRecyclerViewAdapter(
                requireContext(),
                List.of(),
                List.of(),
                List.of());

        rcvSavingGoals.setAdapter(savingGoalRecyclerViewAdapter);
        // Observe dữ liệu từ ViewModel
        homeViewModel.savingGoalList.observe(getViewLifecycleOwner(), savingGoals -> {
            savingGoalRecyclerViewAdapter.updateSavingGoals(savingGoals);
        });
        homeViewModel.categoryList.observe(getViewLifecycleOwner(), categories -> {
            savingGoalRecyclerViewAdapter.updateCategories(categories);
        });
        homeViewModel.iconList.observe(getViewLifecycleOwner(), icons -> {
            savingGoalRecyclerViewAdapter.updateIcons(icons);
        });


        //Item saving goal click
        savingGoalRecyclerViewAdapter.setOnItemClickListener((savingGoal, category, icon) -> {
            Log.d("HomeFragment", "rcvSavingGoals item click");

            savingStatusViewModel.setSavingGoal(savingGoal);
            savingStatusViewModel.setCategory(category);
            savingStatusViewModel.setIcon(icon);
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



        return view;
    }
}