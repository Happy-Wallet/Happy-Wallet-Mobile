package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

public class TransactionDetailFragment extends Fragment {

    FrameLayout flIconBackground;
    ImageView ivIcon;
    TextView tvTitle, tvDate, tvDescription, tvAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_detail, container, false);

        flIconBackground = view.findViewById(R.id.flIconBackground);
        ivIcon = view.findViewById(R.id.ivIcon);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDate = view.findViewById(R.id.tvDate);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvAmount = view.findViewById(R.id.tvAmount);

        return view;
    }
}