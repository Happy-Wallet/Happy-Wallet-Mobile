package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.SignUpViewModel;


public class EditProfileFragment extends Fragment {

    ImageView ivProfileImage;
    EditText etName, etUserName, etMail, etDateOfBirth, etPassword;
    TextView tvSave, tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        etName = view.findViewById(R.id.etName);
        etUserName = view.findViewById(R.id.etUserName);
        etMail = view.findViewById(R.id.etMail);
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth);
        etPassword = view.findViewById(R.id.etPassword);
        tvSave = view.findViewById(R.id.tvSave);
        tvCancel = view.findViewById(R.id.tvCancel);

        // cancel btn click
        tvCancel.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

}