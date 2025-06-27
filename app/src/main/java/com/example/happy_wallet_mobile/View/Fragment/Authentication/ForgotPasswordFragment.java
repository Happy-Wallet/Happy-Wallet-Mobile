package com.example.happy_wallet_mobile.View.Fragment.Authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

public class ForgotPasswordFragment extends Fragment {

    EditText etEmail;
    TextView tvCancel, tvReceivePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvReceivePassword = view.findViewById(R.id.tvReceivePassword);

        return view;
    }
}