package com.example.happy_wallet_mobile.View.Fragment.Authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Activity.MainActivity;
import com.example.happy_wallet_mobile.View.Activity.SignInActivity;
import com.example.happy_wallet_mobile.ViewModel.Authentication.ForgotPasswordViewModel;
import com.example.happy_wallet_mobile.ViewModel.Authentication.SignInViewModel;

public class ForgotPasswordFragment extends Fragment {

    ForgotPasswordViewModel forgotPasswordViewModel;
    EditText etEmail;
    TextView tvCancel, tvReceivePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        forgotPasswordViewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        etEmail = view.findViewById(R.id.etEmail);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvReceivePassword = view.findViewById(R.id.tvReceivePassword);

        // observe forgot password response
        forgotPasswordViewModel.getForgotPasswordResponse().observe(getViewLifecycleOwner(), response->{
            if (response != null) {
                Toast.makeText(requireContext(), "Password đã gửi về Email", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(requireContext(), "Email chưa dùng để đăng ký", Toast.LENGTH_SHORT).show();
            }
        });

        //cancel
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //forgot password
        tvReceivePassword.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if(email.isEmpty()){
                Toast.makeText(requireContext(), "Chưa nhập Email", Toast.LENGTH_SHORT).show();
            } else{
                forgotPasswordViewModel.forgotPassword(email);
            }
        });

        return view;
    }
}