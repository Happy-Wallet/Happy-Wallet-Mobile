package com.example.happy_wallet_mobile.View.Fragment.Setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.SettingService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Setting.ChangePasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Setting.ChangePasswordResponse;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Setting.ChangePasswordViewModel;

import retrofit2.Call;

public class ChangePasswordFragment extends Fragment {

    EditText etCurrentPassword, etNewPassword1, etNewPassword2;
    TextView tvCancel, tvResetPassword;
    ChangePasswordViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        viewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);

        etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        etNewPassword1 = view.findViewById(R.id.etNewPassword1);
        etNewPassword2 = view.findViewById(R.id.etNewPassword2);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvResetPassword = view.findViewById(R.id.tvResetPassword);

        //cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //change pass
        tvResetPassword.setOnClickListener(v -> {
            String currentPassword = etCurrentPassword.getText().toString().trim();
            String newPassword1 = etNewPassword1.getText().toString().trim();
            String newPassword2 = etNewPassword2.getText().toString().trim();

            if (currentPassword.isEmpty() || newPassword1.isEmpty() || newPassword2.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword1.equals(newPassword2)) {
                Toast.makeText(getContext(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.changePassword(currentPassword, newPassword1);
        });

        //observe từ viewmodel
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading != null && loading) {
                tvResetPassword.setEnabled(false);
            } else {
                tvResetPassword.setEnabled(true);
            }
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getChangeSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }
}