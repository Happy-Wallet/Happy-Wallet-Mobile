package com.example.happy_wallet_mobile.View.Fragment.Wallet;

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

import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Fragment.Category.CategoryListFragment;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Wallet.AddExpenditureViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddExpenditureFragment extends Fragment {

    MainViewModel mainViewModel;
    AddExpenditureViewModel addExpenditureViewModel;
    CategoryListViewModel categoryListViewModel;
    EditText etDescription, etMoney;
    RecyclerView rcvCategories;
    TextView tvCancel, tvSave, tvDate;
    private int selectedCategoryId = -1;
    private Date selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expenditure, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        addExpenditureViewModel = new ViewModelProvider(requireActivity()).get(AddExpenditureViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);

        categoryListViewModel.setType(eType.EXPENSE);

        etDescription = view.findViewById(R.id.etDescription);
        etMoney = view.findViewById(R.id.etMoney);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvSave = view.findViewById(R.id.tvSave);
        tvDate = view.findViewById(R.id.tvDate);

        Calendar calendar = Calendar.getInstance();
        tvDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Chọn ngày sinh")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // default: hôm nay
                            .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = new Date(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formattedDate = sdf.format(selectedDate);
                tvDate.setText(formattedDate);
            });

            datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                requireContext(),
                List.of(),
                eType.EXPENSE,
                category -> {
                    selectedCategoryId = category.getCategoryId();
                    Toast.makeText(getContext(), "Bạn chọn: " + category.getName(), Toast.LENGTH_SHORT).show();
                });

        categoryRecyclerViewAdapter.setOnAddClickListener(() -> {
            Toast.makeText(getContext(), "Bạn đã nhấn Add More", Toast.LENGTH_SHORT).show();
            mainViewModel.navigateSubBelow(new CategoryListFragment());
        });
        rcvCategories.setAdapter(categoryRecyclerViewAdapter);
        // Observe LiveData để cập nhật adapter
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), categories -> {
            categoryRecyclerViewAdapter.updateCategories(categories);
        });

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // set money format
        etMoney.addTextChangedListener(new CurrencyTextWatcher(etMoney));

        // create transaction
        tvSave.setOnClickListener(v -> {
            String description = etDescription.getText().toString().trim();
            String amountStr = etMoney.getText().toString().trim();
            BigDecimal amount = CurrencyUtility.parse(amountStr);

            if (selectedCategoryId == -1) {
                Toast.makeText(getContext(), "Vui lòng chọn category", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDate == null) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                selectedDate = cal.getTime();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedDate = sdf.format(selectedDate);

            addExpenditureViewModel.createTransaction(selectedCategoryId, amount, description, formattedDate);
        });


        addExpenditureViewModel.getCreateTransactionResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), "Tạo giao dịch thành công!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });



        return view;
    }
}