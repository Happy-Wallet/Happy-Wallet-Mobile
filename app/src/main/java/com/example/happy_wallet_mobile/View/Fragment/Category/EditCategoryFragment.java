package com.example.happy_wallet_mobile.View.Fragment.Category;

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
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.UpdateCategoryRequest;
import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.ColorRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.IconRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.Category.AddCategoryViewModel;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;
import com.example.happy_wallet_mobile.ViewModel.Category.EditCategoryViewModel;

public class EditCategoryFragment extends Fragment {
    CategoryListViewModel categoryListViewModel;
    EditCategoryViewModel editCategoryViewModel;
    RecyclerView rcvIcons, rcvColors;
    TextView tvCancel, tvSave, tvDelete;
    EditText etName;
    private String selectedIconRes;
    private String selectedColorRes;
    Category selectedCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_category, container, false);

        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        editCategoryViewModel = new ViewModelProvider(requireActivity()).get(EditCategoryViewModel.class);

        selectedCategory = categoryListViewModel.getSelectedCategory().getValue();

        rcvIcons = view.findViewById(R.id.rcvIcons);
        rcvColors = view.findViewById(R.id.rcvColors);
        tvCancel = view.findViewById(R.id.tvCancel);
        etName = view.findViewById(R.id.etName);
        tvSave = view.findViewById(R.id.tvSave);
        tvDelete = view.findViewById(R.id.tvDelete);

        etName.setText(selectedCategory.getName());

        // rcvIcons data setter
        rcvIcons.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        IconRecyclerViewAdapter iconAdapter = new IconRecyclerViewAdapter(
                requireContext(),
                StaticDataProvider.getIconList());

        iconAdapter.setOnIconClickListener(iconResId -> {
            selectedIconRes = getResources().getResourceEntryName(iconResId);
//            Toast.makeText(requireContext(), "Chọn icon: " + selectedIconRes, Toast.LENGTH_SHORT).show();
        });
        rcvIcons.setAdapter(iconAdapter);

        // rcvColors data setter
        rcvColors.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        ColorRecyclerViewAdapter colorAdapter = new ColorRecyclerViewAdapter(
                requireContext(),
                StaticDataProvider.getColorList());

        colorAdapter.setOnColorClickListener(colorResId -> {
            selectedColorRes = getResources().getResourceEntryName(colorResId);
//            Toast.makeText(requireContext(), "Chọn color: " + selectedColorRes, Toast.LENGTH_SHORT).show();
        });
        rcvColors.setAdapter(colorAdapter);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // save
        tvSave.setOnClickListener(v -> {
            editCategoryViewModel.updateCategory(selectedCategory.getCategoryId(), new UpdateCategoryRequest(
                    selectedIconRes,
                    selectedColorRes,
                    etName.getText().toString()
            ));

        });

        // delete
        tvDelete.setOnClickListener(v->{
            editCategoryViewModel.deleteCategory(selectedCategory.getCategoryId());
        });

        editCategoryViewModel.getUpdateCategoryResult().observe(getViewLifecycleOwner(), updatedCategory -> {
            if (updatedCategory != null) {
                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                categoryListViewModel.fetchCategories(requireContext());
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
//                Toast.makeText(getContext(), "không thể cập nhật default category", Toast.LENGTH_SHORT).show();
            }
        });

        editCategoryViewModel.getDeleteCategoryResult().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                categoryListViewModel.fetchCategories(requireContext());
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
//                Toast.makeText(getContext(), "không thể xóa default category", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}