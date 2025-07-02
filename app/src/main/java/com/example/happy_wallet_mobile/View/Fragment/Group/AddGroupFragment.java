package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.Remote.Request.Group.CreateGroupRequest;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddGroupFragment extends Fragment {

    private EditText etName, etDescription, etTargetAmount, etTargetEndDate;
    private TextView tvCancel, tvSave;
    private CheckBox cbHasTarget;
    private FrameLayout flTargetAmountLayout, flTargetDateLayout;

    private GroupsViewModel groupsViewModel;
    private MainViewModel mainViewModel;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        etName = view.findViewById(R.id.etName);
        etDescription = view.findViewById(R.id.etDescription);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvSave = view.findViewById(R.id.tvSave);
        cbHasTarget = view.findViewById(R.id.cbHasTarget);
        flTargetAmountLayout = view.findViewById(R.id.flTargetAmountLayout);
        etTargetAmount = view.findViewById(R.id.etTargetAmount);
        flTargetDateLayout = view.findViewById(R.id.flTargetDateLayout);
        etTargetEndDate = view.findViewById(R.id.etTargetEndDate);

        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        cbHasTarget.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                flTargetAmountLayout.setVisibility(View.VISIBLE);
                etTargetAmount.setVisibility(View.VISIBLE);
                flTargetDateLayout.setVisibility(View.VISIBLE);
                etTargetEndDate.setVisibility(View.VISIBLE);
            } else {
                flTargetAmountLayout.setVisibility(View.GONE);
                etTargetAmount.setText("");
                flTargetDateLayout.setVisibility(View.GONE);
                etTargetEndDate.setText("");
            }
        });

        etTargetEndDate.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        tvSave.setOnClickListener(v -> {
            createNewGroup();
        });

        // Observe ViewModel states
        groupsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            tvSave.setEnabled(!isLoading);
        });

        groupsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                groupsViewModel.clearErrorMessage(); // Gọi phương thức clearErrorMessage()
            }
        });

        groupsViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                groupsViewModel.clearSuccessMessage(); // Gọi phương thức clearSuccessMessage()
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        if (!etTargetEndDate.getText().toString().isEmpty()) {
            try {
                calendar.setTime(dateFormat.parse(etTargetEndDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    etTargetEndDate.setText(dateFormat.format(selectedDate.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void createNewGroup() {
        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        boolean hasTarget = cbHasTarget.isChecked();
        Double targetAmount = null;
        String targetEndDate = null;
        Integer categoryId = null;

        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Tên quỹ là bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (hasTarget) {
            String targetAmountStr = etTargetAmount.getText().toString().trim();
            String targetEndDateStr = etTargetEndDate.getText().toString().trim();

            if (targetAmountStr.isEmpty() || targetEndDateStr.isEmpty()) {
                Toast.makeText(requireContext(), "Số tiền mục tiêu và ngày kết thúc là bắt buộc cho quỹ có mục tiêu.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                targetAmount = Double.parseDouble(targetAmountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Số tiền mục tiêu không hợp lệ.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Date endDate = dateFormat.parse(targetEndDateStr);
                if (endDate != null && endDate.before(new Date())) {
                    Toast.makeText(requireContext(), "Ngày kết thúc mục tiêu phải ở trong tương lai.", Toast.LENGTH_SHORT).show();
                    return;
                }
                targetEndDate = targetEndDateStr;
            } catch (ParseException e) {
                Toast.makeText(requireContext(), "Ngày kết thúc mục tiêu không hợp lệ.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (groupsViewModel.getCategoryList().getValue() != null && !groupsViewModel.getCategoryList().getValue().isEmpty()) {
            categoryId = groupsViewModel.getCategoryList().getValue().get(0).getCategoryId();
        }

        CreateGroupRequest request = new CreateGroupRequest(
                name,
                description,
                hasTarget,
                targetAmount,
                targetEndDate,
                categoryId
        );

        groupsViewModel.createFund(request);
    }
}