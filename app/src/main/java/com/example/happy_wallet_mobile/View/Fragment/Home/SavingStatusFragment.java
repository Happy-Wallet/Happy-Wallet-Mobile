package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Home.EditSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;
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
    TextView tvCancel, tvTitle, tvDescription, tvCurrentAmount, tvAddMoney;
    ImageView ivIcon, ivEditSavingGoal;
    ProgressBar pbProgress;

    SavingGoalListViewModel savingGoalListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saving_status, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        savingGoalListViewModel = new ViewModelProvider(requireActivity()).get(SavingGoalListViewModel.class);
        editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);

        ivIcon = view.findViewById(R.id.ivIcon);
        ivEditSavingGoal = view.findViewById(R.id.ivEditSavingGoal);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        pbProgress = view.findViewById(R.id.pbProgress);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCurrentAmount = view.findViewById(R.id.tvCurrentAmount);
        tvAddMoney = view.findViewById(R.id.tvAddMoney);

        savingGoalListViewModel.selectedSavingGoal.observe(getViewLifecycleOwner(), pair -> {
            if (pair == null) {
                requireActivity().getSupportFragmentManager().popBackStack();
                return;
            }

            SavingGoal goal = pair.first;
            Category category = pair.second;

            // Update UI
            updateUI(goal, category);
        });


        ivEditSavingGoal.setOnClickListener(v -> {
            Log.d("SavingStatusFragment", "ivEditSavingStatus item click");
            EditSavingGoalViewModel editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);
            editSavingGoalViewModel.setCategory(savingGoalListViewModel.selectedSavingGoal.getValue().second);
            editSavingGoalViewModel.setSavingGoal(savingGoalListViewModel.selectedSavingGoal.getValue().first);
            mainViewModel.navigateSubBelow(new EditSavingGoalFragment());
        });

        editSavingGoalViewModel.updateResult.observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                if (success) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        // cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // add money
        tvAddMoney.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new AddSavingMoneyFragment());
        });

        return view;
    }

    private void updateUI(SavingGoal goal, Category category) {
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
                    .divide(target, 0, RoundingMode.HALF_UP)
                    .intValue();
        }
        pbProgress.setProgress(progress);

        try {
            int colorInt = ContextCompat.getColor(requireContext(), category.getColorRes());
            ivIcon.setBackgroundTintList(ColorStateList.valueOf(colorInt));
            ivIcon.setImageResource(category.getIconRes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
