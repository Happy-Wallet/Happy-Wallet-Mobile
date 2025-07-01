package com.example.happy_wallet_mobile.View.Fragment;

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

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Data.Remote.Response.Category.CategoryResponse;
import com.example.happy_wallet_mobile.Data.Repository.CategoryRepository;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryListViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryListFragment extends Fragment {
    MainViewModel mainViewModel;
    FrameLayout flAddCategory;
    ListView lvCategoryList;
    CategoryListViewAdapter categoryListViewAdapter;
    CategoryRepository categoryRepository;


    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        Log.d("CategoryListFragment", "CategoryListFragment on create view");

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        flAddCategory = view.findViewById(R.id.flAddCategory);
        lvCategoryList = view.findViewById(R.id.lvCategoryList);
        tvCancel = view.findViewById(R.id.tvCancel);

        // add category
        flAddCategory.setOnClickListener(v -> {
            mainViewModel.navigateSubBelow(new AddCategoryFragment());
        });

        // Khởi tạo repository và load danh sách từ server
        CategoryRepository categoryRepository = new CategoryRepository();
        categoryRepository.getAllCategories().observe(getViewLifecycleOwner(), responses -> {
            if (responses != null) {
                List<Category> categoryList = new ArrayList<>();

                categoryListViewAdapter = new CategoryListViewAdapter(requireContext(), categoryList);
                categoryListViewAdapter.setOnCategoryClickListener(category -> {
                    Toast.makeText(getContext(), "Bạn đã chọn: " + category.getName(), Toast.LENGTH_SHORT).show();
                    // xử lý thêm tại đây (mở Fragment khác, truyền data,...)
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