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

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Request.SavingGoal.CreateSavingGoalRequest;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.EditSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditSavingGoalFragment extends Fragment {

    MainViewModel mainViewModel;
    EditSavingGoalViewModel editSavingGoalViewModel;
    CategoryListViewModel categoryListViewModel;
    SavingGoalListViewModel savingGoalListViewModel;


    private SavingGoal savingGoal;
    private Category selectedCategory;

    TextView tvCancel, tvSave, tvDelete, tvDate;
    EditText etTitle, etDescription, etTarget;
    RecyclerView rcvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_saving_goal, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        editSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(EditSavingGoalViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        savingGoalListViewModel = new ViewModelProvider(requireActivity()).get(SavingGoalListViewModel.class);

        tvCancel = view.findViewById(R.id.tvCancel);
        tvSave = view.findViewById(R.id.tvSave);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvDate = view.findViewById(R.id.tvDate);
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etTarget = view.findViewById(R.id.etTarget);
        rcvCategories = view.findViewById(R.id.rcvCategories);

        etTarget.addTextChangedListener(new CurrencyTextWatcher(etTarget));
        tvDate.setOnClickListener(v -> {
            showDatePicker();
        });

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);

        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(
                requireContext(), List.of(), category -> {
            selectedCategory = category;
            editSavingGoalViewModel.setCategory(category);
        }
        );
        adapter.setOnAddClickListener(() -> Toast.makeText(getContext(), "Nhấn Add More", Toast.LENGTH_SHORT).show());
        rcvCategories.setAdapter(adapter);

        // Load data vào UI
        editSavingGoalViewModel.savingGoal.observe(getViewLifecycleOwner(), goal -> {
            if (goal != null) {
                this.savingGoal = goal;
                etTitle.setText(goal.getName());
                etDescription.setText(goal.getDescription());
                etTarget.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(goal.getTargetAmount()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                tvDate.setText(sdf.format(goal.getTargetDate()));

            }
        });

        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            adapter.updateCategories(categories);
            if (selectedCategory != null) {
                adapter.setSelectedCategory(selectedCategory.getCategoryId());
            } else if (savingGoal != null) {
                adapter.setSelectedCategory(savingGoal.getCategoryId());
            }
        });

        editSavingGoalViewModel.category.observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                selectedCategory = category;
                adapter.setSelectedCategory(category.getCategoryId());
            }
        });


        // Quan sát kết quả update / delete
        editSavingGoalViewModel.updateResult.observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    savingGoalListViewModel.fetchSavingGoals();
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
                editSavingGoalViewModel.clearUpdateResult();
            }
        });

        editSavingGoalViewModel.deleteResult.observe(getViewLifecycleOwner(), success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                    savingGoalListViewModel.fetchSavingGoals();
                    savingGoalListViewModel.clearSelectedSavingGoal();
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
                editSavingGoalViewModel.clearDeleteResult();
            }
        });


        // Xử lý nút
        tvSave.setOnClickListener(v -> {
            if (savingGoal == null || selectedCategory == null) {
                Toast.makeText(getContext(), "Vui lòng chọn category", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal targetAmount = etTarget.getText().toString().isEmpty() ? BigDecimal.ZERO : CurrencyUtility.parse(etTarget.getText().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String targetDateString = savingGoal.getTargetDate() != null ? sdf.format(savingGoal.getTargetDate()) : "";

            editSavingGoalViewModel.updateSavingGoal(
                    savingGoal.getGoalId(),
                    new CreateSavingGoalRequest(
                            savingGoal.getUserId(),
                            etTitle.getText().toString(),
                            savingGoal.getCurrentAmount(),
                            targetAmount,
                            etDescription.getText().toString(),
                            "", // start_date
                            targetDateString,
                            selectedCategory.getCategoryId()
                    )
            );
        });

        tvDelete.setOnClickListener(v -> {
            if (savingGoal != null) {
                editSavingGoalViewModel.deleteSavingGoal(savingGoal.getGoalId());
            }
        });


        tvCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void showDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày sinh");
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        final MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            tvDate.setText(sdf.format(calendar.getTime()));
        });
    }
}
