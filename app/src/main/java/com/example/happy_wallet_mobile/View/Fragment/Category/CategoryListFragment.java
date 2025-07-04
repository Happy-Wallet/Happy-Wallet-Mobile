package com.example.happy_wallet_mobile.View.Fragment.Category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryListViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;


public class CategoryListFragment extends Fragment {
    MainViewModel mainViewModel;
    CategoryListViewModel categoryListViewModel;

    FrameLayout flAddCategory;
    ListView lvCategoryList;
    CategoryListViewAdapter categoryListViewAdapter;

    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        Log.d("CategoryListFragment", "CategoryListFragment on create view");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);

        categoryListViewModel.fetchCategories(requireContext());

        flAddCategory = view.findViewById(R.id.flAddCategory);
        lvCategoryList = view.findViewById(R.id.lvCategoryList);
        tvCancel = view.findViewById(R.id.tvCancel);

        // add category
        flAddCategory.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new AddCategoryFragment());
        });

        // Khởi tạo repository và load danh sách từ danh sách category trong home
        categoryListViewModel.getCategoryList().observe(getViewLifecycleOwner(), categoryList -> {
            Log.d("CategoryListFragment", "categoryList: " + categoryList);
            if (categoryList != null) {
                categoryListViewAdapter = new CategoryListViewAdapter(
                        requireContext(),
                        categoryList,
                        categoryListViewModel.getType().getValue());

                categoryListViewAdapter.setOnCategoryClickListener(category -> {
//                    Toast.makeText(getContext(), "Bạn đã chọn: " + category.getName(), Toast.LENGTH_SHORT).show();

                    categoryListViewModel.setSelectCategory(category);
                    mainViewModel.navigateSubBelow(new EditCategoryFragment());
                });

                lvCategoryList.setAdapter(categoryListViewAdapter);
            } else {
                Toast.makeText(requireContext(), "Lỗi tải danh mục từ server", Toast.LENGTH_SHORT).show();
            }
        });

        // cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

}