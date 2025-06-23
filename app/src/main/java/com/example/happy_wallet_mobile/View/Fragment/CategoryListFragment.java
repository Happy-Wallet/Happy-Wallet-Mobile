package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryListViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.NotificationListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryListFragment extends Fragment {

    FrameLayout flAddCategory;
    ListView lvCategoryList;
    List<Category> categoryList = new ArrayList<Category>();
    CategoryListViewAdapter categoryListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        flAddCategory = view.findViewById(R.id.flAddCategory);
        lvCategoryList = view.findViewById(R.id.lvCategoryList);

        categoryListViewAdapter = new CategoryListViewAdapter(requireContext(), categoryList);
        lvCategoryList.setAdapter(categoryListViewAdapter);

        return view;
    }
}