package com.example.happy_wallet_mobile.View.Fragment.Setting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;


public class SettingFragment extends Fragment {

    MainViewModel mainViewModel;
    ImageView ivProfileImage;
    TextView tvName;
    FrameLayout flEditProfile, flChangePassword, flAboutUs, flLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvName = view.findViewById(R.id.tvName);
        flEditProfile = view.findViewById(R.id.flEditProfile);
        flChangePassword = view.findViewById(R.id.flChangePassword);
        flAboutUs = view.findViewById(R.id.flAboutUs);
        flLogOut = view.findViewById(R.id.flLogOut);

        // set data
        Glide.with(this)
                .load(UserPreferences.getUser().getAvatarUrl())
                .placeholder(R.drawable.ic_analysis)
                .error(R.drawable.ic_analysis)
                .circleCrop()
                .into(ivProfileImage);
        tvName.setText(UserPreferences.getUser().getUserName());

        // edit profile
        flEditProfile.setOnClickListener(v -> {
            mainViewModel.navigateSubAbove(new EditProfileFragment());
        });

        // change password
        flChangePassword.setOnClickListener( v -> {
            mainViewModel.navigateSubAbove(new ChangePasswordFragment());
        });

        return view;
    }
}