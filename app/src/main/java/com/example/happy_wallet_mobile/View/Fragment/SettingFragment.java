package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;


public class SettingFragment extends Fragment {

    ImageView ivProfileImage;
    FrameLayout flEditProfile, flChangePassword, flAboutUs, flLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        flEditProfile = view.findViewById(R.id.flEditProfile);
        flChangePassword = view.findViewById(R.id.flChangePassword);
        flAboutUs = view.findViewById(R.id.flAboutUs);
        flLogOut = view.findViewById(R.id.flLogOut);





        return view;
    }
}