package com.example.happy_wallet_mobile.View.Fragment.Setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

public class ChangePasswordFragment extends Fragment {

    EditText etCurrentPassword, etNewPassword1, etNewPassword2;
    TextView tvCancel, tvResetPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        etNewPassword1 = view.findViewById(R.id.etNewPassword1);
        etNewPassword2 = view.findViewById(R.id.etNewPassword2);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvResetPassword = view.findViewById(R.id.tvResetPassword);

        //cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}