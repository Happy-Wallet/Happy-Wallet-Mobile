package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Home.EditSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingStatusViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;


public class SavingStatusFragment extends Fragment {

    MainViewModel mainViewModel;
    SavingStatusViewModel savingStatusViewModel;
    EditSavingGoalViewModel editSavingGoalViewModel;

    private SavingGoal savingGoal;
    private Category category;
    TextView tvCancel, tvTitle, tvDescription, tvCurrentAmount;
    ImageView ivIcon, ivEditSavingGoal;
    ProgressBar pbProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving_status, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        savingStatusViewModel = new ViewModelProvider(requireActivity()).get(SavingStatusViewModel.class);
        editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);

        ivIcon = view.findViewById(R.id.ivIcon);
        ivEditSavingGoal = view.findViewById(R.id.ivEditSavingGoal);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        pbProgress = view.findViewById(R.id.pbProgress);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCurrentAmount = view.findViewById(R.id.tvCurrentAmount);


        savingStatusViewModel.savingGoal.observe(getViewLifecycleOwner(), goal -> {
            if (goal != null) {
                tvTitle.setText(goal.getName());
                tvDescription.setText(goal.getDescription());

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String currentStr = currencyFormat.format(goal.getCurrentAmount());
                String targetStr = currencyFormat.format(goal.getTargetAmount());

                tvCurrentAmount.setText(currentStr + " / " + targetStr);

                BigDecimal current = goal.getCurrentAmount();
                BigDecimal target = goal.getTargetAmount();
                int progress = 0;

                if (target != null && target.compareTo(BigDecimal.ZERO) > 0) {
                    progress = current.multiply(BigDecimal.valueOf(100))
                            .divide(target, RoundingMode.HALF_UP)
                            .intValue();
                }
                pbProgress.setProgress(progress);
            }
        });

        savingStatusViewModel.category.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                try {
                    ivIcon.setBackgroundTintList(ColorStateList.valueOf(category.getColorRes()));
                    ivIcon.setImageResource(category.getIconRes());
                } catch (Exception ignored) {}
            }
        });

        // edit saving status
        ivEditSavingGoal.setOnClickListener(v -> {
            Log.d("SavingStatusFragment", "ivEditSavingStatus item click");

            editSavingGoalViewModel.setCategory(savingStatusViewModel.category.getValue());
            editSavingGoalViewModel.setSavingGoal(savingStatusViewModel.savingGoal.getValue());
            mainViewModel.navigateSubBelow(new EditSavingGoalFragment());
        });

        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
