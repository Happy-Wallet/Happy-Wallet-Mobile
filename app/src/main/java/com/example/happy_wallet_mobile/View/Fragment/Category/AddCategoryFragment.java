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
import com.example.happy_wallet_mobile.Data.Remote.Request.Category.CreateCategoryRequest;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.ColorRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.IconRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.Category.AddCategoryViewModel;
import com.example.happy_wallet_mobile.ViewModel.Category.CategoryListViewModel;

public class AddCategoryFragment extends Fragment {

    CategoryListViewModel categoryListViewModel;
    AddCategoryViewModel addCategoryViewModel;
    RecyclerView rcvIcons, rcvColors;
    TextView tvCancel, tvSave;
    EditText etName;

    private String selectedIconRes;
    private String selectedColorRes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        categoryListViewModel = new ViewModelProvider(requireActivity()).get(CategoryListViewModel.class);
        addCategoryViewModel = new ViewModelProvider(requireActivity()).get(AddCategoryViewModel.class);

        rcvIcons = view.findViewById(R.id.rcvIcons);
        rcvColors = view.findViewById(R.id.rcvColors);
        tvCancel = view.findViewById(R.id.tvCancel);
        etName = view.findViewById(R.id.etName);
        tvSave = view.findViewById(R.id.tvSave);


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
            String name = etName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập tên category", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedIconRes == null) {
                Toast.makeText(getContext(), "Vui lòng chọn icon", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedColorRes == null) {
                Toast.makeText(getContext(), "Vui lòng chọn màu", Toast.LENGTH_SHORT).show();
                return;
            }

            eType type = categoryListViewModel.getType().getValue();
            if (type == null) {
                Toast.makeText(getContext(), "Không xác định loại category", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }

            CreateCategoryRequest request = new CreateCategoryRequest(
                    selectedIconRes,
                    selectedColorRes,
                    name
            );

            addCategoryViewModel.createCategory(type.toDbValue(), request);
        });

        addCategoryViewModel.getCreateCategoryResult().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), "Tạo category thành công", Toast.LENGTH_SHORT).show();
                categoryListViewModel.fetchCategories(requireContext());
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
//                Toast.makeText(getContext(), "Tạo category thất bại", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}