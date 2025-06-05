package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

public class HomeFragment extends Fragment {

    FrameLayout flNotification;
    TextView tvAccountBalance;
    RecyclerView rcvMonthIAE, rcvSavingGoals;
    TextView tvDay, tvMonth, tvYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        flNotification = view.findViewById(R.id.flNotification);
        tvAccountBalance = view.findViewById(R.id.tvAccountBalance);
        rcvMonthIAE = view.findViewById(R.id.rcvMonthIAE);
        rcvSavingGoals = view.findViewById(R.id.rcvSavingGoals);
        tvDay = view.findViewById(R.id.tvDay);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);

        rcvMonthIAE.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvSavingGoals.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));




        flNotification.setOnDragListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //tvDay click
        tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));

            }
        });

        //tvMonth click
        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
            }
        });

        //tvYear click
        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvDay.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvMonth.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_white));
                tvYear.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_rounded_20_paolo_veronese_green));
            }
        });

        return view;
    }
}