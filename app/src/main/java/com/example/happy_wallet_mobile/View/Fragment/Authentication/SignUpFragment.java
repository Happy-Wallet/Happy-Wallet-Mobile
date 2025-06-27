package com.example.happy_wallet_mobile.View.Fragment.Authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Authentication.SignUpViewModel;


public class SignUpFragment extends Fragment {

    SignUpViewModel signUpViewModel;
    EditText etName, etUserName, etMail, etDateOfBirth, etPassword;
    TextView tvSignUp, tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpViewModel = new SignUpViewModel();

        etName = view.findViewById(R.id.etName);
        etUserName = view.findViewById(R.id.etUserName);
        etMail = view.findViewById(R.id.etMail);
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth);
        etPassword = view.findViewById(R.id.etPassword);
        tvSignUp = view.findViewById(R.id.tvSignUp);
        tvCancel = view.findViewById(R.id.tvCancel);

        // cancel btn click
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // sign up btn click
        tvSignUp.setOnClickListener(v -> {
            signUpViewModel.setName(etName.getText().toString());
            signUpViewModel.setUserName(etUserName.getText().toString());
            signUpViewModel.setMail(etMail.getText().toString());
            signUpViewModel.setDateOfBirth(etDateOfBirth.getText().toString());
            signUpViewModel.setPassword(etPassword.getText().toString());

            signUpViewModel.attempSignUp();
        });

        // get sign up result from viewmodel
        signUpViewModel.getSignUpResult().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "chưa hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}