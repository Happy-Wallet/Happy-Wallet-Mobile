package com.example.happy_wallet_mobile.View.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Fragment.CategoryListFragment;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.ViewModel.Home.EditSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class EditSavingGoalFragment extends Fragment {

    MainViewModel mainViewModel;
    EditSavingGoalViewModel editSavingGoalViewModel;

    private SavingGoal savingGoal;
    private Category category;

    TextView tvCancel, tvDelete, tvSave, tvDate;
    EditText etTitle, etDescription, etTarget;
    RecyclerView rcvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_saving_goal, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);

        tvCancel = view.findViewById(R.id.tvCancel);
        tvSave = view.findViewById(R.id.tvSave);
        tvDate = view.findViewById(R.id.tvDate);
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etTarget = view.findViewById(R.id.etTarget);
        rcvCategories = view.findViewById(R.id.rcvCategories);

        // Quan sát SavingGoal từ ViewModel
        editSavingGoalViewModel.savingGoal.observe(getViewLifecycleOwner(), goal -> {
            if (goal != null) {
                tvDate.setText(goal.getTargetDate().toString());
                etTitle.setText(goal.getName());
                etDescription.setText(goal.getDescription());
                etTarget.setText(NumberFormat
                        .getCurrencyInstance(new Locale("vi", "VN"))
                        .format(goal.getTargetAmount()));
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                requireContext(),
                List.of(),
                category -> {
                    Toast.makeText(getContext(), "Bạn chọn: " + category.getName(), Toast.LENGTH_SHORT).show();
                });
        categoryRecyclerViewAdapter.setOnAddClickListener(() -> {
            Toast.makeText(getContext(), "Bạn đã nhấn Add More", Toast.LENGTH_SHORT).show();
            mainViewModel.navigateSubBelow(new CategoryListFragment());
        });
        rcvCategories.setAdapter(categoryRecyclerViewAdapter);
        // Observe dữ liệu từ ViewModel
        editSavingGoalViewModel.categoryList.observe(getViewLifecycleOwner(), categories -> {
            categoryRecyclerViewAdapter.updateCategories(categories);
        });

        // cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        etTarget.addTextChangedListener(new CurrencyTextWatcher(etTarget));

        return view;



    }
}