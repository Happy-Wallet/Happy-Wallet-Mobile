package com.example.happy_wallet_mobile.View.Fragment.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Activity.SignInActivity;
import com.example.happy_wallet_mobile.ViewModel.Authentication.ResetPasswordViewModel;

public class ResetPasswordFragment extends Fragment {
    EditText etOtp, etNewPassword, etConfirmPassword;
    TextView tvResetPassword;
    ResetPasswordViewModel viewModel;
    String email;

    public ResetPasswordFragment(String email) {
        this.email = email;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        etOtp = view.findViewById(R.id.etOtp);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        tvResetPassword = view.findViewById(R.id.tvResetPassword);

        viewModel.getResetPasswordResponse().observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                Toast.makeText(getContext(), "Reset thành công", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(requireContext(), SignInActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(getContext(), "Reset thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        tvResetPassword.setOnClickListener(v -> {
            String otp = etOtp.getText().toString().trim();
            String pw1 = etNewPassword.getText().toString().trim();
            String pw2 = etConfirmPassword.getText().toString().trim();

            if (!pw1.equals(pw2)) {
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.resetPassword(email, otp, pw1);
        });

        return view;
    }
}
