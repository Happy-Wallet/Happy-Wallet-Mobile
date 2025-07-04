package com.example.happy_wallet_mobile.View.Fragment.Group;

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
import com.example.happy_wallet_mobile.ViewModel.Group.AddGroupContributionViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;
import com.example.happy_wallet_mobile.ViewModel.Wallet.AddIncomeViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddGroupContributeFragment extends Fragment {

    MainViewModel mainViewModel;
    AddGroupContributionViewModel addGroupContributionViewModel;
    GroupsViewModel groupsViewModel;
    CategoryListViewModel categoryListViewModel;
    EditText etDescription, etMoney;
    RecyclerView rcvCategories;
    TextView tvCancel, tvSave;
    private int selectedCategoryId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_contribute, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        addGroupContributionViewModel = new ViewModelProvider(requireActivity()).get(AddGroupContributionViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);

        categoryListViewModel.setType(eType.INCOME);

        etDescription = view.findViewById(R.id.etDescription);
        etMoney = view.findViewById(R.id.etMoney);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvSave = view.findViewById(R.id.tvSave);


        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                requireContext(),
                List.of(),
                eType.INCOME,
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

            addGroupContributionViewModel.createGroupTransaction(
                    groupsViewModel.getCurrentGroup().getValue().getId(),
                    selectedCategoryId,
                    amount,
                    description
            );
        });



        addGroupContributionViewModel.getCreateResult().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), "Tạo giao dịch thành công!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}