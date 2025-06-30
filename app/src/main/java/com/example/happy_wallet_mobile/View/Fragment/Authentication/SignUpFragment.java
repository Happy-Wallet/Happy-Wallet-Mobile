package com.example.happy_wallet_mobile.View.Fragment.Authentication;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Authentication.SignUpViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SignUpFragment extends Fragment {

    SignUpViewModel signUpViewModel;
    EditText etUserName, etMail, etPassword;
    TextView tvSignUp, tvCancel;
    EditText etDateOfBirth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        etUserName = view.findViewById(R.id.etUserName);
        etMail = view.findViewById(R.id.etMail);
        etPassword = view.findViewById(R.id.etPassword);
        tvSignUp = view.findViewById(R.id.tvSignUp);
        tvCancel = view.findViewById(R.id.tvCancel);
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth);
        etDateOfBirth.setInputType(InputType.TYPE_NULL);
        etDateOfBirth.setFocusable(false);
        Calendar calendar = Calendar.getInstance();
        etDateOfBirth.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Chọn ngày sinh")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // default: hôm nay
                            .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formattedDate = sdf.format(new Date(selection));
                etDateOfBirth.setText(formattedDate);
            });

            datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        // cancel btn click
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // sign up btn click
        tvSignUp.setOnClickListener(v -> {
            tvSignUp.setEnabled(false);

            String userName = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String mail = etMail.getText().toString().trim();
            String dob = etDateOfBirth.getText().toString().trim();

            if (userName.isEmpty() || password.isEmpty() || mail.isEmpty() || dob.isEmpty()) {
                Toast.makeText(requireContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                tvSignUp.setEnabled(true);
            } else {
                signUpViewModel.register(mail, userName, password, dob);
            }
        });


        // get sign up result from viewmodel
        signUpViewModel.getRegisterResponse().observe(getViewLifecycleOwner(), response -> {
            tvSignUp.setEnabled(true);

            if (response != null) {
                Toast.makeText(requireContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(requireContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}