package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.Model.SubDestination;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.CategoryRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.SavingGoalRecyclerViewAdapter;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;


public class AddSavingGoalFragment extends Fragment {

    TextView tvCancel, tvDate;
    EditText etTitle, etDescription, etTarget;
    RecyclerView rcvCategories;
    MainViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_saving_goal, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        tvCancel = view.findViewById(R.id.tvCancel);
        tvDate = view.findViewById(R.id.tvDate);
        etTitle = view.findViewById(R.id.etTitle);
        etDescription = view.findViewById(R.id.etDescription);
        etTarget = view.findViewById(R.id.etTarget);
        rcvCategories = view.findViewById(R.id.rcvCategories);


        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 3);
        rcvCategories.setLayoutManager(layoutManager);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(
                requireContext(),
                MockDataProvider.getMockCategories(),
                MockDataProvider.getMockIcons(),
                category -> {
                    Toast.makeText(getContext(), "Bạn chọn: " + category.getName(), Toast.LENGTH_SHORT).show();
                });
        categoryRecyclerViewAdapter.setOnAddClickListener(() -> {
            Toast.makeText(getContext(), "Bạn đã nhấn Add More", Toast.LENGTH_SHORT).show();
            mainViewModel.onNavItemClickedSubBelow(SubDestination.CATEGORY_LIST);

        });
        rcvCategories.setAdapter(categoryRecyclerViewAdapter);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }


}