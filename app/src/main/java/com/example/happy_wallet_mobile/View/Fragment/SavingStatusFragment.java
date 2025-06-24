package com.example.happy_wallet_mobile.View.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;


public class SavingStatusFragment extends Fragment {

    private SavingGoal savingGoal;
    private Category category;
    private Icon icon;
    TextView tvCancel, tvTitle, tvDescription, tvCurrentAmount;
    ImageView ivIcon;
    ProgressBar pbProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving_status, container, false);

        ivIcon = view.findViewById(R.id.ivIcon);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        pbProgress = view.findViewById(R.id.pbProgress);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCurrentAmount = view.findViewById(R.id.tvCurrentAmount);

        if (getArguments() != null) {
            savingGoal = (SavingGoal) getArguments().getSerializable("goal");
            category = (Category) getArguments().getSerializable("category");
            icon = (Icon) getArguments().getSerializable("icon");

            Log.d("SavingStatusFragment", "Received goal: " + savingGoal.getName());

            // set icon
            if (icon != null) {
                int iconResId = requireContext().getResources().getIdentifier(icon.getIconPath(), "drawable", requireContext().getPackageName());
                ivIcon.setImageResource(iconResId);
            }

            // set icon background
            if (category != null) {
                try {
                    int color = Color.parseColor(category.getColorCode());
                    ivIcon.setBackgroundTintList(ColorStateList.valueOf(color));
                } catch (Exception ignored) {}
            }

            // set savingGoal data
            if (savingGoal != null) {
                tvTitle.setText(savingGoal.getName());
                tvDescription.setText(savingGoal.getDescription());

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // hoặc Locale.US
                String currentStr = currencyFormat.format(savingGoal.getCurrentAmount());
                String targetStr = currencyFormat.format(savingGoal.getTargetAmount());

                tvCurrentAmount.setText(currentStr + " / " + targetStr);

                BigDecimal current = savingGoal.getCurrentAmount();
                BigDecimal target = savingGoal.getTargetAmount();
                int progress = current.multiply(BigDecimal.valueOf(100))
                        .divide(target, RoundingMode.HALF_UP)
                        .intValue();
                pbProgress.setProgress(progress);
            }
        }

        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
