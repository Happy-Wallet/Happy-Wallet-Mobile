package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.Local.StaticDataProvider;
import com.example.happy_wallet_mobile.Data.MockDataProvider;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.ColorRecyclerViewAdapter;
import com.example.happy_wallet_mobile.View.Adapter.IconRecyclerViewAdapter;

public class AddCategoryFragment extends Fragment {

    RecyclerView rcvIcons, rcvColors;
    TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        rcvIcons = view.findViewById(R.id.rcvIcons);
        rcvColors = view.findViewById(R.id.rcvColors);
        tvCancel = view.findViewById(R.id.tvCancel);

        // rcvIcons data setter
        rcvIcons.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        IconRecyclerViewAdapter iconAdapter = new IconRecyclerViewAdapter(requireContext(), StaticDataProvider.getIconList());

        iconAdapter.setOnIconClickListener(iconResId -> {
            Toast.makeText(requireContext(), "Chọn icon: " + iconResId, Toast.LENGTH_SHORT).show();
        });
        rcvIcons.setAdapter(iconAdapter);

        // rcvColors data setter
        rcvColors.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        ColorRecyclerViewAdapter colorAdapter = new ColorRecyclerViewAdapter(requireContext(), StaticDataProvider.getColorList());

        colorAdapter.setOnColorClickListener(color -> {
            Toast.makeText(requireContext(), "Chọn color: " + color, Toast.LENGTH_SHORT).show();
        });
        rcvColors.setAdapter(colorAdapter);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}