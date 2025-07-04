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
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Fragment.Category.CategoryListFragment;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.AddSavingGoalViewModel;
import com.example.happy_wallet_mobile.ViewModel.Home.SavingGoalListViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddSavingGoalFragment extends Fragment {

    TextView tvCancel, tvSave, tvDate;
    EditText etTitle, etDescription, etTarget;
    RecyclerView rcvCategories;
    MainViewModel mainViewModel;
    AddSavingGoalViewModel addSavingGoalViewModel;
    CategoryListViewModel categoryListViewModel;
    SavingGoalListViewModel savingGoalListViewModel;

    private Category selectedCategory = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_saving_goal, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        addSavingGoalViewModel = new ViewModelProvider(requireActivity()).get(AddSavingGoalViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        savingGoalListViewModel = new ViewModelProvider(requireActivity()).get(SavingGoalListViewModel.class);

        categoryListViewModel.fetchCategories(requireContext());
        categoryListViewModel.setType(eType.SAVING_GOAL);

        // Bind UI
        tvCancel = view.findViewById(R.id.tvCancel);
        tvDate = view.findViewById(R.id.tvDate);
        tvSave = view.findViewById(R.id.tvSave); // thêm nút lưu
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etTarget = view.findViewById(R.id.etTarget);
        rcvCategories = view.findViewById(R.id.rcvCategories);

        // RecyclerView category
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);

        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                requireContext(),
                List.of(),
                eType.SAVING_GOAL,
                category -> {
                    selectedCategory = category;
//                    Toast.makeText(getContext(), "Đã chọn: " + category.getName(), Toast.LENGTH_SHORT).show();
                });

        categoryRecyclerViewAdapter.setOnAddClickListener(() -> {
//            Toast.makeText(getContext(), "Bạn đã nhấn Add More", Toast.LENGTH_SHORT).show();
            mainViewModel.navigateSubBelow(new CategoryListFragment());
        });

        rcvCategories.setAdapter(categoryRecyclerViewAdapter);

        // Quan sát danh sách category
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), categoryRecyclerViewAdapter::updateCategories);

        // Huỷ bỏ
        tvCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Format tiền
        etTarget.addTextChangedListener(new CurrencyTextWatcher(etTarget));

        // set target Date
        tvDate.setOnClickListener(v -> {
            showDatePicker();
        });

        // Xử lý nút Lưu
        tvSave.setOnClickListener(v -> {
            String token = getToken(); // lấy từ SharedPreferences
            int userId = getUserId(); // hoặc hardcode nếu test

            if (selectedCategory == null) {
                Toast.makeText(getContext(), "Bạn chưa chọn danh mục", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            BigDecimal target = CurrencyUtility.parse(etTarget.getText().toString());

            if (title.isEmpty() || (target.compareTo(BigDecimal.ZERO) <= 0)
) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String startDate = sdf.format(Calendar.getInstance().getTime());
            String endDate = tvDate.getText().toString();

            CreateSavingGoalRequest request = new CreateSavingGoalRequest(
                    userId,
                    title,
                    BigDecimal.ZERO, // current_amount mặc định
                    target,
                    description,
                    startDate,
                    endDate,
                    selectedCategory.getCategoryId()
            );

            addSavingGoalViewModel.createSavingGoal(token, request);
        });

        // Quan sát kết quả tạo
        addSavingGoalViewModel.createResult.observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(getContext(), "Tạo mục tiêu thành công!", Toast.LENGTH_SHORT).show();
                savingGoalListViewModel.fetchSavingGoals();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Tạo mục tiêu thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private double parseMoney(String formatted) {
        try {
            return Double.parseDouble(formatted.replaceAll("[^\\d]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private String getToken() {
        return UserPreferences.getToken();
    }

    private int getUserId() {
        User user = UserPreferences.getUser();
        return (user != null) ? user.getId() : -1;
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
