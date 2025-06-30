package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

import java.util.Arrays;
import java.util.List;


public class SelectDateRangeFragment extends Fragment {

    ListView lvDateRange;
    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_date_range, container, false);

        lvDateRange = view.findViewById(R.id.lvDateRange);
        tvCancel = view.findViewById(R.id.tvCancel);

        // set lvDateRange data
        List<String> dataRanges = Arrays.asList(
                "Today",
                "Yesterday",
                "This week",
                "Last week",
                "This month",
                "Last month",
                "This year",
                "Last year",
                "Last 10 days",
                "Last 30 days",
                "Last 90 days"
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                dataRanges
        );
        lvDateRange.setAdapter(adapter);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}