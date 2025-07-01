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

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Fragment.CategoryListFragment;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyTextWatcher;
import com.example.happy_wallet_mobile.ViewModel.Wallet.AddIncomeViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddIncomeFragment extends Fragment {

    MainViewModel mainViewModel;
    AddIncomeViewModel addIncomeViewModel;
    EditText etTitle, etDescription, etMoney;
    RecyclerView rcvCategories;
    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_income, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        addIncomeViewModel = new ViewModelProvider(requireActivity()).get(AddIncomeViewModel.class);

        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etMoney = view.findViewById(R.id.etMoney);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        tvCancel = view.findViewById(R.id.tvCancel);

        addIncomeViewModel.loadDataFromServer();

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
        // Observe LiveData để cập nhật adapter
        addIncomeViewModel.CategoryList.observe(getViewLifecycleOwner(), categoryResponses -> {
            if (categoryResponses != null) {
                List<Category> categories = new ArrayList<>();
                for (CategoryResponse response : categoryResponses) {
                    categories.add(mapToCategory(response));
                }
                categoryRecyclerViewAdapter.updateCategories(categories);
            }
        });



        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // set money format
        etMoney.addTextChangedListener(new CurrencyTextWatcher(etMoney));

        return view;
    }

    private Category mapToCategory(CategoryResponse response) {
        // Lấy icon và màu mặc định từ StaticDataProvider (tránh lỗi không tồn tại resource)
        int defaultIconRes = StaticDataProvider.getIconList().get(0);
        int defaultColorRes = StaticDataProvider.getColorList().get(0);

        return new Category(
                response.getId(),
                response.getUser_id(),
                defaultColorRes,
                defaultIconRes,
                eType.EXPENSE, // Bạn có thể ánh xạ đúng nếu response có trường `type`
                response.getName(),
                response.isIs_default(),
                null, // createdAt (nếu cần, có thể convert từ response.getCreated_at())
                null, // updatedAt
                null  // deletedAt
        );
    }
}