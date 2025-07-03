package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Home.EditSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddSavingMoneyFragment extends Fragment {

    EditText etAmount;
    TextView tvAddMoney, tvCancel, tvTitle, tvDescription;
    ImageView ivIcon;

    SavingGoal goal;

    SavingGoalListViewModel savingGoalListViewModel;
    EditSavingGoalViewModel editSavingGoalViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_saving_money, container, false);

        savingGoalListViewModel = new ViewModelProvider(requireActivity()).get(SavingGoalListViewModel.class);
        editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);

        etAmount = view.findViewById(R.id.etAmount);
        tvAddMoney = view.findViewById(R.id.tvAddMoney);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivIcon = view.findViewById(R.id.ivIcon);



        // format money
        etAmount.addTextChangedListener(new CurrencyTextWatcher(etAmount));

        // cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        savingGoalListViewModel.selectedSavingGoal.observe(getViewLifecycleOwner(), pair -> {
            if (pair == null) {
                requireActivity().getSupportFragmentManager().popBackStack();
                return;
            }

            goal = pair.first;
            Category category = pair.second;

            // Update UI
            updateUI(goal, category);
        });

        // add money
        tvAddMoney.setOnClickListener(v -> {

            BigDecimal amount = CurrencyUtility.parse(etAmount.getText().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String targetDateStr = goal.getTargetDate() != null ? sdf.format(goal.getTargetDate()) : "";

            editSavingGoalViewModel.updateSavingGoal(
                    goal.getGoalId(),
                    new CreateSavingGoalRequest(
                            goal.getUserId(),
                            goal.getName(),
                            goal.getCurrentAmount().add(amount),
                            goal.getTargetAmount(),
                            goal.getDescription(),
                            "", // start_date
                            targetDateStr,
                            goal.getCategoryId()
                    )
            );
        });

        editSavingGoalViewModel.updateResult.observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    savingGoalListViewModel.fetchSavingGoals();
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                editSavingGoalViewModel.clearUpdateResult();
            }
        });


        return view;
    }


    private void updateUI(SavingGoal goal, Category category) {
        tvTitle.setText(goal.getName());
        tvDescription.setText(goal.getDescription());

        try {
            int colorInt = ContextCompat.getColor(requireContext(), category.getColorRes());
            ivIcon.setBackgroundTintList(ColorStateList.valueOf(colorInt));
            ivIcon.setImageResource(category.getIconRes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}